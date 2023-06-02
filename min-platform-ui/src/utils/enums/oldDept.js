import {byName, byValue} from "./enums";







export const deptStatusEnums = {
  ENABLE: {desc: "启用", value: true, name: "ENABLE"},
  DISABLE: {
    desc: "禁用",
    value: false,
    name: "DISABLE"
  },
  values() {
    return [
      this.ENABLE,
      this.DISABLE,
    ]
  },
  byName(name) {
    return byName(this.values(), name);
  },
  byValue(value) {
    return byValue(this.values(), value);
  },
};

