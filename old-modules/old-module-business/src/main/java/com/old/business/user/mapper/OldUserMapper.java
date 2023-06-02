package com.old.business.user.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.business.user.domain.OldUser;
import com.old.business.user.domain.req.SearchUserReq;
import com.old.business.user.domain.resp.SearchUserResp;
import com.old.business.user.enums.user.OldUserEnums;
import com.old.common.mybatis.base.BaseMapper;
import org.apache.ibatis.annotations.Param;


/**
 * 用户表  old_user
 *
 * @author old
 * @date 2022-10-17
 */
public interface OldUserMapper extends BaseMapper<OldUser> {

    Page<OldUser> selectUnallocatedList(@Param("roleId") Integer roleId, @Param("userName") String userName, @Param("phone") String phone, @Param("page") Page<OldUser> page);

    Page<SearchUserResp> searchPage(@Param("req") SearchUserReq req, @Param("userType") OldUserEnums.UserTypeEnum userType, @Param("page") Page<SearchUserResp> page);

}
