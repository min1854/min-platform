package com.old.business.user.domain;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色和部门关联表 对象 old_role_dept
 *
 * @author old
 * @date 2023-04-25
 */
@Data
@FieldNameConstants
public class OldRoleDept implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * role_id
     * 角色ID
     */
    private Integer roleId;

    /**
     * dept_id
     * 部门ID
     */
    private Integer deptId;

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
