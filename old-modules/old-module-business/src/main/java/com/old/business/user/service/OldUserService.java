package com.old.business.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.business.user.domain.OldUser;
import com.old.business.user.domain.req.AddUserReq;
import com.old.business.user.domain.req.SearchUserReq;
import com.old.business.user.domain.req.UpdateUserReq;
import com.old.business.user.domain.resp.SearchUserResp;
import com.old.business.user.domain.resp.UpdateInfoResp;
import com.old.business.user.enums.user.OldUserEnums;
import com.old.common.mybatis.base.BaseService;

import java.util.List;

/**
 * 用户表  old_user
 *
 * @author old
 * @date 2022-10-17
 */
public interface OldUserService extends BaseService<OldUser> {

    void addUser(AddUserReq req, Integer createUserId, String createUser);

    void updateUser(UpdateUserReq req, Integer updateUserId, String updateUser);

    UpdateInfoResp updateInfo(Integer id);

    Page<SearchUserResp> searchPage(SearchUserReq req, OldUserEnums.UserTypeEnum userType, Integer pageNum, Integer pageSize);

    void resetPwd(Integer id, String oldPassword, String newPassword, Integer updateUserId, String updateUserName);

    String login(String userName, String password, String ipAddress);

    void updateByKey(OldUser oldUser, String loginUserName, Integer loginUserId);

    void insert(OldUser oldUser, String loginUserName, Integer loginUserId);

    void removeById(Integer id, String loginUserName, Integer loginUserId);

    void removeByIds(List<Integer> ids, Integer userId, String userName);

    void changeStatus(Integer userId, OldUserEnums.UserStatusEnum userStatus, Integer updateUserId, String updateUser);

    void checkUserDataScope(Integer userId, Integer adminUserId);
}
