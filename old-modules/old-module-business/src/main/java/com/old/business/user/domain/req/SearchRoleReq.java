package com.old.business.user.domain.req;

import cn.hutool.core.date.DatePattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class SearchRoleReq implements Serializable {
    private Integer id;

    private String roleName;

    private Boolean roleStatus;

    private String roleKey;

    @DateTimeFormat(pattern = DatePattern.NORM_DATE_PATTERN)
    private LocalDate beginTime;

    @DateTimeFormat(pattern = DatePattern.NORM_DATE_PATTERN)
    private LocalDate endTime;
}
