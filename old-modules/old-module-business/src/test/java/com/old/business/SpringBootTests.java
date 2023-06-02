package com.old.business;

import com.old.business.user.controller.OldMenuController;
import com.old.business.user.mapper.OldMenuMapper;
import com.old.business.user.mapper.OldRoleMapper;
import com.old.business.user.mapper.OldUserMapper;
import com.old.business.user.service.OldDeptService;
import com.old.business.user.service.impl.OldMenuServiceImpl;
import com.old.common.base.BaseTest;
import com.old.common.file.controller.OldUploadFileController;
import com.old.common.upload.controller.FileController;
import io.minio.MinioClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@SpringBootTest(classes = OldBusinessApplication.class)
public class SpringBootTests extends BaseTest {


    @Autowired
    public FileController fileController;

    @Autowired
    public OldUploadFileController oldUploadFileController;

    @Autowired
    public OldMenuController oldMenuController;


    @Autowired
    public OldDeptService oldDeptService;


    @Autowired
    public OldMenuServiceImpl oldMenuServiceImpl;

    @Autowired
    public MinioClient minioClient;


    @Autowired
    public ConfigurableEnvironment environment;

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Autowired
    public OldMenuMapper oldMenuMapper;

    @Autowired
    public OldRoleMapper oldRoleMapper;

    @Autowired
    public OldUserMapper oldUserMapper;

    @Test
    public void showMaxUsedConnections() {
        jdbcTemplate.query("SHOW STATUS LIKE 'Max_used_connections';", new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                System.out.println(rs.getObject(1));
            }
        });
    }


    @Test
    public void echConfig() {
        debug("启用的配置值：{}", Arrays.toString(environment.getActiveProfiles()));
        for (PropertySource<?> propertySource : environment.getPropertySources()) {
            debug("名称：{}，值：{}", propertySource.getName(), propertySource.getSource());
        }
    }
}
