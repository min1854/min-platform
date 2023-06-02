package com.old.generator;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.old.generator.domain.Columns;
import com.old.generator.domain.Tables;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;

public class GeneratorTests extends SpringBootTests {

    @Test
    public void selectList() {
        LambdaQueryWrapper<Tables> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Tables::getTableName, "old");
        tablesMapper.selectList(queryWrapper).forEach(System.out::println);
    }

    @Test
    public void columnsSelectList() {
        columnsMapper.selectList(new LambdaQueryWrapper<>(Columns.class).like(Columns::getTableName, "old_order")).forEach(System.out::println);
    }

    @Test
    public void selectByDataBaseAndTables() {
        String database = "old";
        List<String> tables = List.of("old_order");
        atLastLog("有表的数据的查询", tablesService.selectByDataBaseAndTables(database, tables, null));

        tables = null;
        atLastLog("无表的数据的查询", tablesService.selectByDataBaseAndTables(database, tables, null));
    }

    @Test
    public void selectByDatabaseAndTable() {
        String database = "old";
        String table = "old_order";
        atLastLog("查找表字段", columnsService.selectByDatabaseAndTable(database, table));
    }

    public void t() {
        DefaultResultSetHandler resultSetHandler = new DefaultResultSetHandler(null, null, null, null, null, null);

    }


}
