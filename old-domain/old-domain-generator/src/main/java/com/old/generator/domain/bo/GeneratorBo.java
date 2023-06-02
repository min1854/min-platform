package com.old.generator.domain.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratorBo {
    /**
     * 包名 com.old.模块名，指定到这层
     */
    //@Nonnull
    private String packageName;

    private String moduleName;

    // 作者
    private String author;
    // 创建时间，为空则是当前时间、yyyy-MM-dd 格式
    private String datetime;
    //@Nonnull
    // 输出目录 目录最后要加 \\ 或是 /
    private String outDir;


    // 数据库名
    private String database;

    //  请求前缀 默认为空字符串
    private String requestPathPrefix;

    /**
     * 不需要的表名称
     */
    private Set<String> dontNeedTablesName = new HashSet<>();

    // 表名，多个用逗号隔开，in 的模式，非模糊查询，没有则是该数据库下所有的表
    private Set<String> tableNames = new HashSet<>();

    private String loginUtilImport = "com.old.common.web.util.LoginUtil";
    private String loginUtilName = "LoginUtil";

    // 生成的表和枚举字段
    private Set<TablesBo> tables;


}
