package com.old.business.user.domain.req;

import com.old.business.user.enums.user.OldUserEnums;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AddUserReq implements Serializable {

    /**
     * id
     * 主键
     */
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

    private List<Integer> postIds;

    private List<Integer> roleIds;


}
