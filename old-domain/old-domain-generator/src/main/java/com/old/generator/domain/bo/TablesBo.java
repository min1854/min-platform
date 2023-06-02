package com.old.generator.domain.bo;

import com.old.generator.domain.Tables;
import lombok.Data;

import java.util.*;

@Data
public class TablesBo extends Tables {


    private String requestPath;

    /**
     * 类名称，大写
     */
    private String className = "";

    /**
     * 小写的类名称
     * 这是小写，不如说是对象名称
     */
    private String objectName = "";

    /**
     * 非 lang 包需要引入的包名
     */
    private Set<String> importList;

    /**
     * 主键的 java 类型
     */
    private String primaryKeyJavaType;

    /**
     * 主键字段名
     */
    private String primaryKeyJavaFiledName;

    /**
     * 主键大写的 java 字段名
     */
    private String primaryKeyEnumName;

    private String table = "";
    private Integer parentMenuId = 0;
    private String treeCode = "";
    private String treeName = "";
    private String treeParentCode = "";
    private List<Map<String, ColumnsBo>> ymlFields = new ArrayList<>();
    private Map<String, ColumnsBo> fields = new HashMap<>();
}