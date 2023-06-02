package com.old.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.old.common.mybatis.base.BaseServiceImpl;
import com.old.generator.domain.Columns;
import com.old.generator.mapper.ColumnsMapper;
import com.old.generator.service.ColumnsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class ColumnsServiceImpl extends BaseServiceImpl<ColumnsMapper, Columns> implements ColumnsService {

    @Autowired
    ColumnsMapper columnsMapper;


    @Override
    public List<Columns> selectByDatabaseAndTable(String database, String table) {

        LambdaQueryWrapper<Columns> queryWrapper = Wrappers.<Columns>lambdaQuery(Columns.class);
        queryWrapper.eq(Columns::getTableSchema, database)
                .eq(Columns::getTableName, table);
        return columnsMapper.selectList(queryWrapper);
    }

}
