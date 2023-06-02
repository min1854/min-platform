package com.old.generator.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.old.common.mybatis.base.BaseServiceImpl;
import com.old.generator.domain.Tables;
import com.old.generator.mapper.TablesMapper;
import com.old.generator.service.TablesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class TablesServiceImpl extends BaseServiceImpl<TablesMapper, Tables> implements TablesService {

    @Autowired
    TablesMapper tablesMapper;

    @Override
    public List<Tables> selectByDataBaseAndTables(String database, Collection<String> tables, Set<String> dontNeedTablesName) {
        LambdaQueryWrapper<Tables> queryWrapper = Wrappers.<Tables>lambdaQuery(Tables.class);
        queryWrapper.eq(Tables::getTableSchema, database)
                .in(CollUtil.isNotEmpty(tables), Tables::getTableName, tables)
                .notIn(CollUtil.isNotEmpty(dontNeedTablesName), Tables::getTableName, dontNeedTablesName);
        return tablesMapper.selectList(queryWrapper);
    }
}
