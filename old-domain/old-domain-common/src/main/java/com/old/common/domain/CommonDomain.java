package com.old.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommonDomain implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;


    private LocalDateTime createTime;
    private String createUser;
    private Integer createUserId;

    private LocalDateTime updateTime;
    private String updateUser;
    private Integer updateUserId;

    private Boolean deleteFlag;

    private Boolean isFiled;

    private boolean isBaseBooleanFiled;
}
