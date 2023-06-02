package com.old.business.user.enums.user;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.old.common.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 用户表 实体的枚举类 old_user
 *
 * @author old
 * @date 2023-04-11
 */
public interface OldUserEnums {


    @Getter
    @ToString
    @AllArgsConstructor
    enum UserStatusEnum implements BaseEnum<UserStatusEnum> {
        // 用户状态：0：启用，1：禁用
        ENABLE(0, "启用"),
        DISABLE(1, "禁用"),
        ;

        @EnumValue
        private final Integer value;
        private final String desc;

        public static UserStatusEnum select(Integer value) {
            for (UserStatusEnum userStatusEnum : values()) {
                if (userStatusEnum.getValue().equals(value)) {
                    return userStatusEnum;
                }
            }
            return null;
        }

        @Override
        public Enum<UserStatusEnum> self() {
            return this;
        }

        public boolean valueEquals(Integer value) {
            return this.getValue().equals(value);
        }

    }

    @Getter
    @ToString
    @AllArgsConstructor
    enum UserTypeEnum implements BaseEnum<UserTypeEnum> {
        // 用户类型（00系统用户）
        SYSTEM("00", "系统用户"),
        ;

        @EnumValue
        private final String value;
        private final String desc;

        public static UserTypeEnum select(String value) {
            for (UserTypeEnum userTypeEnum : values()) {
                if (userTypeEnum.getValue().equals(value)) {
                    return userTypeEnum;
                }
            }
            return null;
        }

        @Override
        public Enum<UserTypeEnum> self() {
            return this;
        }

        public boolean valueEquals(String value) {
            return this.getValue().equals(value);
        }

    }

    @Getter
    @ToString
    @AllArgsConstructor
    enum SexEnum implements BaseEnum<SexEnum> {
        // 用户性别（0男 1女 2未知）
        MALE(0, "男"),
        FEMALE(1, "女"),
        UNKNOWN(2, "未知"),
        ;

        @EnumValue
        private final Integer value;
        private final String desc;

        public static SexEnum select(Integer value) {
            for (SexEnum sexEnum : values()) {
                if (sexEnum.getValue().equals(value)) {
                    return sexEnum;
                }
            }
            return null;
        }

        @Override
        public Enum<SexEnum> self() {
            return this;
        }

        public boolean valueEquals(Integer value) {
            return this.getValue().equals(value);
        }

    }

}
