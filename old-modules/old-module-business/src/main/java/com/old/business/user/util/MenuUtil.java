package com.old.business.user.util;

import com.old.common.domain.login.MenuBo;
import com.old.common.domain.login.html.HtmlMenuBo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MenuUtil {
    public static List<HtmlMenuBo> buildMenuTree(List<HtmlMenuBo> menus) {
        List<HtmlMenuBo> returnList = new ArrayList<>();
        List<Integer> tempList = menus.stream().map(MenuBo::getId).toList();
        for (Iterator<HtmlMenuBo> iterator = menus.iterator(); iterator.hasNext(); ) {
            var menu = (HtmlMenuBo) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(menu.getParentId())) {
                recursionFn(menus, menu);
                returnList.add(menu);
            }
        }
        if (returnList.isEmpty()) {
            returnList = menus;
        }
        return returnList;
    }

    private static void recursionFn(List<HtmlMenuBo> list, HtmlMenuBo t) {
        // 得到子节点列表
        List<HtmlMenuBo> childList = getChildList(list, t);
        t.setChildren(childList);
        for (HtmlMenuBo tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }


    /**
     * 判断是否有子节点
     */
    private static boolean hasChild(List<HtmlMenuBo> list, HtmlMenuBo t) {
        return getChildList(list, t).size() > 0;
    }

    private static List<HtmlMenuBo> getChildList(List<HtmlMenuBo> list, HtmlMenuBo t) {
        List<HtmlMenuBo> tlist = new ArrayList<HtmlMenuBo>();
        Iterator<HtmlMenuBo> it = list.iterator();
        while (it.hasNext()) {
            HtmlMenuBo n = it.next();
            if (n.getParentId().equals(t.getId())) {
                tlist.add(n);
            }
        }
        return tlist;
    }


    public static List<HtmlMenuBo> buildMenuTree(List<HtmlMenuBo> menuBos, int parentId) {
        List<HtmlMenuBo> parentMenu = menuBos.stream().filter(bo -> bo.getParentId().equals(parentId)).toList();
        for (HtmlMenuBo menu : parentMenu) {
            List<HtmlMenuBo> child = buildMenuTree(menuBos.stream()
                    .filter(bo -> !bo.getParentId().equals(parentId))
                    .toList(), menu.getId());
            menu.setChildren(child);
        }

        return parentMenu;
    }


}
