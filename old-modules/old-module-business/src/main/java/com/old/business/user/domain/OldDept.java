package com.old.business.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 部门表 对象 old_dept
 *
 * @author old
 * @date 2023-05-14
 */
@Data
@FieldNameConstants
public class OldDept implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     * 部门id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * parent_id
     * 父部门id
     */
    private Integer parentId;

    /**
     * ancestors
     * 祖级列表
     */
    private String ancestors;

    /**
     * dept_name
     * 部门名称
     */
    private String deptName;

    /**
     * order_num
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * leader
     * 负责人
     */
    private String leader;

    /**
     * phone
     * 联系电话
     */
    private String phone;

    /**
     * email
     * 邮箱
     */
    private String email;

    /**
     * dept_status
     * 部门状态（0停用 1正常）
     */
    private Boolean deptStatus;

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
