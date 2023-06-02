package com.old.common.apiAssert;

import com.old.common.base.BaseResultEnum;
import com.old.common.exception.ResultException;
import io.github.min1854.apiAssert.api.StandardApiAssert;
import io.github.min1854.apiAssert.check.abstractAssert.AbstractOperationApiAssert;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class ResultAssertGenerator {
    public static <ELEMENT> ResultEnumAssert<ELEMENT> create(Supplier<ELEMENT> element) {
        return new ResultEnumAssert<>(element.get(), ResultException::new);
    }

    public static <ELEMENT> ResultEnumAssert<ELEMENT> create(ELEMENT element) {
        return new ResultEnumAssert<>(element, ResultException::new);
    }


    public static class ResultEnumAssert<ELEMENT> extends AbstractOperationApiAssert<ELEMENT, ResultEnumAssert<ELEMENT>, BaseResultEnum, Object> {


        public ResultEnumAssert(ELEMENT obj, Function<BaseResultEnum, RuntimeException> exceptionGenerator) {
            super(obj, exceptionGenerator);
        }

        @Override
        public <ELEMENT> ResultEnumAssert<ELEMENT> then(ELEMENT element) {
            return new ResultEnumAssert<>(element, this.exceptionGenerator);
        }

        @Override
        public <ELEMENT> ResultEnumAssert<ELEMENT> then(Supplier<ELEMENT> supplier) {
            return new ResultEnumAssert<>(supplier.get(), this.exceptionGenerator);
        }

        @Override
        public <RESULT> ResultEnumAssert<RESULT> then(Function<ELEMENT, RESULT> function) {
            return new ResultEnumAssert<>(function.apply(this.obj), this.exceptionGenerator);
        }

        @Override
        public <RESULT> ResultEnumAssert<RESULT> then(BiFunction<ELEMENT, StandardApiAssert<Object, ResultEnumAssert<ELEMENT>, BaseResultEnum>, RESULT> biFunction) {
            return new ResultEnumAssert<>(biFunction.apply(this.obj, this), this.exceptionGenerator);
        }

        @Override
        public ResultEnumAssert<ELEMENT> self() {
            return this;
        }
    }
}
