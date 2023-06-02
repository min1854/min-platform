package com.old.business.user.domain.resp;

import com.old.business.user.domain.OldUser;

import java.util.List;

public record AuthRoleResp(OldUser user, List<AuthMyRoleResp> roles) {
}
