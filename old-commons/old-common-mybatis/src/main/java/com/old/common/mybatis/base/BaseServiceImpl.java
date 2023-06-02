package com.old.common.mybatis.base;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.old.common.apiAssert.ResultAssertHolder;


public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    public ResultAssertHolder.ResultAssert apiAssert = ResultAssertHolder.apiAssert();

    @Deprecated
    @Override
    public LambdaQueryWrapper<T> lqw() {
        return Wrappers.lambdaQuery(this.entityClass);
    }
}
