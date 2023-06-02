package com.old.generator.service;

import com.old.common.mybatis.base.BaseService;
import com.old.generator.domain.Tables;

import java.util.Collection;
import java.util.List;
import java.util.Set;


public interface TablesService extends BaseService<Tables> {
    List<Tables> selectByDataBaseAndTables(String database, Collection<String> tables, Set<String> dontNeedTablesName);
}
