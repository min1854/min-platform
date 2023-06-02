package com.old.common.file.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.old.common.file.enums.OldFileRelEnums;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件引用表 对象 old_file_rel
 *
 * @author old
 * @date 2023-06-02
 */
@Data
@FieldNameConstants
public class OldFileRel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * file_id
     * 用户 id
     */
    private Integer fileId;

    /**
     * rel_id
     * 引用 id
     */
    private Integer relId;

    /**
     * rel_type
     * 引用类型，以区分是哪个表
     */
    private OldFileRelEnums.RelType relType;

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
