import {byName, byValue} from "./enums";


export const userStatusEnums = {
  ENABLE: {desc: "启用", value: 0, name: "ENABLE"},
  DISABLE: {
    desc: "禁用",
    value: 1,
    name: "DISABLE"
  },
  values() {
    return [this.ENABLE, this.DISABLE]
  },
  byName(name) {
    return byName(this.values(), name);
  },
  byValue(value) {
    return byValue(this.values(), value);
  },
};

export const userSexEnums =
  {
    MALE: {desc: "男", value: 0, name: "MALE"},
    FEMALE: {desc: "女", value: 1, name: "FEMALE"},
    UNKNOWN: {desc: "未知", value: 2, name: "UNKNOWN"},
    values() {
      return [this.MALE, this.FEMALE, this.UNKNOWN]
    },
    byName(name) {
      return byName(this.values(), name);
    },
    byValue(value) {
      return byValue(this.values(), value);
    },

  };

