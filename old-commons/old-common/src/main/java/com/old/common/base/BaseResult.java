package com.old.common.base;

import com.old.common.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BaseResult<T> implements Serializable {

    private String message;
    private Integer code;
    private T data;

    public BaseResult(BaseResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    public BaseResult(BaseResultEnum resultEnum, T data) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
        this.data = data;
    }

    public boolean success() {
        return ResultEnum.SUCCESS.getCode().equals(this.getCode());
    }

    public boolean nonSuccess() {
        return !success();
    }

    public boolean ifFeignError() {
        return ResultEnum.FEIGN_ERROR.getCode().equals(this.getCode());
    }
}
