package com.old.business.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.old.business.user.enums.user.OldMenuEnums;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜单权限表 对象 old_menu
 *
 * @author old
 * @date 2023-04-20
 */
@Data
@FieldNameConstants
public class OldMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     * 菜单ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * menu_name
     * 菜单名称
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
    private OldMenuEnums.MenuTypeEnum menuType;

    /**
     * menu_visible
     * 菜单状态（0显示 1隐藏）
     */
    private OldMenuEnums.MenuVisibleEnum menuVisible;

    /**
     * menu_status
     * 菜单状态（0正常 1停用）
     */
    private OldMenuEnums.MenuStatusEnum menuStatus;

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

    /**
     * create_user
     * 创建者
     */
    private String createUser;

    /**
     * create_time
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * create_user_id
     * 创建用户id
     */
    private Integer createUserId;

    /**
     * update_user
     * 更新者
     */
    private String updateUser;

    /**
     * update_time
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * update_user_id
     * 修改用户id
     */
    private Integer updateUserId;

    /**
     * delete_flag
     * 删除标识
     */
    private Boolean deleteFlag;

}
