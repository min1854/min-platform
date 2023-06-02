package com.old.common.domain.login;

import java.io.Serializable;

public interface UserBo extends Serializable {

    Integer getId();

    String getUserName();

    String getPassword();

    String getPhone();
}
