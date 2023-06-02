import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function menuBoToPerms(menuBos, permissions) {

  for (let i in menuBos) {
    const item = menuBos[i]
    if (item.perms !== null && item.perms !== undefined && item.perms !== '') {
      permissions.push(item.perms);
    }

    if (item.children !== undefined && item.children !== null) {
      menuBoToPerms(item.children, permissions);
    }
  }
};
