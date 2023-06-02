package com.old.business.user.domain.resp;

import com.old.business.user.enums.user.OldUserEnums;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class GetUserProfileResp implements Serializable {


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


    private String nickName;

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


    private LocalDateTime createTime;


    private String deptName;

    private String postGroup;
    private String roleGroup;
}
