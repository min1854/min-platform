package com.old.business.user.enums.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 岗位信息表 实体的枚举类 old_post
 *
 * @author old
 * @date 2023-05-20
 */
public interface OldPostEnums {


    @Getter
    @ToString
    @RequiredArgsConstructor
    enum PostStatus {
        // 状态（0正常 1停用）
        ENABLE("启用"),
        DISABLE("禁用"),
        ;

        private final String desc;

    }

}
