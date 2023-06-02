package com.old.common.base;

import cn.hutool.core.util.StrUtil;

public interface BaseResultEnum {

    default BaseResultEnum format(Object... params) {
        return new DefaultBaseResultEnum(this.getCode(), StrUtil.format(this.getMessage(), params));
    }

    Integer getCode();

    String getMessage();
}
