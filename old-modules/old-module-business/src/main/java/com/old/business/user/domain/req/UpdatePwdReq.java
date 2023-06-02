package com.old.business.user.domain.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdatePwdReq implements Serializable {
    @NotBlank(message = "密码不符合规范")
    private String oldPassword;


    @NotBlank(message = "密码不符合规范")
    private String newPassword;
}
