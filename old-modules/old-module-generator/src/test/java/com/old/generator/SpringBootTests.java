package com.old.generator;

import com.old.common.base.BaseTest;
import com.old.generator.mapper.ColumnsMapper;
import com.old.generator.mapper.TablesMapper;
import com.old.generator.service.ColumnsService;
import com.old.generator.service.GeneratorService;
import com.old.generator.service.TablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class SpringBootTests extends BaseTest {

    @Autowired
    public TablesMapper tablesMapper;
    @Autowired
    public ColumnsMapper columnsMapper;

    @Autowired
    public GeneratorService generatorService;

    @Autowired
    public TablesService tablesService;

    @Autowired
    public ColumnsService columnsService;

    @Autowired
    public JdbcTemplate jdbcTemplate;
}
