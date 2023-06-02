package com.old.business.user;

import com.old.business.SpringBootTests;
import com.old.business.user.domain.req.DeptTreeResp;
import org.junit.jupiter.api.Test;

import java.util.List;

public class OldDeptTests extends SpringBootTests {

    @Test
    public void deptTree() {
        List<DeptTreeResp> deptTreeRespList = oldDeptService.deptTree();
        echoDefaultJsonResult(deptTreeRespList);
//        debug("结果：{}", deptTreeRespList);
    }
}
