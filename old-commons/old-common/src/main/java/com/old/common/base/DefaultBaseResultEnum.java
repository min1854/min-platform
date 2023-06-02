package com.old.common.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
@AllArgsConstructor
public class DefaultBaseResultEnum implements BaseResultEnum {

    private final Integer code;
    private final String message;
}
