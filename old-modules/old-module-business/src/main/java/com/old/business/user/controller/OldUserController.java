package com.old.business.user.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.business.user.domain.OldDept;
import com.old.business.user.domain.OldPost;
import com.old.business.user.domain.OldUser;
import com.old.business.user.domain.req.*;
import com.old.business.user.domain.resp.AuthRoleResp;
import com.old.business.user.domain.resp.GetUserProfileResp;
import com.old.business.user.domain.resp.SearchUserResp;
import com.old.business.user.domain.resp.UpdateInfoResp;
import com.old.business.user.enums.user.OldUserEnums;
import com.old.business.user.service.*;
import com.old.common.apiAssert.ResultAssertHolder;
import com.old.common.domain.login.LoginUser;
import com.old.common.domain.login.RoleBo;
import com.old.common.domain.login.html.HtmlLoginUser;
import com.old.common.domain.login.html.HtmlUserBo;
import com.old.common.enums.ResultEnum;
import com.old.common.enums.redis.RedisKey;
import com.old.common.exception.ResultException;
import com.old.common.mybatis.result.DatabasePageR;
import com.old.common.mybatis.result.DbPageR;
import com.old.common.redis.util.RedisUtil;
import com.old.common.result.PageData;
import com.old.common.result.R;
import com.old.common.upload.service.FileService;
import com.old.common.web.util.LoginUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static cn.hutool.core.net.NetUtil.getMultistageReverseProxyIp;
import static com.old.common.apiAssert.ResultAssertHolder.apiAssert;


/**
 * 用户表 Controller old_user
 *
 * @author old
 * @date 2022-10-17
 */
@Slf4j
@RestController
@RequestMapping("/oldUser")
public class OldUserController {

    private static final ResultAssertHolder.ResultAssert apiAssert = ResultAssertHolder.apiAssert();

    @Autowired
    OldUserService oldUserService;

    @Autowired
    OldDeptService oldDeptService;

    @Autowired
    OldPostService oldPostService;

    @Autowired
    OldRoleService oldRoleService;

    @Autowired
    OldUserRoleRelService oldUserRoleRelService;

    @Autowired
    private FileService fileService;

    @PostMapping("/uploadAvatar")
    public R<String> uploadAvatar(@RequestParam("avatarFile") MultipartFile avatarFile) throws IOException {
        LoginUser loginUser = LoginUtil.getLoginUser();
        HtmlUserBo userBo = (HtmlUserBo) loginUser.getUserBo();
        fileService.upload(new MultipartFile[]{avatarFile}, bo -> {
            userBo.setProfile(bo.getFilePath());
        });

        OldUser oldUser = new OldUser();
        oldUser.setId(userBo.getId());
        oldUser.setProfile(userBo.getProfile());
        oldUserService.updateByKey(oldUser, loginUser.loginUserName(), loginUser.loginUserId());

        LoginUtil.refresh(loginUser);

        return R.ok(userBo.getProfile());
    }


    @GetMapping("/authRole/{userId}")
    public R<AuthRoleResp> authRole(@PathVariable("userId") Integer userId) {
        var user = oldUserService.getById(userId);
        apiAssert()
                .isNull(user, ResultEnum.USER_NOT_EXISTS)
                .isTrue(user.getDeleteFlag(), ResultEnum.ILLEGAL_ACTION);
        var roles = oldRoleService.selectRolesByUserId(userId);
        return R.ok(new AuthRoleResp(user, userId.equals(LoginUser.DEFAULT_USER_ID) ? roles : roles.stream()
                .filter(r -> !RoleBo.ADMIN_ID.equals(r.getId())).collect(Collectors.toList())));
    }

    @PostMapping("/authRole")
    public R<Void> insertAuthRole(@RequestParam("userId") Integer userId, @RequestParam("roleIds") List<Integer> roleIds) {
        oldUserService.checkUserDataScope(userId, LoginUser.DEFAULT_USER_ID);
        oldUserRoleRelService.deleteAfterInsert(userId, roleIds, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }

    @PostMapping("/updateUserProfile")
    public void updateUserProfile(@RequestBody UpdateUserProfileReq req) {
        var oldUser = new OldUser();
        BeanUtils.copyProperties(req, oldUser);

        var loginUser = (HtmlLoginUser) LoginUtil.getLoginUser();
        oldUserService.updateByKey(oldUser, loginUser.loginUserName(), loginUser.loginUserId());

        HtmlUserBo userBo = loginUser.getUserBo();
        BeanUtils.copyProperties(req, userBo);
        loginUser.setUserBo(userBo);
        LoginUtil.refresh(loginUser);
    }

    @GetMapping("/getUserProfile")
    public R<GetUserProfileResp> getUserProfile() {
        GetUserProfileResp resp = new GetUserProfileResp();


        HtmlUserBo userBo = (HtmlUserBo) LoginUtil.getLoginUser().getUserBo();
        BeanUtils.copyProperties(userBo, resp);
        resp.setSex(OldUserEnums.SexEnum.select(userBo.getSex()));
        OldDept oldDept = oldDeptService.getById(userBo.getDeptId());
        apiAssert().isTrue(oldDept.getDeleteFlag(), ResultEnum.DEPT_DELETE);
        resp.setDeptName(oldDept.getDeptName());


        String postGroup = oldPostService.selectByUserId(userBo.getId()).stream().map(OldPost::getPostName).collect(Collectors.joining(","));
        resp.setPostGroup(postGroup);

        String roleGroup = LoginUtil.getLoginUser().getRoleBos().stream().map(RoleBo::getRoleName).collect(Collectors.joining(","));
        resp.setRoleGroup(roleGroup);
        return R.ok(resp);
    }

    @PostMapping("/addUser")
    public R<Void> addUser(@RequestBody AddUserReq req) {
        oldUserService.addUser(req, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }


    @PostMapping("/updateUser")
    public R<Void> updateUser(@RequestBody UpdateUserReq req) {
        oldUserService.updateUser(req, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }

    @GetMapping("/updateInfo")
    public R<UpdateInfoResp> updateInfo(@RequestParam("userId") Integer userId) {
        return R.ok(oldUserService.updateInfo(userId));
    }


    @PostMapping("/changeStatus")
    public R<Void> changeStatus(@RequestParam("userId") Integer userId, @RequestParam("userStatus") Integer userStatus) {
        oldUserService.changeStatus(userId, OldUserEnums.UserStatusEnum.select(userStatus), LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }


    @GetMapping("/searchPage")
    public R<PageData<SearchUserResp>> searchPage(@ModelAttribute SearchUserReq req,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        HtmlUserBo userBo = (HtmlUserBo) LoginUtil.getLoginUser().getUserBo();
        return DbPageR.of(oldUserService.searchPage(req, OldUserEnums.UserTypeEnum.select(userBo.getUserType()),
                pageNum, pageSize));
    }


    @PostMapping("/resetPwd")
    public R<Void> resetPwd(@RequestBody @Validated ResetPwdReq req) {
        oldUserService.resetPwd(req.getId(), LoginUtil.getLoginUser().getUserBo().getPassword(),
                req.getPassword(), LoginUtil.loginUserId(), LoginUtil.loginUserName());

        // 还需要让修改了密码的人重新登录，或是也不影响吧，
        return R.ok();
    }

    @PostMapping("/updatePwd")
    public R<Void> updatePwd(@RequestBody @Validated UpdatePwdReq req) {
        oldUserService.resetPwd(LoginUtil.loginUserId(), req.getOldPassword(), req.getNewPassword(), LoginUtil.loginUserId(),
                LoginUtil.loginUserName());
        var loginUser = (HtmlLoginUser) LoginUtil.getLoginUser();
        loginUser.getUserBo().setPassword(req.getNewPassword());
        LoginUtil.refresh(loginUser);
        return R.ok();
    }

    @PostMapping("/logout")
    public R<Void> logout() {
        try {
            LoginUtil.logout();
        } catch (ResultException e) {
            if (!ResultEnum.UNAUTHORIZED.getCode().equals(e.getResultEnum().getCode())) {
                throw e;
            }
        }
        return R.ok();
    }

    @GetMapping("/loginUser")
    public R<HtmlLoginUser> loginUser() {
        return R.ok((HtmlLoginUser) LoginUtil.getLoginUser());
    }

    @PostMapping("/login")
    public R<String> login(@RequestParam("userName") String userName, @RequestParam("password") String password, @Value("${login.error.limit:5}") Long limit,
                           HttpServletRequest request) {
        var key = RedisKey.LOGIN_OUT_OF_LIMIT;
        try {
            Integer error = RedisUtil.get(key, userName);

            apiAssert.isTrue(error != null && error >= limit,
                    ResultEnum.LOGIN_OUT_OF_LIMIT.format(key.getExpireTimeUnit().toMinutes(key.getExpireTime())));
            String token = oldUserService.login(userName, password, getIpAddress(request));
            return R.ok(token);
        } catch (ResultException e) {
            RedisUtil.incr(key, userName);
            throw e;
        }
    }


    public String getIpAddress(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : getMultistageReverseProxyIp(ip);
    }

    @GetMapping("/page")
    public R<PageData<OldUser>> page(@ModelAttribute OldUser oldUser,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return DatabasePageR.of(oldUserService.page(new Page<>(pageNum, pageSize), Wrappers.lambdaQuery(oldUser)));
    }


    @GetMapping("/select")
    public R<List<OldUser>> select(@ModelAttribute OldUser oldUser) {
        List<OldUser> list = oldUserService.list(Wrappers.lambdaQuery(oldUser));
        return R.ok(list);
    }

    @GetMapping("/getById")
    public R<OldUser> getById(@RequestParam("id") Integer id) {
        return R.ok(oldUserService.getById(id));
    }

    @PostMapping("/updateById")
    public R<?> updateById(@RequestBody OldUser oldUser) {
        oldUserService.updateByKey(oldUser, LoginUtil.loginUserName(), LoginUtil.loginUserId());
        return R.ok();
    }

    @PostMapping("/save")
    public R<?> save(@RequestBody OldUser oldUser) {
        oldUserService.insert(oldUser, LoginUtil.loginUserName(), LoginUtil.loginUserId());
        return R.ok();
    }


    @PostMapping("/removeById")
    public R<?> removeById(@RequestParam("id") Integer id) {
        oldUserService.removeById(id, LoginUtil.loginUserName(), LoginUtil.loginUserId());
        return R.ok();
    }


    @PostMapping("/removeByIds")
    public R<Void> removeByIds(@RequestBody List<Integer> ids) {
        oldUserService.removeByIds(ids, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }

}
