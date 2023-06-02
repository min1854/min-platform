package com.old.common.mybatis.base;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.old.common.apiAssert.ResultAssertHolder;

public interface BaseService<T> extends IService<T> {
    @Deprecated
    LambdaQueryWrapper<T> lqw();

    @Deprecated
    default ResultAssertHolder.ResultAssert apiAssert() {
        return ResultAssertHolder.apiAssert();
    }
}
