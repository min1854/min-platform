package com.old.business.user.domain.resp;

import com.old.business.user.domain.req.DeptTreeResp;

import java.io.Serializable;
import java.util.List;

public record RoleDeptTreeResp(List<Integer> checkedKeys, List<DeptTreeResp> depts) implements Serializable {
}
