package com.old.business.user.domain.resp;

import com.old.business.user.domain.OldRole;
import lombok.Data;

@Data
public class AuthMyRoleResp extends OldRole {
    private Boolean flag;
}
