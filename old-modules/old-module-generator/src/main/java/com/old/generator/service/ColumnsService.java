package com.old.generator.service;

import com.old.common.mybatis.base.BaseService;
import com.old.generator.domain.Columns;

import java.util.List;


public interface ColumnsService extends BaseService<Columns> {
    List<Columns> selectByDatabaseAndTable(String database, String table);
}
