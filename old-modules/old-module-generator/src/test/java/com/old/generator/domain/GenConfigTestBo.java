package com.old.generator.domain;

import com.old.generator.domain.bo.EnumInfoBo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenConfigTestBo {
    /**
     * 包名 com.old.模块名，指定到这层
     */
    //@Nonnull
    private String packageName;

    private String moduleName;

    // 作者
    private String author;
    // 创建时间，为空则是当前时间、yyyy-MM-dd 格式
    private String datetime;
    //@Nonnull
    // 输出目录 目录最后要加 \\ 或是 /
    private String outDir;


    // 数据库名
    private String database;

    //  请求前缀 默认为空字符串
    private String requestPathPrefix;

    private Boolean removeGenDir = true;

    /**
     * 不需要的表名称
     */
    private Set<String> dontNeedTablesName = new HashSet<>();

    private String loginUtilImport = "com.old.common.web.util.LoginUtil";
    private String loginUtilName = "LoginUtil";

    // 生成的表和枚举字段
    private List<WrapGenTable> genTables;


    @Data
    public static class WrapGenTable {
        private String table;
        private List<Map<String, WrapField>> fields = new ArrayList<>();
    }

    @Data
    public static class WrapField {
        private String inputType;
        private List<EnumInfoBo> enumInfos = new ArrayList<>();
    }
}
