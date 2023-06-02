package com.old.common.apiAssert;

import com.old.apiAssert.check.abstractAssert.AbstractApiAssert;
import com.old.common.base.BaseResultEnum;
import com.old.common.exception.ResultException;

import java.util.function.Supplier;

public class ResultAssertHolder {


    public static ResultAssert apiAssert() {
        return Internal.INTERNAL_API_ASSERT;
    }

    private static class Internal {
        private static final ResultAssert INTERNAL_API_ASSERT = new ResultAssert();
    }


    public static class ResultAssert extends AbstractApiAssert<Object, ResultAssert, BaseResultEnum> {

        @Override
        protected void established(BaseResultEnum message) throws RuntimeException {
            throw new ResultException(message);
        }

        @Override
        public ResultAssert self() {
            return this;
        }


        public <ELEMENT> ResultAssertGenerator.ResultEnumAssert<ELEMENT> of(Supplier<ELEMENT> element) {
            return ResultAssertGenerator.create(element);
        }

        public <ELEMENT> ResultAssertGenerator.ResultEnumAssert<ELEMENT> of(ELEMENT element) {
            return ResultAssertGenerator.create(element);
        }

        @Override
        public ResultAssert handler(BaseResultEnum baseResultEnum) {
            throw new ResultException(baseResultEnum);
        }
    }

}
