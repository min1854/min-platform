package com.old.business.user.domain.req;

import com.old.business.user.enums.user.OldUserEnums;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class SearchUserReq implements Serializable {

    private String userName;
    private String phone;
    private OldUserEnums.UserStatusEnum userStatus;
    private LocalDate startTime;
    private LocalDate endTime;
    private Integer deptId;
}
