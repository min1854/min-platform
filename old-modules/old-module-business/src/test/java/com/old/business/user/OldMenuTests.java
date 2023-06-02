package com.old.business.user;

import com.old.business.SpringBootTests;
import com.old.business.user.domain.OldRole;
import com.old.business.user.util.RouterUtil;
import com.old.common.domain.login.LoginUser;
import com.old.common.domain.login.html.HtmlMenuBo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OldMenuTests extends SpringBootTests {

    @Test
    public void roleMenuTreeSelect() {
        oldMenuController.roleMenuTreeSelect(2);
    }

    @Test
    public void errorSelectMenuListByUserId() {
        var defaultUserId = LoginUser.DEFAULT_USER_ID;

        try {
            //                 menuName 传入 null 会报错
            // 把 bind 放入 if 里面就不会了
            oldMenuServiceImpl.selectMenuListByUserId(5, null,
                    null, null, defaultUserId);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void selectMenuListByUserId() {
        var defaultUserId = LoginUser.DEFAULT_USER_ID;
        echoDefaultJsonResult(oldMenuServiceImpl.selectMenuListByUserId(1, null, null,
                null, defaultUserId))
                .echoDefaultJsonResult(oldMenuServiceImpl.selectMenuListByUserId(5, "123123",
                        null, null, defaultUserId))
//                 menuName 传入 null 会报错
                .echoDefaultJsonResult(oldMenuServiceImpl.selectMenuListByUserId(5, "",
                        null, null, defaultUserId));

        try {
            oldMenuServiceImpl.selectMenuListByUserId(5, null,
                    null, null, defaultUserId);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void findParent(List<HtmlMenuBo> bos, int id) {
        for (HtmlMenuBo bo : bos) {
            if (bo.getId().equals(id)) {
                System.out.println(bo);
            } else if (bo.getChildren() != null) {
                findParent(bo.getChildren(), id);
            }
        }

    }

    public static class Test7 extends OldMenuTests {

        @Test
        public void buildMenuTree() {

            List<HtmlMenuBo> menuBoList = oldMenuMapper.selectInRoleId(List.of(oldRoleMapper.selectById(1).getId()));
            List<HtmlMenuBo> htmlMenuBos = groupMenu(menuBoList, 0);
            echoDefaultJsonResult(htmlMenuBos);
        }


        private List<HtmlMenuBo> groupMenu(List<HtmlMenuBo> menuBos, int parentId) {
            List<HtmlMenuBo> parentMenu = menuBos.stream().filter(bo -> bo.getParentId().equals(parentId)).toList();
            for (HtmlMenuBo menu : parentMenu) {


                List<HtmlMenuBo> child = groupMenu(menuBos.stream()
                        // .filter(bo -> !bo.getParentId().equals(menu.getId()))
                        .filter(bo -> !bo.getParentId().equals(parentId))
                        .toList(), menu.getId());
                menu.setChildren(child);
            }

            return parentMenu;
        }


    }


    public static class Test6 extends OldMenuTests {

        @Test
        public void buildMenuTree() {

            List<HtmlMenuBo> menuBoList = oldMenuMapper.selectInRoleId(List.of(oldRoleMapper.selectById(1).getId()));
            List<HtmlMenuBo> htmlMenuBos = groupMenu(menuBoList, 0);
            echoDefaultJsonResult(htmlMenuBos);
            echoDefaultJsonResult(RouterUtil.buildRouter(htmlMenuBos));
        }


        private List<HtmlMenuBo> groupMenu(List<HtmlMenuBo> menuBos, int parentId) {
            List<HtmlMenuBo> parentMenu = menuBos.stream().filter(bo -> bo.getParentId().equals(parentId)).toList();
            for (HtmlMenuBo menu : parentMenu) {
                menu.setChildren(menuBos.stream().filter(bo -> bo.getParentId().equals(menu.getId())).toList());

                groupMenu(menuBos.stream()
                        // .filter(bo -> !bo.getParentId().equals(menu.getId()))
                        // .filter(bo -> !bo.getParentId().equals(parentId))
                        .toList(), menu.getId());
            }

            return parentMenu;
        }


    }


    public static class Test5 extends OldMenuTests {

        @Test
        public void buildMenuTree() {

            List<HtmlMenuBo> menuBoList = oldMenuMapper.selectInRoleId(List.of(oldRoleMapper.selectById(1).getId()));
            groupMenu(menuBoList, 0);
            echoDefaultJsonResult(menuBoList);
        }


        private void groupMenu(List<HtmlMenuBo> menuBos, int parentId) {
            List<HtmlMenuBo> parentMenu = menuBos.stream().filter(bo -> bo.getParentId().equals(parentId)).toList();
            for (HtmlMenuBo menu : parentMenu) {
                menu.setChildren(menuBos.stream().filter(bo -> bo.getParentId().equals(menu.getId())).toList());
                groupMenu(menuBos, menu.getId());
            }
        }


    }

    public static class Test4 extends OldMenuTests {

        @Test
        public void buildMenuTree() {

            List<HtmlMenuBo> menuBoList = new ArrayList<>();
            groupMenu(menuBoList, oldMenuMapper.selectInRoleId(List.of(oldRoleMapper.selectById(1).getId())), 0);
            echoDefaultJsonResult(menuBoList);
        }


        private void groupMenu(List<HtmlMenuBo> resp, List<HtmlMenuBo> menuBos, int parentId) {
            List<HtmlMenuBo> parentMenu = menuBos.stream().filter(bo -> bo.getParentId().equals(parentId)).toList();
            for (HtmlMenuBo parent : parentMenu) {
                resp.add(parent);
                List<HtmlMenuBo> children = new ArrayList<>();
                groupMenu(children, menuBos.stream().filter(bo -> !bo.getParentId().equals(parentId)).toList(), parent.getId());
                parent.setChildren(children);
            }
        }

    }

    public static class Test3 extends OldMenuTests {

        @Test
        public void buildMenuTree() {
            OldRole role = oldRoleMapper.selectById(1);

            List<HtmlMenuBo> menuBoList = oldMenuMapper.selectInRoleId(List.of(role.getId()));
            groupMenu(menuBoList, 0);
        }


        private void groupMenu(List<HtmlMenuBo> menuBos, int parentId) {
            List<HtmlMenuBo> parentMenu = menuBos.stream().filter(bo -> bo.getParentId().equals(parentId)).toList();
            for (HtmlMenuBo parent : parentMenu) {
                findChild(parent, menuBos);
            }
            echoDefaultJsonResult(parentMenu);

        }

        private void findChild(HtmlMenuBo parentMenu, List<HtmlMenuBo> menuBoList) {
            List<HtmlMenuBo> child = menuBoList.stream().filter(bo -> bo.getParentId().equals(parentMenu.getId())).toList();
            parentMenu.setChildren(child);
            for (HtmlMenuBo htmlMenuBo : child) {
                findChild(htmlMenuBo, menuBoList);
            }

        }
    }

    public static class Test2 extends OldMenuTests {

        @Test
        public void buildMenuTree() {
            OldRole role = oldRoleMapper.selectById(1);

            List<HtmlMenuBo> menuBoList = oldMenuMapper.selectInRoleId(List.of(role.getId()));
            groupMenu(menuBoList, 0);
        }


        private void groupMenu(List<HtmlMenuBo> menuBos, int parentId) {
            List<HtmlMenuBo> parentMenu = menuBos.stream().filter(bo -> bo.getParentId().equals(BigDecimal.ZERO.intValue())).toList();
            for (HtmlMenuBo parent : parentMenu) {
                parent.setChildren(findChild(parent, menuBos));
            }
            echoDefaultJsonResult(parentMenu);

        }

        private List<HtmlMenuBo> findChild(HtmlMenuBo parentMenu, List<HtmlMenuBo> child) {
            ArrayList<HtmlMenuBo> resp = new ArrayList<>();
            for (HtmlMenuBo htmlMenuBo : child) {
                if (htmlMenuBo.getParentId().equals(parentMenu.getId())) {
                    resp.add(htmlMenuBo);
                } else {
                    List<HtmlMenuBo> respChild = findChild(htmlMenuBo, child);
                    htmlMenuBo.setChildren(respChild);
                }
            }


            return resp;

        }
    }


    public static class Test1 extends OldMenuTests {

        @Test
        public void buildMenuTree() {
            OldRole role = oldRoleMapper.selectById(1);

            List<HtmlMenuBo> menuBoList = oldMenuMapper.selectInRoleId(List.of(role.getId()));
            groupMenu(menuBoList, 0);
        }


        private void groupMenu(List<HtmlMenuBo> menuBos, int parentId) {
            // List<HtmlMenuBo> parentMenu = menuBos.stream().filter(bo -> bo.getParentId().equals(BigDecimal.ZERO.intValue())).toList();
            List<HtmlMenuBo> resp = new ArrayList<>();
            for (HtmlMenuBo menuBo : menuBos) {
                if (menuBo.getParentId().equals(parentId)) {
                    resp.add(menuBo);
                    menuBo.setChildren(findChild(menuBo, menuBos));
                }
            }
            echoDefaultJsonResult(resp);

        }

        private List<HtmlMenuBo> findChild(HtmlMenuBo parentMenu, List<HtmlMenuBo> child) {
            ArrayList<HtmlMenuBo> resp = new ArrayList<>();
            for (HtmlMenuBo htmlMenuBo : child) {
                if (htmlMenuBo.getParentId().equals(BigDecimal.ZERO.intValue())) {
                    continue;
                } else if (htmlMenuBo.getParentId().equals(parentMenu.getId())) {
                    resp.add(htmlMenuBo);
                } else {
                    List<HtmlMenuBo> respChild = findChild(htmlMenuBo, child);
                    htmlMenuBo.setChildren(respChild);
                }
            }


            return resp;

        }
    }

}
