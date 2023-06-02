package com.old.business.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.old.business.user.enums.user.OldRoleEnums;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色表 对象 old_role
 *
 * @author old
 * @date 2023-04-25
 */
@Data
@FieldNameConstants
public class OldRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * role_name
     * 角色名
     */
    private String roleName;

    /**
     * role_key
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * role_sort
     * 显示顺序
     */
    private Integer roleSort;

    /**
     * data_scope
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    private OldRoleEnums.DataScope dataScope;

    /**
     * menu_check_strictly
     * 菜单树选择项是否关联显示
     */
    private Boolean menuCheckStrictly;

    /**
     * dept_check_strictly
     * 部门树选择项是否关联显示
     */
    private Boolean deptCheckStrictly;

    /**
     * role_status
     * 角色状态 false 停用，true 使用
     */
    private Boolean roleStatus;

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

    /**
     * update_time
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * update_user_id
     * 更新用户id
     */
    private Integer updateUserId;

    /**
     * update_user
     * 更新用户
     */
    private String updateUser;

    /**
     * delete_flag
     * 删除标识
     */
    private Boolean deleteFlag;

}
