package com.old.business.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.business.SpringBootTests;
import com.old.business.user.domain.req.SearchUserReq;
import com.old.business.user.enums.user.OldUserEnums;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.function.Consumer;

public class OldUserTests extends SpringBootTests {

    @Test
    public void searchPage() {
        Consumer<SearchUserReq> search = req -> {
            echoDefaultJsonResult(oldUserMapper.searchPage(req, OldUserEnums.UserTypeEnum.SYSTEM, Page.of(1, 10)));
        };
        SearchUserReq req = new SearchUserReq();
        search.accept(req);

        req.setUserName("old");
        req.setPhone("130");
        req.setUserStatus(OldUserEnums.UserStatusEnum.ENABLE);
        req.setStartTime(LocalDate.of(2021, 1, 1));
        req.setEndTime(LocalDate.of(2024, 1, 1));
//
        search.accept(req);

        req.setDeptId(100);
        search.accept(req);

    }
}
