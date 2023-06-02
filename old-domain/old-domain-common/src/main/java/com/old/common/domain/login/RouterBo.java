package com.old.common.domain.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
public class RouterBo implements Serializable {

    /**
     * 菜单名
     */
    private String name;

    /**
     * menu_path
     * 路由地址
     */
    private String path;

    /**
     * 是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现
     */
    private Boolean hidden;

    /**
     * 重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
     */
    private String redirect;

    /**
     * component
     * 组件路径
     */
    private String component;

    /**
     * menu_query
     * 路由参数
     */
    private String query;

    /**
     * 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
     */
    private Boolean alwaysShow;

    private Meta meta;

    private List<RouterBo> children;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Meta implements Serializable {
        private String title;
        private String icon;
        private Boolean noCache;
        private String link;


        public Meta(String title, String icon) {
            this.title = title;
            this.icon = icon;
        }

        public Meta(String title, String icon, String link) {
            this.title = title;
            this.icon = icon;
            this.link = link;
        }
    }
}
