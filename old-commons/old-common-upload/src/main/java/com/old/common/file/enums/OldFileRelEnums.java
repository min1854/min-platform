package com.old.common.file.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 文件引用表 实体的枚举类 old_file_rel
 *
 * @author old
 * @date 2023-06-02
 */
public interface OldFileRelEnums {


    /**
     * rel_type
     * 引用类型，以区分是哪个表
     */
    @Getter
    @ToString
    @RequiredArgsConstructor
    enum RelType {
        OLD_WORKER_ACTIVITIES("工人动态表"),
        ;

        private final String desc;

    }

}
