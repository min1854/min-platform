package com.old.generator.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.min1854.apiAssert.check.OperateApiAssert;
import com.old.common.base.BaseException;
import com.old.generator.domain.Columns;
import com.old.generator.domain.Tables;
import com.old.generator.domain.bo.ColumnsBo;
import com.old.generator.domain.bo.GeneratorBo;
import com.old.generator.domain.bo.TablesBo;
import com.old.generator.domain.bo.TemplateBo;
import com.old.generator.enums.ColumnsEnums;
import com.old.generator.service.ColumnsService;
import com.old.generator.service.GeneratorService;
import com.old.generator.service.TablesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class GeneratorServiceImpl implements GeneratorService {
    @Autowired
    ColumnsService columnsService;

    @Autowired
    TablesService tablesService;

    @Autowired
    JdbcTemplate jdbcTemplate;


    /**
     *
     */
    @Override
    public void generator(GeneratorBo param) {
        log.debug("生成代码参数：{}", param);
        OperateApiAssert.create(param, BaseException::new)
                .isNull("参数不存在")
                .isEmpty(GeneratorBo::getPackageName, "包名不存在")
                .isEmpty(GeneratorBo::getOutDir, "输出目录不存在")
                .isEmpty(GeneratorBo::getModuleName, "模块名不存在")
                .isEmpty(GeneratorBo::getDatabase, "数据库不存在")
                .process(bo -> {
                    if (StrUtil.isEmpty(bo.getDatetime())) {
                        bo.setDatetime(DateUtil.formatDate(new Date()));
                    }
                    if (StrUtil.isEmpty(bo.getRequestPathPrefix())) {
                        bo.setRequestPathPrefix("");
                    } else if (!bo.getRequestPathPrefix().startsWith("/")) {
                        bo.setRequestPathPrefix("/" + bo.getRequestPathPrefix());
                    }
                })
                .then(bo -> {
                    List<Tables> list = tablesService.selectByDataBaseAndTables(bo.getDatabase(),
                            bo.getTableNames(), bo.getDontNeedTablesName());
                    if (list == null) {
                        LambdaQueryWrapper<Tables> queryWrapper = Wrappers.lambdaQuery(Tables.class);
                        queryWrapper.eq(Tables::getTableSchema, param.getDatabase());
                        list = tablesService.list(queryWrapper);
                    }
                    return list;
                })
                .isEmpty("当前数据库不存在表数据")
                .then(tables -> {
                    Map<TablesBo, List<ColumnsBo>> map = toMap(tables, param.getTables());
                    log.info("map 结果：{}", map);
                    log.info("map 结果：{}", JSONUtil.toJsonStr(map));
                    return map;
                }).process(map -> {

                    for (Map.Entry<TablesBo, List<ColumnsBo>> entry : map.entrySet()) {
                        for (TemplateBo template : templateList()) {
                            if (entry.getKey().getTreeCode().isEmpty() && template.path().equals("/vm/code/views/index-tree.vue.vm")) {
                                continue;
                            } else if (!entry.getKey().getTreeCode().isEmpty() && template.path().equals("/vm/code/views/index.vue.vm")) {
                                continue;
                            }
                            VelocityContext velocityContext = initVelocity(param, entry.getKey(), entry.getValue());
                            try (
                                    StringWriter sw = new StringWriter();
                            ) {
                                Template tpl = Velocity.getTemplate(template.path(), StandardCharsets.UTF_8.name());
                                filter(velocityContext, template.path());
                                tpl.merge(velocityContext, sw);
                                // System.out.println(sw.toString());
                                StringBuilder filePath = new StringBuilder(param.getOutDir())
                                        .append(template.profile())
                                        .append(template.requiredPrefix() ? StrUtil.replace(param.getPackageName(), ".", "/") : "")
                                        .append(template.requiredPrefix() ? "/" : "")
                                        .append(template.requiredPrefix() ? param.getModuleName() + "/" : "")
                                        .append(template.packageName())
                                        .append(templateToFileName(entry.getKey().getClassName(), template, param.getModuleName()));
                                log.debug("生成的文件：{}", filePath);
                                FileUtil.writeString(sw.toString(), new File(filePath.toString()), StandardCharsets.UTF_8);
                            } catch (IOException e) {
                                log.error("代码文件生成异常", e);
                            }
                        }
                    }
                });
    }

    public String templateToFileName(String className, TemplateBo templateBo, String moduleName) {
        String template = templateBo.path();
        moduleName = moduleName + "/";

        // Controller.java Mapper.xml
        String fileName = StrUtil.upperFirst(template.substring(template.lastIndexOf("/") + 1).replace(".vm", ""));

        return switch (template) {
            case "/vm/code/resources/mapper.xml.vm" -> moduleName + className + fileName;
            case "/vm/code/resources/sql.vm" -> className + "Menu." + StrUtil.lowerFirst(fileName);
            case "/vm/code/api/api.js.vm" -> moduleName + StrUtil.lowerFirst(className) + ".js";
            case "/vm/code/utils/enums.js.vm" -> StrUtil.lowerFirst(className) + fileName;
            case "/vm/code/java/domain.java.vm" -> className + ".java";
            case "/vm/code/java/viewPageReq.java.vm" -> className + "ViewPageReq.java";
            case "/vm/code/views/index.vue.vm", "/vm/code/views/index-tree.vue.vm" ->
                    moduleName + StrUtil.lowerFirst(className) + "/" + "index.vue";
            //            case "/vm/code/controller.java.vm" ->
//                    StrUtil.lowerFirst(fileName.substring(0, fileName.indexOf("."))) + "/" + moduleName + className + fileName;
//            case "/vm/code/enums.java.vm" ->
//                    StrUtil.lowerFirst(fileName.substring(0, fileName.indexOf("."))) + "/" + moduleName + className + fileName;
//            case "/vm/code/mapper.java.vm" ->
//                    StrUtil.lowerFirst(fileName.substring(0, fileName.indexOf("."))) + "/" + moduleName + className + fileName;
//            case "/vm/code/service.java.vm" ->
//                    StrUtil.lowerFirst(fileName.substring(0, fileName.indexOf("."))) + "/" + moduleName + className + fileName;
            default -> className + fileName;
        };
    }


    private List<TemplateBo> templateList() {
        return List.of(
                new TemplateBo("vm/code/java/controller.java.vm", "src/main/java/", "controller/", true),
                new TemplateBo("/vm/code/java/domain.java.vm", "src/main/java/", "domain/", true),
                new TemplateBo("/vm/code/java/enums.java.vm", "src/main/java/", "enums/", true),
                new TemplateBo("/vm/code/java/mapper.java.vm", "src/main/java/", "mapper/", true),
                new TemplateBo("/vm/code/java/service.java.vm", "src/main/java/", "service/", true),
                new TemplateBo("/vm/code/java/serviceImpl.java.vm", "src/main/java/", "service/impl/", true),
                new TemplateBo("/vm/code/java/viewPageReq.java.vm", "src/main/java/", "domain/req/", true),


                new TemplateBo("/vm/code/resources/mapper.xml.vm", "src/main/resources/", "mapper/", false),
                new TemplateBo("/vm/code/resources/sql.vm", "src/main/resources/", "", false),


                new TemplateBo("/vm/code/api/api.js.vm", "src/", "api/", false),
                new TemplateBo("/vm/code/utils/enums.js.vm", "src/", "utils/enums/", false),
                new TemplateBo("/vm/code/views/index-tree.vue.vm", "src/", "views/", false),
                new TemplateBo("/vm/code/views/index.vue.vm", "src/", "views/", false)

        );

    }

    private VelocityContext initVelocity(GeneratorBo generatorBo, TablesBo tablesBo, List<ColumnsBo> columnsBos) {
        Properties p = new Properties();
        // 加载 classpath 目录下的 vm 文件
        p.setProperty("resource.loader.file.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        // 定义字符集
        p.setProperty(Velocity.INPUT_ENCODING, StandardCharsets.UTF_8.name());
        // 初始化Velocity引擎，指定配置Properties
        Velocity.init(p);
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("generatorBo", generatorBo);
        velocityContext.put("tablesBo", tablesBo);
        velocityContext.put("columnsBos", columnsBos);
        return velocityContext;
    }

    private void filter(VelocityContext context, String template) {
        switch (template) {
            case "/vm/code/views/index-tree.vue.vm":
            case "/vm/code/views/index.vue.vm": {
                Set<String> set = Set.of("create_user", "create_user_id", "create_time", "update_user", "update_user_id", "update_time", "delete_flag");
                List<ColumnsBo> columnsBos = ((List<ColumnsBo>) context.get("columnsBos")).stream().filter(columnsBo -> !set.contains(columnsBo.getColumnName())).toList();
                context.put("columnsBos", columnsBos);
            }
        }
    }

    public Map<TablesBo, List<ColumnsBo>> toMap(List<Tables> tables, Set<TablesBo> genTables) {
        Map<TablesBo, List<ColumnsBo>> map = new HashMap<>();
        for (Tables table : tables) {
            List<Columns> columns = columnsService.selectByDatabaseAndTable(table.getTableSchema(), table.getTableName());
            if (CollUtil.isEmpty(columns)) {
                log.warn("数据库 {} 中 {} 表，不存在字段", table.getTableSchema(), table.getTableName());
                continue;
            }
            TablesBo tablesBo = toTablesBo(table, genTables);
            genTables.stream().filter(tempTable -> tempTable.getTable().equals(tablesBo.getTableName()))
                    .findAny().ifPresent(tempTable -> tablesBo.setFields(tempTable.getFields()));
            map.put(tablesBo, toColumnsBo(tablesBo, columns));
        }
        return map;
    }


    public TablesBo toTablesBo(Tables table, Collection<TablesBo> genTables) {
        TablesBo bo = genTables.stream().filter(item -> item.getTable().equals(table.getTableName()))
                .findAny().orElse(new TablesBo());
        BeanUtils.copyProperties(table, bo);
        String objectName = StrUtil.toCamelCase(bo.getTableName());
        String className = StrUtil.upperFirst(objectName);
        bo.setClassName(className);
        bo.setObjectName(objectName);
        bo.setRequestPath("/" + objectName);
        return bo;
    }


    public List<ColumnsBo> toColumnsBo(TablesBo tablesBo, List<Columns> columns) {
        List<ColumnsBo> bos = toColumnsBos(tablesBo, columns);
        Set<String> importSet = new HashSet<>(columns.size());
        for (Columns column : columns) {

            bos.stream()
                    .filter(bo -> bo.getColumnName().equals(column.getColumnName()))
                    .findAny()
                    .ifPresent(bo -> {


                        bo.setJavaFiled(StrUtil.toCamelCase(column.getColumnName()));
                        bo.setEnumName(StrUtil.upperFirst(bo.getJavaFiled()));

                        bo.setIsEnum(bo.getIsEnum());


                        if (!bo.getColumnClassName().startsWith("java.lang")) {
                            log.debug("不是 lang 包下的类，字段名：{}，数据库字段类型：{}，java字段类型：{}，字段类：{}",
                                    bo.getJavaFiled(), bo.getColumnType(), bo.getJavaFieldType(), bo.getColumnClassName());
                            importSet.add(bo.getColumnClassName());
                        }


                        bo.setPrimaryKey(ColumnsEnums.ColumnKeyEnum.PRIMARY_KEY.equals(bo.getColumnKey()));
                        if (bo.isPrimaryKey()) {
                            tablesBo.setPrimaryKeyJavaType(bo.getJavaFieldType());
                            tablesBo.setPrimaryKeyJavaFiledName(bo.getJavaFiled());
                            tablesBo.setPrimaryKeyEnumName(bo.getEnumName());
                        }


                        bo.setColumnType(bo.getDataType().toUpperCase());
                    });

        }
        tablesBo.setImportList(importSet);
        return bos;
    }

    private List<ColumnsBo> toColumnsBos(TablesBo tablesBo, List<Columns> columns) {
        String sql = "select * from " + tablesBo.getTableSchema() + "." + tablesBo.getTableName() + " limit 1";
        log.debug("sql {}", sql);
        List<ColumnsBo> bos = new ArrayList<>(columns.size());
        return jdbcTemplate.query(sql, new ResultSetExtractor<List<ColumnsBo>>() {
            @Override
            public List<ColumnsBo> extractData(ResultSet rs) throws SQLException, DataAccessException {
                int row = rs.getRow();
                log.info("结果集类型：{}，行号：{}，结果集：{}", rs.getClass(), row, rs);
                for (Columns column : columns) {
                    ColumnsBo bo = tablesBo.getFields().get(column.getColumnName());
                    if (bo == null) {
                        bo = new ColumnsBo();
                    }
                    BeanUtils.copyProperties(column, bo);
                    int columnIndex = rs.findColumn(column.getColumnName());
                    String columnClassName = rs.getMetaData().getColumnClassName(columnIndex);
                    // mysql 空类型会返回 [B，默认使用 字符串类型
                    if (columnClassName.equals("[B")) {
                        columnClassName = "java.lang.String";
                    }
                    String columnName = rs.getMetaData().getColumnName(columnIndex);
                    if (column.getColumnName().equals(columnName)) {
                        bo.setColumnClassName(columnClassName);
                        bo.setJavaFieldType(columnClassName.substring(columnClassName.lastIndexOf(".") + 1));
                        if (column.getColumnType().equals("date")) {
                            bo.setColumnClassName("java.time.LocalDate");
                            bo.setJavaFieldType("LocalDate");
                        }
                    }
                    bo.setMybatisJdbcType(JdbcType.forCode(rs.getMetaData().getColumnType(columnIndex)).name());
                    ;
                    bos.add(bo);

                }

                return bos;
            }
        });
    }
}
