package com.old.business.user.domain.req;

import com.old.business.user.domain.OldRole;
import com.old.business.user.enums.user.OldRoleEnums;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class UpdateAndAllocationReq {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Integer id;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Size(min = 0, max = 30, message = "角色名称长度不能超过30个字符")
    private String roleName;

    /**
     * 角色权限
     */
    @NotBlank(message = "权限字符不能为空")
    @Size(min = 0, max = 100, message = "权限字符长度不能超过100个字符")
    private String roleKey;

    /**
     * 角色排序
     */
    @NotNull(message = "显示顺序不能为空")
    private Integer roleSort;

    /**
     * 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限）
     */
    private OldRoleEnums.DataScope dataScope;

    /**
     * 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）
     */
    private boolean menuCheckStrictly;

    /**
     * 部门树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示 ）
     */
    private boolean deptCheckStrictly;

    /**
     * 角色状态（0正常 1停用）
     */
    private Boolean roleStatus;

    /**
     * 用户是否存在此角色标识 默认不存在
     */
    private boolean flag = false;


    /**
     * 菜单组
     */
    private List<Integer> menuIds;

    /**
     * 一种 ddd 的想法，其实是否应该这个 req 对象为贫血模型，再通过一个包装类作为充血模型，将这个作为内部属性，然后包装类（即充血模型）做各种操作
     *
     * @return
     */
    public OldRole toRole() {
        var update = new OldRole();
        BeanUtils.copyProperties(this, update);
        return update;
    }

}
