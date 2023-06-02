package com.old.business.user.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.old.business.user.enums.user.OldMenuEnums;
import com.old.common.domain.login.RouterBo;
import com.old.common.domain.login.html.HtmlMenuBo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class RouterUtil {

    private static Function<HtmlMenuBo, Boolean> isMenuFrame = bo -> {
        return bo.getParentId() == 0 && OldMenuEnums.MenuTypeEnum.MENU.valueEquals(bo.getMenuType())
                && !bo.getIsFrame();
    };

    private static Function<HtmlMenuBo, String> getRouteName = bo -> {
        String routerName = StrUtil.upperFirst(bo.getMenuPath());
        // 非外链并且是一级目录（类型为目录）
        if (isMenuFrame.apply(bo)) {
            routerName = StrUtil.EMPTY;
        }
        return routerName;
    };

    /**
     * 是否为内链组件
     * 原来的条件就是这样，不是内链，但路径是 http 开头
     */
    private static Function<HtmlMenuBo, Boolean> isInnerLink = bo -> {
        return !bo.getIsFrame() && (HttpUtil.isHttp(bo.getMenuPath()) || HttpUtil.isHttps(bo.getMenuPath()));
    };

    private static Function<String, String> innerLinkReplaceEach = path -> {

        String replace = StrUtil.replace(path, "http://", "");

        replace = StrUtil.replace(replace, "https://", "");
        replace = StrUtil.replace(replace, "www.", "");
        replace = StrUtil.replace(replace, ".", "/");
        return replace;
    };

    private static Function<HtmlMenuBo, String> getRouterPath = bo -> {
        String routerPath = bo.getMenuPath();
        // 内链打开外网方式
        if (bo.getParentId() != 0 && isInnerLink.apply(bo)) {
            routerPath = innerLinkReplaceEach.apply(routerPath);
        }
        // 非外链并且是一级目录（类型为目录）
        if (0 == bo.getParentId() && OldMenuEnums.MenuTypeEnum.DIR.valueEquals(bo.getMenuType())
                && !bo.getIsFrame()) {
            routerPath = "/" + bo.getMenuPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMenuFrame.apply(bo)) {
            routerPath = "/";
        }
        return routerPath;
    };

    private static Function<HtmlMenuBo, Boolean> isParentView = bo -> {
        return bo.getParentId() != 0 && OldMenuEnums.MenuTypeEnum.DIR.valueEquals(bo.getMenuType());
    };

    private static Function<HtmlMenuBo, String> getComponent = bo -> {
        OldMenuEnums.ComponentEnum component = OldMenuEnums.ComponentEnum.LAYOUT;
        if (StrUtil.isNotEmpty(bo.getComponent()) && !isMenuFrame.apply(bo)) {
            return bo.getComponent();
        } else if (StrUtil.isEmpty(bo.getComponent()) && bo.getParentId() != 0 && isInnerLink.apply(bo)) {
            component = OldMenuEnums.ComponentEnum.INNER_LINK;
        } else if (StrUtil.isEmpty(bo.getComponent()) && isParentView.apply(bo)) {
            component = OldMenuEnums.ComponentEnum.PARENT_VIEW;
        }
        return component.getValue();
    };

    public static List<RouterBo> buildRouter(List<HtmlMenuBo> menus) {

        List<RouterBo> routers = new ArrayList<>();
        for (HtmlMenuBo menu : menus) {
            RouterBo router = new RouterBo();
            router.setHidden(OldMenuEnums.MenuVisibleEnum.HIDDEN.getValue().equals(menu.getMenuVisible()));
            router.setName(getRouteName.apply(menu));
            router.setPath(getRouterPath.apply(menu));
            router.setComponent(getComponent.apply(menu));
            router.setQuery(menu.getMenuQuery());
            List<HtmlMenuBo> cMenus = menu.getChildren();
            router.setMeta(new RouterBo.Meta(menu.getMenuName(), menu.getIcon(), !menu.getIsCache(),
                    isInnerLink.apply(menu) ? menu.getMenuPath() : null));
            if (!cMenus.isEmpty() && OldMenuEnums.MenuTypeEnum.DIR.valueEquals(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildRouter(cMenus));
            } else if (isMenuFrame.apply(menu)) {
                router.setMeta(null);
                List<RouterBo> childrenList = new ArrayList<RouterBo>();
                RouterBo children = new RouterBo();
                children.setPath(menu.getMenuPath());
                children.setComponent(menu.getComponent());
                children.setName(StrUtil.upperFirst(menu.getMenuPath()));
                children.setMeta(new RouterBo.Meta(menu.getMenuName(), menu.getIcon(), !menu.getIsCache(),
                        isInnerLink.apply(menu) ? menu.getMenuPath() : null));
                children.setQuery(menu.getMenuQuery());
                childrenList.add(children);
                router.setChildren(childrenList);
            } else if (menu.getParentId() == 0 && isInnerLink.apply(menu)) {
                router.setMeta(new RouterBo.Meta(menu.getMenuName(), menu.getIcon()));
                router.setPath("/");
                List<RouterBo> childrenList = new ArrayList<RouterBo>();
                RouterBo children = new RouterBo();
                String routerPath = innerLinkReplaceEach.apply(menu.getMenuPath());
                children.setPath(routerPath);
                children.setComponent(OldMenuEnums.ComponentEnum.INNER_LINK.getValue());
                children.setName(StrUtil.upperFirst(routerPath));
                children.setMeta(new RouterBo.Meta(menu.getMenuName(), menu.getIcon(),
                        isInnerLink.apply(menu) ? menu.getMenuPath() : null));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

}
