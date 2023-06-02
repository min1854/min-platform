package com.old.common.domain.login;

import java.io.Serializable;

public interface RoleBo extends Serializable {

    public static final Integer ADMIN_ID = 1;

    Integer getId();

    String getRoleName();
}
