package com.old.common.exception;

import com.old.common.base.BaseException;
import com.old.common.base.BaseResultEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultException extends BaseException {

    private BaseResultEnum resultEnum;

    public ResultException(BaseResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.resultEnum = resultEnum;
    }

    public ResultException(BaseResultEnum resultEnum, Throwable cause) {
        super(resultEnum.getMessage(), cause);
        this.resultEnum = resultEnum;
    }

}
