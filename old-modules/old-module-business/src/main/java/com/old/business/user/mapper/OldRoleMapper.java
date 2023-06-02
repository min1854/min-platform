package com.old.business.user.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.business.user.domain.OldRole;
import com.old.business.user.domain.OldUser;
import com.old.business.user.domain.req.SearchRoleReq;
import com.old.common.domain.login.html.HtmlRoleBo;
import com.old.common.mybatis.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 角色表  old_role
 *
 * @author old
 * @date 2022-10-17
 */
public interface OldRoleMapper extends BaseMapper<OldRole> {

    List<OldRole> selectRolePermissionByUserId(@Param("userId") Integer userId);


    Page<OldUser> selectAllocatedList(@Param("roleId") Integer roleId, @Param("userName") String userName, @Param("phone") String phone, @Param("page") Page<OldUser> page);

    List<Integer> selectDeptListByRoleId(@Param("id") Integer id, @Param("deptCheckStrictly") boolean deptCheckStrictly);

    List<HtmlRoleBo> selectByUserId(@Param("userId") Integer userId);

    Page<OldRole> searchRolePage(@Param("req") SearchRoleReq req, @Param("page") Page<OldRole> page);

    List<Integer> selectMenuListByRoleId(@Param("roleId") Integer roleId, @Param("menuCheckStrictly") Boolean menuCheckStrictly);
}
