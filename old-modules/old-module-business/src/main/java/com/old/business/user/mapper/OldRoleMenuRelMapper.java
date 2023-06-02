package com.old.business.user.mapper;

import com.old.business.user.domain.OldRoleMenuRel;
import com.old.common.mybatis.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 角色菜单关联表  old_role_menu_rel
 *
 * @author old
 * @date 2023-04-19
 */
public interface OldRoleMenuRelMapper extends BaseMapper<OldRoleMenuRel> {

    int saveBatch(@Param("insetList") List<OldRoleMenuRel> insetList);
}
