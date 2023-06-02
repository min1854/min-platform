package com.old.generator.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.old.common.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface ColumnsEnums {

    @Getter
    @AllArgsConstructor
    enum ColumnKeyEnum implements BaseEnum<ColumnKeyEnum> {
        PRIMARY_KEY("PRI", "主键"),
        ;

        @EnumValue
        private String value;
        private String desc;

        @Override
        public Enum<ColumnKeyEnum> self() {
            return this;
        }
    }
}
