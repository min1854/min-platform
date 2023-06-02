package com.old.common.domain.login;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface LoginUser extends Serializable {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final int DEFAULT_USER_ID = 1;
    public static final String DEFAULT_USER_NAME = "old";

    UserBo getUserBo();

    List<RoleBo> getRoleBos();

    List<MenuBo> getMenuBos();

    /**
     * 没用的感觉
     *
     * @return
     */
    @Deprecated
    Set<String> getMenuPaths();

    Integer loginUserId();

    String loginUserName();

    List<RouterBo> getRouters();

    default String getPhone() {
        return getUserBo().getPhone();
    }

    default boolean isDefaultUser() {
        return LoginUser.DEFAULT_USER_ID == loginUserId();
    }


    default List<Integer> roleIds() {
        return getRoleBos().stream().map(RoleBo::getId).toList();
    }
}
