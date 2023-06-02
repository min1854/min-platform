package com.old.business.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.business.SpringBootTests;
import com.old.business.user.domain.OldRole;
import com.old.business.user.domain.req.SearchRoleReq;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.function.Consumer;

public class OldRoleTests extends SpringBootTests {

    @Test
    public void searchRolePage() {

        Consumer<SearchRoleReq> consumer = req -> {
            Page<OldRole> oldRolePage = oldRoleMapper.searchRolePage(req, new Page<>(1, 10));
            echoDefaultJsonResult(oldRolePage);
        };
        SearchRoleReq req = new SearchRoleReq();
        consumer.accept(req);


        req.setId(1);
        req.setRoleName("admin");
        req.setRoleStatus(Boolean.TRUE);
        req.setRoleKey("*");
        req.setBeginTime(LocalDate.of(2022, 1, 1));
        req.setEndTime(LocalDate.of(2025, 1, 1));
        consumer.accept(req);

    }
}
