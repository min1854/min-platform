package com.old.business.user.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.min1854.apiAssert.tuple.Pair;
import io.github.min1854.apiAssert.tuple.Triple;
import io.github.min1854.apiAssert.tuple.Tuple4;
import com.old.business.user.domain.OldUser;
import com.old.business.user.domain.OldUserPost;
import com.old.business.user.domain.req.AddUserReq;
import com.old.business.user.domain.req.SearchUserReq;
import com.old.business.user.domain.req.UpdateUserReq;
import com.old.business.user.domain.resp.SearchUserResp;
import com.old.business.user.domain.resp.UpdateInfoResp;
import com.old.business.user.enums.user.OldUserEnums;
import com.old.business.user.mapper.OldMenuMapper;
import com.old.business.user.mapper.OldRoleMapper;
import com.old.business.user.mapper.OldUserMapper;
import com.old.business.user.service.OldUserPostService;
import com.old.business.user.service.OldUserRoleRelService;
import com.old.business.user.service.OldUserService;
import com.old.business.user.util.MenuUtil;
import com.old.business.user.util.RouterUtil;
import com.old.common.apiAssert.ResultAssertGenerator;
import com.old.common.apiAssert.ResultAssertHolder;
import com.old.common.domain.login.LoginUser;
import com.old.common.domain.login.RouterBo;
import com.old.common.domain.login.html.HtmlLoginUser;
import com.old.common.domain.login.html.HtmlMenuBo;
import com.old.common.domain.login.html.HtmlRoleBo;
import com.old.common.domain.login.html.HtmlUserBo;
import com.old.common.enums.ResultEnum;
import com.old.common.mybatis.base.BaseServiceImpl;
import com.old.common.web.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表  old_user
 *
 * @author old
 * @date 2022-10-17
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class OldUserServiceImpl extends BaseServiceImpl<OldUserMapper, OldUser> implements OldUserService {

    @Autowired
    OldMenuMapper oldMenuMapper;
    @Autowired
    OldRoleMapper oldRoleMapper;
    @Autowired
    OldUserRoleRelService oldUserRoleRelService;
    @Autowired
    OldUserPostService oldUserPostService;
    @Autowired
    private OldUserMapper oldUserMapper;

    @Override
    public void addUser(AddUserReq req, Integer createUserId, String createUser) {
        OldUser oldUser = new OldUser();
        BeanUtils.copyProperties(req, oldUser);
        this.insert(oldUser, createUser, createUserId);

        if (CollUtil.isNotEmpty(req.getPostIds())) {
            oldUserPostService.saveUserPost(oldUser.getId(), req.getPostIds(), createUserId, createUser);
        }
        if (CollUtil.isNotEmpty(req.getRoleIds())) {
            oldUserRoleRelService.saveUserRoleRel(oldUser.getId(), req.getRoleIds(), createUserId, createUser);
        }
    }

    @Override
    public void updateUser(UpdateUserReq req, Integer updateUserId, String updateUser) {
        OldUser oldUser = new OldUser();
        BeanUtils.copyProperties(req, oldUser);

        updateByKey(oldUser, updateUser, updateUserId);

        oldUserPostService.deleteAfterInsert(oldUser.getId(), req.getPostIds(), updateUserId, updateUser);

        oldUserRoleRelService.deleteAfterInsert(oldUser.getId(), req.getRoleIds(), updateUserId, updateUser);

    }

    @Override
    public UpdateInfoResp updateInfo(Integer id) {
        OldUser oldUser = this.getById(id);
        apiAssert.isTrue(oldUser.getDeleteFlag(), ResultEnum.USER_NOT_EXISTS);

        UpdateInfoResp resp = new UpdateInfoResp();

        UpdateInfoResp.User user = new UpdateInfoResp.User();
        BeanUtils.copyProperties(oldUser, user);
        resp.setUser(user);


        resp.setRoleIds(oldRoleMapper.selectByUserId(id).stream().map(HtmlRoleBo::getId).toList());

        resp.setPostIds(oldUserPostService.selectByUserId(id).stream().map(OldUserPost::getPostId).toList());

        return resp;
    }

    @Override
    public Page<SearchUserResp> searchPage(SearchUserReq req, OldUserEnums.UserTypeEnum userType, Integer pageNum, Integer pageSize) {
        log.debug("搜索用户条件：{}, {}, {} {}", userType, pageNum, pageSize, req);
        return oldUserMapper.searchPage(req, userType, Page.of(pageNum, pageSize));
    }


    @Override
    public void resetPwd(Integer id, String oldPassword, String newPassword, Integer updateUserId, String updateUserName) {
        log.debug("修改用户密码：id：{}，密码：{} {}，更新用户id：{}，更新用户：{}", id, oldPassword, newPassword, updateUserId, updateUserName);
        ResultAssertGenerator.create(this.baseMapper.selectById(id))
                .isNull(ResultEnum.USER_NOT_EXISTS)
                .isTrue(OldUser::getDeleteFlag, ResultEnum.ILLEGAL_ACTION)
                .isTrue(oldPassword.equals(newPassword), ResultEnum.PASSWORD_IDENTICAL)
                .isFalse(LoginUser.DEFAULT_USER_ID == updateUserId || updateUserId.equals(id), ResultEnum.ILLEGAL_ACTION)
                .process(oldUser -> {
                    OldUser temp = new OldUser();
                    temp.setId(id);
                    temp.setPassword(newPassword);
                    this.updateByKey(temp, updateUserName, updateUserId);
                })

        ;
    }


    @Override
    public String login(String userName, String password, String ipAddress) {
        log.debug("登录参数：{}，{}", userName, password);
        return ResultAssertGenerator.create(() -> {
                    LambdaQueryWrapper<OldUser> query = Wrappers.lambdaQuery(OldUser.class);
                    query.eq(OldUser::getUserName, userName);
                    query.eq(OldUser::getDeleteFlag, false);
                    return this.getOne(query);
                })
                // 后续根据真实进行修改
                .isFalse(oldUser -> oldUser != null && oldUser.getUserName().equals(userName)
                        && oldUser.getPassword().equals(password), ResultEnum.USERNAME_OR_PASSWORD_ERROR)
                .then(oldUser -> {
                    HtmlUserBo userBo = new HtmlUserBo();
                    BeanUtils.copyProperties(oldUser, userBo);
                    userBo.setUserType(oldUser.getUserType().getValue());
                    userBo.setSex(oldUser.getSex().getValue());
                    return userBo;
                })
                .then(htmlUserBo -> {
                    List<HtmlRoleBo> roleBos = oldRoleMapper.selectByUserId(htmlUserBo.getId());
                    return Pair.of(htmlUserBo, roleBos);
                })
                .then(pair -> {
                    List<HtmlMenuBo> menuBos = new ArrayList<>();
                    List<Integer> roleIds = pair.getValue().stream().map(HtmlRoleBo::getId).collect(Collectors.toList());
                    if (!roleIds.isEmpty()) {
                        menuBos = MenuUtil.buildMenuTree(oldMenuMapper.selectInRoleId(roleIds), BigDecimal.ZERO.intValue());
                    }
                    return Triple.of(pair.getKey(), pair.getValue(), menuBos);
                })
                .then(triple -> {
                    List<HtmlMenuBo> menuBoList = triple.getRight();
                    List<RouterBo> routerBos = RouterUtil.buildRouter(menuBoList);
                    return Tuple4.of(triple.getLeft(), triple.getMiddle(), menuBoList, routerBos);
                })
                .then(tuple4 -> {
                    String token = IdUtil.simpleUUID();
                    LoginUtil.login(token, HtmlLoginUser.builder()
                            .userBo(tuple4.getT1())
                            .roleBos(tuple4.getT2())
                            .menuBos(tuple4.getT3())
                            .routers(tuple4.getT4())
                            .ipAddress(ipAddress)
                            .token(token)
                            .loginTime(LocalDateTime.now())
                            .build());
                    return token;
                })
                .getObj();
    }


    @Override
    public void updateByKey(OldUser oldUser, String loginUserName, Integer loginUserId) {
        oldUser.setUpdateTime(LocalDateTime.now());
        oldUser.setUpdateUser(loginUserName);
        oldUser.setUpdateUserId(loginUserId);
        ResultAssertHolder.apiAssert().isFalse(this.updateById(oldUser), ResultEnum.UPDATE_FAIL);
    }

    @Override
    public void insert(OldUser oldUser, String loginUserName, Integer loginUserId) {
        oldUser.setCreateTime(LocalDateTime.now());
        oldUser.setCreateUser(loginUserName);
        oldUser.setCreateUserId(loginUserId);
        ResultAssertHolder.apiAssert().isFalse(this.save(oldUser), ResultEnum.SAVE_FAIL);
    }

    @Override
    public void removeById(Integer id, String loginUserName, Integer loginUserId) {
        OldUser oldUser = new OldUser();
        oldUser.setUpdateTime(LocalDateTime.now());
        oldUser.setUpdateUser(loginUserName);
        oldUser.setUpdateUserId(loginUserId);
        oldUser.setDeleteFlag(Boolean.TRUE);
        ResultAssertHolder.apiAssert().isFalse(this.updateById(oldUser), ResultEnum.DELETE_FAIL);
    }

    @Override
    public void removeByIds(List<Integer> ids, Integer userId, String userName) {
        LocalDateTime now = LocalDateTime.now();
        List<OldUser> list = ids.stream().map(id -> {
            OldUser oldUser = new OldUser();
            oldUser.setId(id);
            oldUser.setUpdateTime(now);
            oldUser.setUpdateUser(userName);
            oldUser.setUpdateUserId(userId);
            oldUser.setDeleteFlag(Boolean.TRUE);
            return oldUser;
        }).toList();
        apiAssert().isFalse(this.updateBatchById(list), ResultEnum.DELETE_FAIL);
    }

    @Override
    public void changeStatus(Integer userId, OldUserEnums.UserStatusEnum userStatus, Integer updateUserId, String updateUser) {
        ResultAssertGenerator.create(Pair.of(userId, userStatus))
                .isNull(Pair::getValue, ResultEnum.USER_STATUS_NOT_EXISTS)
                .then(pair -> this.getById(pair.getKey()))
                .isNull(ResultEnum.USER_NOT_EXISTS)
                .isTrue(OldUser::getDeleteFlag, ResultEnum.ILLEGAL_ACTION)
                .process(oldUser -> {
                    OldUser update = new OldUser();
                    update.setId(oldUser.getId());
                    update.setUserStatus(userStatus);
                    this.updateByKey(update, updateUser, updateUserId);
                })
        ;

    }

    @Override
    public void checkUserDataScope(Integer userId, Integer adminUserId) {
        if (userId.equals(adminUserId)) {
            return;
        }
        // 这里还有问题
    }

}
