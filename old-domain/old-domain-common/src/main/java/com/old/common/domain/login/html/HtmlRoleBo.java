package com.old.common.domain.login.html;

import com.old.common.domain.login.RoleBo;
import lombok.Data;

@Data
public class HtmlRoleBo implements RoleBo {
    private Integer id;
    private String roleName;


    /**
     * dept_check_strictly
     * 部门树选择项是否关联显示
     */
    private Boolean deptCheckStrictly;

}
