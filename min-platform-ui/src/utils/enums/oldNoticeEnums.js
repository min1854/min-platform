import {byValue} from "./enums";




export const noticeTypeEnum = {
  NOTICE: {
    desc: "通知",
    value: "NOTICE"
  },
  ANNOUNCEMENT: {
    desc: "公告",
    value: "ANNOUNCEMENT"
  },
  values() {
    return [
      this.NOTICE,
      this.ANNOUNCEMENT,
    ]
  },
  byValue(value) {
    return byValue(this.values(), value);
  },
};



export const noticeStatusEnum = {
  NORMAL: {
    desc: "正常",
    value: "NORMAL"
  },
  CLOSE: {
    desc: "关闭",
    value: "CLOSE"
  },
  values() {
    return [
      this.NORMAL,
      this.CLOSE,
    ]
  },
  byValue(value) {
    return byValue(this.values(), value);
  },
};


