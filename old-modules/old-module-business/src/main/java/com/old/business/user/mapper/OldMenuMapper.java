package com.old.business.user.mapper;

import com.old.business.user.domain.OldMenu;
import com.old.business.user.enums.user.OldMenuEnums;
import com.old.common.domain.login.html.HtmlMenuBo;
import com.old.common.mybatis.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 菜单权限表  old_menu
 *
 * @author old
 * @date 2023-04-02
 */
public interface OldMenuMapper extends BaseMapper<OldMenu> {

    List<HtmlMenuBo> selectInRoleId(@Param("roleIds") List<Integer> roleIds);

    List<OldMenu> selectMenuListByUserId(@Param("userId") Integer userId, @Param("menuName") String menuName,
                                         @Param("menuVisible") OldMenuEnums.MenuVisibleEnum menuVisible,
                                         @Param("menuStatus") OldMenuEnums.MenuStatusEnum menuStatus);
}
