package com.old.common.file.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件 对象 old_upload_file
 *
 * @author old
 * @date 2023-05-26
 */
@Data
@FieldNameConstants
public class OldUploadFile implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * db_file
     * 文件
     */
    private byte[] dbFile;

    /**
     * file_type
     * 文件类型
     */
    private String fileType;

    /**
     * create_time
     * 创建时间
     */
    private LocalDateTime createTime;

}
