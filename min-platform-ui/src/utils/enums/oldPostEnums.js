import {byName, byValue} from "./enums";


export const postStatusEnum = {
  ENABLE: {desc: "启用", value: "ENABLE"},
  DISABLE: {
    desc: "禁用",
    value: "DISABLE"
  },
  values() {
    return [this.ENABLE, this.DISABLE]
  },
  byValue(value) {
    return byValue(this.values(), value);
  },
};
