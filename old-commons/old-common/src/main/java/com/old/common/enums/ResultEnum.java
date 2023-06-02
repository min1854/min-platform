package com.old.common.enums;


import com.old.common.base.BaseException;
import com.old.common.base.BaseResultEnum;
import com.old.common.base.DefaultBaseResultEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@ToString
@AllArgsConstructor
public enum ResultEnum implements BaseResultEnum {
    SUCCESS(200, "成功"),
    FAIL(500, "失败"),
    FEIGN_ERROR(502, "远程调用失败"),


    BAD_REQUEST(400, "{}"),
    UNAUTHORIZED(401, "用户未授权"),


    FORBIDDEN(403, "授权已过期"),


    REQUESTED_RANGE_NOT_SATISFIABLE(416, "无法访问"),


    PARAMS_TEST(5001, "拼接参数测试，{}，{}"),


    FEIGN_FAIL(5002, "{}"),

    USERNAME_NOT_FOUND(5003, "用户不存在"),


    USERNAME_OR_PASSWORD_ERROR(5004, "用户名或密码错误"),


    NOT_WEB(5005, "非 web 环境"),


    FILE_UPLOAD_FAIL(5006, "文件上传失败"),
    EMPTY_FILE(5007, "不存在需要上传的文件"),
    REQUEST_END(5008, "请求已结束"),


    UPDATE_FAIL(5009, "更新失败"),
    SAVE_FAIL(5010, "保存失败"),
    DELETE_FAIL(5011, "删除失败"),

    USER_ROLE_DEFICIENCY(5021, "用户角色权限不足"),

    NO_REQUEST_PERMISSION(5022, "用户没有请求权限"),

    NO_MESSAGE_BODY(5023, "没有可发送的内容"),

    WRITE_ERROR(5024, "发送响应时出现异常"),

    FORWARD_ERROR(5025, "转发异常"),

    USER_NOT_EXISTS(5026, "用户不存在"),

    ILLEGAL_ACTION(5027, "非法操作"),

    USER_STATUS_NOT_EXISTS(5028, "用户状态不存在"),

    DEPT_DELETE(5029, "部门已被删除"),

    PASSWORD_IDENTICAL(5030, "新旧密码不可相同"),

    NOT_ALLOWED_UPDATE_ADMIN_ROLE(5031, "不允许操作管理员角色"),

    NO_POWER_UPDATE_ROLE(5032, "没有权限访问角色数据"),

    ROLE_NOT_EXISTS(5032, "角色不存在"),

    ADMIN_NOT_ALLOWED_OPERATION(5033, "admin 角色不允许操作"),

    ROLE_NAME_EXISTS(5033, "角色名：{}，已存在"),

    ROLE_KEY_EXISTS(5033, "角色权限：{}，已存在"),

    DEPT_NAME_NOT_UNIQUE(5034, "部门名称已存在"),

    DEPT_DISABLE(5035, "部门停用，不允许新增"),

    NOT_POWER_OPERATE_ADMIN_ROLE(5036, "没有权限操作 admin 角色"),

    CHILD_MENU_EXISTS_NOT_DELETE(5036, "存在子菜单,不允许删除"),

    MENU_ALLOCATED_CANT_DELETE(5036, "菜单已分配,不允许删除"),

    LOGIN_OUT_OF_LIMIT(5037, "登陆超出限制，请{}分钟后再试"),

    FILE_NOT_EXISTS(5007, "文件不存在"),


    ;
    private static final Map<Integer, ResultEnum> RESULT_MAP;

    static {
        RESULT_MAP = new ConcurrentHashMap<>();
        for (ResultEnum value : values()) {
            RESULT_MAP.put(value.getCode(), value);
        }
    }

    private final Integer code;
    private final String message;

    public static BaseResultEnum of(Integer code, String message) {
        if (code == null || message == null) {
            throw new BaseException("查找结果状态失败，两者不可为空。" + code + " " + message);
        }
        if (RESULT_MAP.containsKey(code)) {
            ResultEnum resultEnum = RESULT_MAP.get(code);
            if (resultEnum.getMessage().equals(message)) {
                return resultEnum;
            } else {
                return new DefaultBaseResultEnum(code, message);
            }
        }
        throw new BaseException("不存在的状态码：" + code + "，与错误信息：" + message);
    }
}
