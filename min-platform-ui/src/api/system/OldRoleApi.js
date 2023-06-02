import request from '@/utils/request'

export function searchRolePage(query) {
  return request({
    url: '/oldRole/searchRolePage',
    method: 'get',
    params: query
  })
}

export function updateAndAllocation(data) {
  return request({
    url: '/oldRole/updateAndAllocation',
    method: 'post',
    data: data
  })

}

// 查询 用户表 列表
export function filterAdmin(isAdmin) {
  return request({
    url: '/oldRole/filterAdmin?isAdmin=' + (isAdmin == null ? false : isAdmin),
    method: 'get',
  })
}





// 查询 角色表 列表
export function oldRoleSelect(query) {

  return request({
    url: '/oldRole/select',
    method: 'get',
    params: query
  })
}

// 查询 角色表 分页
export function oldRolePage(query) {
  return request({
    url: '/oldRole/page',
    method: 'get',
    params: query
  })
}

// 查询 角色表 详细
export function getOldRole(id) {
  return request({
    url: '/oldRole?id=' + id,
    method: 'get'
  })
}

// 新增 角色表
export function saveOldRole(data) {
  return request({
    url: '/oldRole/save',
    method: 'post',
    data: data
  })
}

// 修改 角色表
export function updateOldRoleById(data) {
  return request({
    url: '/oldRole/updateById',
    method: 'post',
    data: data
  })
}



// 删除 角色表
export function removeOldRoleById(id) {
  return request({
    url: '/oldRole/removeById/' + id,
    method: 'post',
  })
}


// 删除多个 角色表
export function removeOldRoleByIds(ids) {
  return request({
    url: '/oldRole/removeByIds',
    method: 'post',
    data: ids
  })
}









// 查询角色列表
export function listRole(query) {
  return request({
    url: '/oldRole/select',
    method: 'get',
    params: query
  })
}

// 查询角色详细
export function getRole(roleId) {
  return request({
    url: '/oldRole/getById?id=' + roleId,
    method: 'get'
  })
}

// 新增角色
export function addRole(data) {
  return request({
    url: '/oldRole',
    method: 'post',
    data: data
  })
}


// 角色数据权限
export function dataScope(data) {
  return request({
    url: '/oldRole/updateDataScope',
    method: 'post',
    data: data
  })
}

// 角色状态修改
export function changeRoleStatus(id, roleStatus) {
  const data = new URLSearchParams()
  data.append("id", id);
  data.append("roleStatus", roleStatus);
  return request({
    url: '/oldRole/changeStatus',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    data: data
  })
}

// 删除角色
export function delRole(roleId) {
  return request({
    url: '/oldRole/removeByIdAndCheckPower' ,
    method: 'post',
    data: roleId
  })
}

// 查询角色已授权用户列表
export function allocatedUserList(query) {
  return request({
    url: '/oldRole/selectAllocatedList',
    method: 'get',
    params: query
  })
}

// 查询角色未授权用户列表
export function unallocatedUserList(query) {
  return request({
    url: '/oldRole/selectUnallocatedList',
    method: 'get',
    params: query
  })
}

// 取消用户授权角色
export function authUserCancel(data) {
  const params = new URLSearchParams();
  params.append("userId", data.userId);
  params.append("roleId", data.roleId);
  return request({
    url: '/oldUserRoleRel/authUser/cancel',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    data: params
  })
}

// 批量取消用户授权角色
export function authUserCancelAll(data) {
  return request({
    url: '/oldUserRoleRel/authUser/cancelAll',
    method: 'post',
    params: data
  })
}

// 授权用户选择
export function authUserSelectAll(data) {
  return request({
    url: '/oldRole/authUser/selectAll',
    method: 'put',
    params: data
  })
}

// 根据角色ID查询部门树结构
export function deptTreeSelect(roleId) {
  return request({
    url: '/oldRole/deptTree/' + roleId,
    method: 'get'
  })
}
