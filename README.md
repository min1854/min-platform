[TOC]



# min-platform

- [github](https://github.com/min1854/min-platform)
- [gitee](https://gitee.com/min1854/min-platform)


[English](README-en.md)

觉得常用的脚手架不顺手，所以自己搭建了一个。功能与若依相同，实现了若依百分之九十的功能。优点：插件化的中间件集成，实现了 json 对象式枚举序列化与反序列，实现`BaseEnum` 接口即可实现，文件服务默认提供 db 方式；提供三种文件上传服务；由配置文件选择，抽象了用户登录相关逻辑，权限校验有默认提供实现过滤器与拦截器。

内置了生成器，通过 yml 进行配置，并且是最适合 mybatis 字段生成，可将实体类自动映射为枚举片段，严格执行代码规范。树表与单表功能，可一键生成完成功能。

ui 为若依原有页面，进行了重写与适配。

技术栈：
- jdk17
- mysql
- redis
- apiAssert
- lombok
- spring boot 3.0
- mybatis-plus