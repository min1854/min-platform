import {byName, byValue} from "./enums";







export const menuTypeEnums = {
  DIR: {
    desc: "目录",
    value: "M",
    name: "DIR"
  },
  MENU: {
    desc: "菜单",
    value: "C",
    name: "MENU"
  },
  BUTTON: {
    desc: "按钮",
    value: "F",
    name: "BUTTON"
  },
  values() {
    return [
      this.DIR,
      this.MENU,
      this.BUTTON,
    ]
  },
  byName(name) {
    return byName(this.values(), name);
  },
  byValue(value) {
    return byValue(this.values(), value);
  },
};


export const menuVisibleEnums = {
  SHOW: {
    desc: "显示",
    value: 0,
    name: "SHOW"
  },
  HIDDEN: {
    desc: "隐藏",
    value: 1,
    name: "HIDDEN"
  },
  values() {
    return [
      this.SHOW,
      this.HIDDEN,
    ]
  },
  byName(name) {
    return byName(this.values(), name);
  },
  byValue(value) {
    return byValue(this.values(), value);
  },
};


export const menuStatusEnums = {
  USE: {
    desc: "正常",
    value: 0,
    name: "USE"
  },
  STOP: {
    desc: "停用",
    value: 1,
    name: "STOP"
  },
  values() {
    return [
      this.USE,
      this.STOP,
    ]
  },
  byName(name) {
    return byName(this.values(), name);
  },
  byValue(value) {
    return byValue(this.values(), value);
  },
};


