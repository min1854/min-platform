package com.old.business.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜单层级关系 对象 old_menu_path
 *
 * @author old
 * @date 2022-10-17
 */
@Data
public class OldMenuPath implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 祖先节点
     */
    private Long ancestor;

    /**
     * 后代节点
     */
    private Long descendant;

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
