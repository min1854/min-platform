package com.old.business.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.old.business.user.enums.user.OldUserEnums;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表 对象 old_user
 *
 * @author old
 * @date 2023-04-11
 */
@Data
@FieldNameConstants
public class OldUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * dept_id
     * 部门ID
     */
    private Integer deptId;

    /**
     * user_name
     * 用户名
     */
    private String userName;

    /**
     * user_status
     * 用户状态：0：启用，1：禁用
     */
    private OldUserEnums.UserStatusEnum userStatus;

    /**
     * nick_name
     * 用户昵称
     */
    private String nickName;

    /**
     * user_type
     * 用户类型（00系统用户）
     */
    private OldUserEnums.UserTypeEnum userType;

    /**
     * email
     * 用户邮箱
     */
    private String email;

    /**
     * phone
     * 手机号码
     */
    private String phone;

    /**
     * sex
     * 用户性别（0男 1女 2未知）
     */
    private OldUserEnums.SexEnum sex;

    /**
     * password
     * 密码
     */
    private String password;

    /**
     * profile
     * 头像
     */
    private String profile;

    /**
     * login_ip
     * 最后登录IP
     */
    private String loginIp;

    /**
     * login_date
     * 最后登录时间
     */
    private LocalDateTime loginDate;

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
