import request from '@/utils/request'

// 登录方法
export function login(username, password, code, uuid) {
  const params = new URLSearchParams();
  params.append("userName", username);
  params.append("password", password);

  return request({
    url: 'oldUser/login',
    headers: {
      isToken: false,
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    method: 'post',
    data: params
  })
}

// 注册方法
export function register(data) {
  return request({
    url: '/register',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

// 获取用户详细信息
export function getInfo() {
  return request({
    url: '/oldUser/loginUser',
    method: 'get'
  })
}

// 退出方法
export function logout() {
  return request({
    url: '/oldUser/logout',
    method: 'post'
  })
}

// 获取验证码
export function getCodeImg() {
  return request({
    url: '/captchaImage',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })
}
