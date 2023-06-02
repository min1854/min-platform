package com.old.business.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.old.business.user.enums.user.OldPostEnums;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 岗位信息表 对象 old_post
 *
 * @author old
 * @date 2023-05-20
 */
@Data
@FieldNameConstants
public class OldPost implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     * 岗位ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * post_code
     * 岗位编码
     */
    private String postCode;

    /**
     * post_name
     * 岗位名称
     */
    private String postName;

    /**
     * post_sort
     * 显示顺序
     */
    private Integer postSort;

    /**
     * remark
     * 备注
     */
    private String remark;

    /**
     * create_time
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * post_status
     * 状态（0正常 1停用）
     */
    private OldPostEnums.PostStatus postStatus;

    /**
     * create_user_id
     * 创建用户id
     */
    private Integer createUserId;

    /**
     * create_user
     * 创建用户
     */
    private String createUser;

    /**
     * update_time
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * update_user_id
     * 更新用户id
     */
    private Integer updateUserId;

    /**
     * update_user
     * 更新用户
     */
    private String updateUser;

    /**
     * delete_flag
     * 删除标识
     */
    private Boolean deleteFlag;

}
