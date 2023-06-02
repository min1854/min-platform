package com.old.business.user.domain.req;

import com.old.business.user.enums.user.OldUserEnums;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateUserProfileReq implements Serializable {


    /**
     * id
     * 主键
     */
    private Integer id;


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
}
