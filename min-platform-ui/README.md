## 开发

```bash
# 安装依赖
npm install

# 建议不要直接使用 cnpm 安装依赖，会有各种诡异的 bug。可以通过如下操作解决 npm 下载速度慢的问题
npm install --registry=https://registry.npmmirror.com

# 启动服务
npm run dev
```

浏览器访问 http://localhost:80

## 发布

```bash
# 构建测试环境
npm run build:stage

# 构建生产环境
npm run build:prod
```


- []()
- response 就是数据了， response = r(message="ok", code=200, data=)， response = r
- el-select el-radio 可以绑定对象作为值，但 el-radio 无法回显
- [掘金-前端枚举的项目实践](https://juejin.cn/post/7212427549610344509?share_token=e8c5fc4a-957d-4cac-9dbf-643101bbf4bd#heading-4)
- [掘金-前端如何优雅地使用枚举](https://juejin.cn/post/7193526127573336123)
- [cdsn-Vue项目踩坑：el-switch等用1，0绑定无法渲染的问题](https://blog.csdn.net/SundayGoG/article/details/120664839)