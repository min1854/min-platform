import { byName, byValue } from './enums'


export const dataScopeEnums = {
  ALL: {
    desc: "全部数据权限",
    name: "ALL"
  },
  CUSTOMIZE: {
    desc: "自定数据权限",
    name: "CUSTOMIZE"
  },
  CURRENT_DEPT: {
    desc: "本部门数据权限",
    name: "CURRENT_DEPT"
  },
  CURRENT_DEPT_AND_BELOW: {
    desc: "本部门及以下数据权限",
    name: "CURRENT_DEPT_AND_BELOW"
  },
  INDIVIDUAL: {
    desc: "个人数据",
    name: "INDIVIDUAL"
  },
  values() {
    return [
      this.ALL,
      this.CUSTOMIZE,
      this.CURRENT_DEPT,
      this.CURRENT_DEPT_AND_BELOW,
      this.INDIVIDUAL,
    ]
  },
  byName(name) {
    return byName(this.values(), name);
  },
};

export const roleStatusEnums = {
  ENABLE: {
    desc: '启用',
    value: true,
    name: 'ENABLE'
  },
  DISABLE: {
    desc: '禁用',
    value: false,
    name: 'DISABLE'
  },
  values() {
    return [
      this.ENABLE,
      this.DISABLE
    ]
  },
  byName(name) {
    return byName(this.values(), name)
  },
  byValue(value) {
    return byValue(this.values(), value)
  }
}


