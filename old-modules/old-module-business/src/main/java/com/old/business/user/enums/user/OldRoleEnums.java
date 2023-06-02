package com.old.business.user.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 角色表 实体的枚举类 old_role
 *
 * @author old
 * @date 2023-05-08
 */
public interface OldRoleEnums {


    @Getter
    @ToString
    @AllArgsConstructor
    enum DataScope {
        // 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
        ALL("全部数据权限"),
        CUSTOMIZE("自定数据权限"),
        CURRENT_DEPT("本部门数据权限"),
        CURRENT_DEPT_AND_BELOW("本部门及以下数据权限"),
        INDIVIDUAL("个人数据"),
        ;

        private final String desc;

    }

}
