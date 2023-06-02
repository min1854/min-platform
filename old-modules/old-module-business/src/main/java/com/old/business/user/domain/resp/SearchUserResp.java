package com.old.business.user.domain.resp;

import com.old.business.user.enums.user.OldUserEnums;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SearchUserResp implements Serializable {

    private Integer id;
    private String userName;
    private String nickName;
    private String phone;
    private OldUserEnums.UserStatusEnum userStatus;
    private LocalDateTime createTime;
    private Integer deptId;
    private String deptName;


}
