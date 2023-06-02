package com.old.common.web.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum CheckMode {
    AND(0, "并且"),
    OR(1, "或者"),
    ;

    private final Integer value;
    private final String String;
}
