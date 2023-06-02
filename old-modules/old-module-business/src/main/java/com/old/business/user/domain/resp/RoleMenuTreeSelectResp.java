package com.old.business.user.domain.resp;

import com.old.common.domain.login.html.HtmlMenuBo;

import java.util.List;

public record RoleMenuTreeSelectResp(List<Integer> checkedKeys, List<HtmlMenuBo> menus) {
}
