import request from '@/utils/request'



// 新增 用户，包括岗位和角色
export function addUser(data) {
  return request({
    url: '/oldUser/addUser',
    method: 'post',
    data: data
  })
}


// 修改 用户，包括岗位和角色
export function updateUser(data) {
  return request({
    url: '/oldUser/updateUser',
    method: 'post',
    data: data
  })
}

export function searchPage(query) {
  return request({
    url: '/oldUser/searchPage',
    method: 'get',
    params: query
  })
}

// 查询 用户表 列表
export function oldUserPage(query) {
  return request({
    url: '/oldUser/page',
    method: 'get',
    params: query
  })
}

// 查询 用户表 详细
export function updateInfo(id) {
  return request({
    url: '/oldUser/updateInfo?userId=' + id,
    method: 'get'
  })
}

// 查询 用户表 详细
export function getOldUser(id) {
  return request({
    url: '/oldUser/getById?id=' + id,
    method: 'get'
  })
}

// 新增 用户表
export function saveOldUser(data) {
  return request({
    url: '/oldUser/save',
    method: 'post',
    data: data
  })
}

// 修改 用户表
export function updateOldUserById(data) {
  return request({
    url: '/oldUser/updateById',
    method: 'post',
    data: data
  })
}

// 删除 用户表
export function removeOldUserByIds(ids) {
  return request({
    url: '/oldUser/removeByIds',
    method: 'post',
    data: ids
  })
}


// 用户密码重置
export function resetUserPwd(id, password) {
  const data = {
    id: id,
    password: password
  }
  return request({
    url: '/oldUser/resetPwd',
    method: 'post',
    data: data
  })
}

// 用户密码重置
export function updatePwd(oldPassword, newPassword) {
  const data = {
    oldPassword,
    newPassword
  }
  return request({
    url: '/oldUser/updatePwd',
    method: 'post',
    data: data
  })
}


// 用户状态修改
export function changeUserStatus(userId, status) {
  const urlSearchParams = new URLSearchParams();
  urlSearchParams.append("userId", userId);
  urlSearchParams.append("userStatus", status);
  return request({
    url: '/oldUser/changeStatus',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    data: urlSearchParams
  })
}

// 查询用户个人信息
export function getUserProfile() {
  return request({
    url: '/oldUser/getUserProfile',
    method: 'get'
  })
}

// 修改用户个人信息
export function updateUserProfile(data) {
  return request({
    url: '/oldUser/updateUserProfile',
    method: 'post',
    data: data
  })
}


// 用户头像上传
export function uploadAvatar(data) {
  return request({
    url: '/oldUser/uploadAvatar',
    method: 'post',
    data: data
  })
}

// 查询授权角色
export function getAuthRole(userId) {
  return request({
    url: '/oldUser/authRole/' + userId,
    method: 'get'
  })
}

// 保存授权角色
export function updateAuthRole(data) {
  return request({
    url: '/oldUser/authRole',
    method: 'post',
    params: data
  })
}
