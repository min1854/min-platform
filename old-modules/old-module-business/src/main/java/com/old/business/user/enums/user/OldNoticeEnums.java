package com.old.business.user.enums.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 通知公告表 实体的枚举类 old_notice
 *
 * @author old
 * @date 2023-05-20
 */
public interface OldNoticeEnums {


    @Getter
    @ToString
    @RequiredArgsConstructor
    enum NoticeType {
        // 公告类型（通知 公告）
        NOTICE("通知"),
        ANNOUNCEMENT("公告"),
        ;

        private final String desc;

    }

    @Getter
    @ToString
    @RequiredArgsConstructor
    enum NoticeStatus {
        // 公告状态（0正常 1关闭）
        NORMAL("正常"),
        CLOSE("关闭"),
        ;

        private final String desc;

    }

}
