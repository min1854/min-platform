package com.old.business.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单表 对象 old_order
 *
 * @author old
 * @date 2022-10-17
 */
@Data
public class OldOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 扩展 json
     */
    private String extJson;

    /**
     * 订单价格
     */
    private BigDecimal price;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建用户id
     */
    private Integer createUserId;

    /**
     * 创建用户
     */
    private String createUser;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新用户id
     */
    private Integer updateUserId;

    /**
     * 更新用户
     */
    private String updateUser;

    /**
     * 删除标识
     */
    private Boolean deleteFlag;

}
