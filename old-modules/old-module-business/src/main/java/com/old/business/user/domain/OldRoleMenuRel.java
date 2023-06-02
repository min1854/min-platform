package com.old.business.user.domain;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色菜单关联表 对象 old_role_menu_rel
 *
 * @author old
 * @date 2023-05-20
 */
@Data
@FieldNameConstants
public class OldRoleMenuRel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * role_id
     * 角色 id
     */
    private Integer roleId;

    /**
     * menu_id
     * 菜单 id
     */
    private Integer menuId;

    /**
     * create_time
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * create_user_id
     * 创建用户id
     */
    private Integer createUserId;

    /**
     * create_user
     * 创建用户
     */
    private String createUser;

}
