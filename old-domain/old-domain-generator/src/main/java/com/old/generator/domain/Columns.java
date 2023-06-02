package com.old.generator.domain;

import com.old.generator.enums.ColumnsEnums;
import lombok.Data;

import java.io.Serializable;

@Data
public class Columns implements Serializable {

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
     * column_name
     */
    private String columnName;

    /**
     * ordinal_position
     */
    private Long ordinalPosition;

    /**
     * column_default
     */
    private String columnDefault;

    /**
     * is_nullable
     */
    private String isNullable;

    /**
     * data_type
     */
    private String dataType;

    /**
     * character_maximum_length
     */
    private Long characterMaximumLength;

    /**
     * character_octet_length
     */
    private Long characterOctetLength;

    /**
     * numeric_precision
     */
    private Long numericPrecision;

    /**
     * numeric_scale
     */
    private Long numericScale;

    /**
     * datetime_precision
     */
    private Long datetimePrecision;

    /**
     * character_set_name
     */
    private String characterSetName;

    /**
     * collation_name
     */
    private String collationName;

    /**
     * column_type
     */
    private String columnType;

    /**
     * column_key
     */
    private ColumnsEnums.ColumnKeyEnum columnKey;

    /**
     * extra
     */
    private String extra;

    /**
     * privileges
     */
    private String privileges;

    /**
     * column_comment
     */
    private String columnComment;

    /**
     * generation_expression
     */
    private String generationExpression;
}