package com.old.business.user.domain.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResetPwdReq implements Serializable {
    @NotNull(message = "用户不可为空")
    private Integer id;


    @NotBlank(message = "密码不符合规范")
    private String password;
}
