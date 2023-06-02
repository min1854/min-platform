package com.old.business.user.domain.req;

import com.old.business.user.domain.OldRole;
import com.old.business.user.enums.user.OldRoleEnums;
import lombok.Builder;

import java.util.List;

@Builder
public record RoleDataScopeReq(Integer id, OldRoleEnums.DataScope dataScope, List<Integer> deptIds) {

    public OldRole toRole() {
        OldRole oldRole = new OldRole();
        oldRole.setDataScope(this.dataScope);
        oldRole.setId(this.id);
        return oldRole;
    }
}
