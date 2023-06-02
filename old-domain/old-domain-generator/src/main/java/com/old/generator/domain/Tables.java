package com.old.generator.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Tables implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * table_catalog
     */
    private String tableCatalog;

    /**
     * table_schema
     */
    private String tableSchema;

    /**
     * table_name
     */
    private String tableName;

    /**
     * table_type
     */
    private String tableType;

    /**
     * engine
     */
    private String engine;

    /**
     * version
     */
    private Long version;

    /**
     * row_format
     */
    private String rowFormat;

    /**
     * table_rows
     */
    private Long tableRows;

    /**
     * avg_row_length
     */
    private Long avgRowLength;

    /**
     * data_length
     */
    private Long dataLength;

    /**
     * max_data_length
     */
    private Long maxDataLength;

    /**
     * index_length
     */
    private Long indexLength;

    /**
     * data_free
     */
    private Long dataFree;

    /**
     * auto_increment
     */
    private Long autoIncrement;

    /**
     * create_time
     */
    private Date createTime;

    /**
     * update_time
     */
    private Date updateTime;

    /**
     * check_time
     */
    private Date checkTime;

    /**
     * table_collation
     */
    private String tableCollation;

    /**
     * checksum
     */
    private Long checksum;

    /**
     * create_options
     */
    private String createOptions;

    /**
     * table_comment
     */
    private String tableComment;

}