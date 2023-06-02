package com.old.business.user.domain;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户角色关联表 对象 old_user_role_rel
 *
 * @author old
 * @date 2023-05-14
 */
@Data
@FieldNameConstants
public class OldUserRoleRel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * user_id
     * 用户 id
     */
    private Integer userId;

    /**
     * role_id
     * 角色 id
     */
    private Integer roleId;

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
