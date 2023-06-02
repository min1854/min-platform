package com.old.business.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.old.business.user.enums.user.OldNoticeEnums;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 通知公告表 对象 old_notice
 *
 * @author old
 * @date 2023-05-20
 */
@Data
@FieldNameConstants
public class OldNotice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     * 公告ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * notice_title
     * 公告标题
     */
    private String noticeTitle;

    /**
     * notice_type
     * 公告类型（通知 公告）
     */
    private OldNoticeEnums.NoticeType noticeType;

    /**
     * notice_content
     * 公告内容
     */
    private String noticeContent;

    /**
     * notice_status
     * 公告状态（0正常 1关闭）
     */
    private OldNoticeEnums.NoticeStatus noticeStatus;

    /**
     * create_time
     * 创建时间
     */
    private LocalDateTime createTime;

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
