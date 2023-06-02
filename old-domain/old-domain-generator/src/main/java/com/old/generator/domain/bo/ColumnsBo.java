package com.old.generator.domain.bo;

import com.old.generator.domain.Columns;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 本身 boolean 类型不会为空，去除了 is 前缀，也是可以接受，就不额外创建方法了
 */
@Data
public class ColumnsBo extends Columns {

    /**
     * 驼峰模式的字段名
     */
    private String javaFiled;

    /**
     * 字段类型，如：Integer
     */
    private String javaFieldType;

    /**
     * 大写的字段名
     */
    private String enumName;

    /**
     * 如 java.lang.Integer
     */
    private String columnClassName;

    /**
     * lombok 对 is 开头的字段，并且是 boolean 类型的字段，生成的 get、set 方法，都没有 is 了，。。。。。。
     */
    private boolean isEnum;

    /**
     * 是不是主键
     */
    private boolean primaryKey;

    /**
     * jdbc 类型
     */
    private String columnType;

    /**
     * mybatis 的 jdbcType
     */
    private String mybatisJdbcType;
    private String queryType = "EQ";
    private String htmlType = "input";
    private Boolean query = false;
    private Boolean list = true;
    private Boolean insert = true;
    private Boolean update = true;
    private Boolean required = true;
    private List<EnumInfoBo> enumInfos = new ArrayList<>();

    /**
     * 生成的 get、set 方法 为 getEnum()、setEnum 不存在 is，导致模块在调用时无法获取字段。
     *
     * @return
     */
    public boolean getIsEnum() {
        return isEnum;
    }

    public void setIsEnum(boolean anEnum) {
        isEnum = anEnum;
    }


}
