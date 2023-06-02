package com.old.business.user.enums.user;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.old.common.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * 菜单权限表 实体的枚举类 old_menu
 *
 * @author old
 * @date 2023-04-02
 */
public interface OldMenuEnums {


    @Getter
    @ToString
    @AllArgsConstructor
    enum ComponentEnum implements BaseEnum<ComponentEnum> {
        // 菜单类型（M目录 C菜单 F按钮）
        LAYOUT("Layout", "Layout组件标识"),
        PARENT_VIEW("ParentView", "ParentView组件标识"),
        INNER_LINK("InnerLink", "InnerLink组件标识"),
        ;

        @EnumValue
        private final String value;
        private final String desc;

        @Override
        public Enum<ComponentEnum> self() {
            return this;
        }


        public boolean valueEquals(String value) {
            return Objects.equals(this.getValue(), value);
        }

        public ComponentEnum select(String value) {
            for (ComponentEnum componentEnum : values()) {
                if (componentEnum.getValue().equals(value)) {
                    return componentEnum;
                }
            }
            return null;
        }

    }


    @Getter
    @ToString
    @AllArgsConstructor
    enum MenuTypeEnum implements BaseEnum<MenuTypeEnum> {
        // 菜单类型（M目录 C菜单 F按钮）
        DIR("M", "目录"),
        MENU("C", "菜单"),
        BUTTON("F", "按钮"),
        ;

        @EnumValue
        private final String value;
        private final String desc;

        @Override
        public Enum<MenuTypeEnum> self() {
            return this;
        }


        public boolean valueEquals(String value) {
            return this.getValue().equals(value);
        }

    }

    @Getter
    @ToString
    @AllArgsConstructor
    enum MenuVisibleEnum implements BaseEnum<MenuVisibleEnum> {
        // 菜单状态（0显示 1隐藏）
        SHOW(0, "显示"),
        HIDDEN(1, "隐藏"),
        ;

        @EnumValue
        private final Integer value;
        private final String desc;

        @Override
        public Enum<MenuVisibleEnum> self() {
            return this;
        }

    }

    @Getter
    @ToString
    @AllArgsConstructor
    enum MenuStatusEnum implements BaseEnum<MenuStatusEnum> {
        // 菜单状态（0正常 1停用）
        USE(0, "正常"),
        STOP(1, "停用"),
        ;

        @EnumValue
        private final Integer value;
        private final String desc;

        @Override
        public Enum<MenuStatusEnum> self() {
            return this;
        }

    }

}
