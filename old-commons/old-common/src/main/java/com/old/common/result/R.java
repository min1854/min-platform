package com.old.common.result;

import com.old.common.base.BaseResult;
import com.old.common.base.BaseResultEnum;
import com.old.common.enums.ResultEnum;
import com.old.common.util.JsonUtil;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class R<T> extends BaseResult<T> {


    public static final R<Void> SUCCESS = new R<>(ResultEnum.SUCCESS);
    public static final R<Void> FAIL = new R<>(ResultEnum.FAIL);
    public static final R<Void> FEIGN_ERROR = new R<>(ResultEnum.FEIGN_ERROR);


    public R(BaseResultEnum resultEnum) {
        super(resultEnum);
    }

    public R(BaseResultEnum resultEnum, T data) {
        super(resultEnum, data);
    }

    public static R<Void> ok() {
        return SUCCESS;
    }

    public static <T> R<T> ok(T data) {
        return new R<>(ResultEnum.SUCCESS, data);
    }

    public static R<Void> fail() {
        return FAIL;
    }

    public static R<Void> feignError() {
        return FEIGN_ERROR;
    }

    public static R<Void> r(BaseResultEnum resultEnum) {
        return new R<>(resultEnum);
    }

    public String toJson() {
        return JsonUtil.toJson(this);
    }

}
