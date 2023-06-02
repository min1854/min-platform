package com.old.common.domain.login.html;

import com.old.common.domain.login.UserBo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HtmlUserBo implements UserBo {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    private String phone;

    private String nickName;

    private String email;

    private Integer deptId;

    private String profile;

    private String userType;

    private Integer sex;

    private LocalDateTime createTime;

}
