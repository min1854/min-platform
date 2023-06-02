package com.old.common.domain.login.html;

import com.old.common.domain.login.MenuBo;
import lombok.Data;

import java.util.List;

@Data
public class HtmlMenuBo implements MenuBo {


    List<HtmlMenuBo> children;
    private Integer id;
    /**
     * 菜单名
     */
    private String menuName;
    /**
     * parent_id
     * 父菜单ID
     */
    private Integer parentId;
    /**
     * order_num
     * 显示顺序
     */
    private Integer orderNum;
    /**
     * menu_path
     * 路由地址
     */
    private String menuPath;
    /**
     * component
     * 组件路径
     */
    private String component;
    /**
     * menu_query
     * 路由参数
     */
    private String menuQuery;
    /**
     * is_frame
     * 是否为外链（0是 1否）
     */
    private Boolean isFrame;
    /**
     * is_cache
     * 是否缓存（0缓存 1不缓存）
     */
    private Boolean isCache;
    /**
     * menu_type
     * 菜单类型（M目录 C菜单 F按钮）
     */
    private String menuType;
    /**
     * menu_visible
     * 菜单状态（0显示 1隐藏）
     */
    private Integer menuVisible;
    /**
     * menu_status
     * 菜单状态（0正常 1停用）
     */
    private Integer menuStatus;
    /**
     * perms
     * 权限标识
     */
    private String perms;
    /**
     * icon
     * 菜单图标
     */
    private String icon;
}
