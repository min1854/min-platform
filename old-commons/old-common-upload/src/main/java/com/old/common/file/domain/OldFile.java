package com.old.common.file.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 对象 old_file
 *
 * @author old
 * @date 2023-06-02
 */
@Data
@FieldNameConstants
public class OldFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * original_file_name
     * 原文件名
     */
    private String originalFileName;

    /**
     * file_type
     * 文件类型
     */
    private String fileType;

    /**
     * file_name
     * 文件名
     */
    private String fileName;

    /**
     * file_server
     * 文件服务器地址
     */
    private String fileServer;

    /**
     * file_path
     * 文件路径
     */
    private String filePath;

    /**
     * x80
     * 80%的文件（压缩了20%）压缩后的文件路径名：原文件名-x80，下面的以此类推
     */
    private String x80;

    /**
     * x50
     * 50%的文件（压缩了50%）
     */
    private String x50;

    /**
     * x20
     * 20%的文件（压缩了80%）
     */
    private String x20;

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
