Building Blocks - 积木
=====================

![logo](http://7xk54v.com1.z0.glb.clouddn.com/images/logo/icon.png)

>**积木** - 一个以知乎日报作为数据展现内容；以抽屉菜单作为功能扩展入口；遵循 Material Design 设计规范的应用；好吧，我知道，这听起来就很酷！

### How to use - 如何使用

>dev 分支由 [troyliu0105](https://github.com/troyliu0105) 同学全力维护，他会不断对**积木**进行重构与增加好玩的功能，这非常酷，欢迎 **Star** 与 **Fork** 此分支！

那么，你该如何利用「她」呢？

好啦，其实我已经帮你完成了应用整体框架的搭建，你需要做的，仅仅是依据自己的实际需求，简单的替换下数据来源，比如在 [APIStore](http://apistore.baidu.com/) 上就有详细的类别供你选择，然后再改改 UI，一款全新应用就完成啦！

想想，是不是还有点小激动？那么赶紧 **Star** 与 **Fork** 吧！你的支持将成为我最大的动力与褒奖！

## Update Log - 更新日志

### 0.7.0

- 新增 - 夜间模式
- 新增 - 程序崩溃日志收集
- 新增 - 自动更新
- 新增 - `html+`模式（其实就是修改了html标签==）
- 修复 - 自动清理功能的错误
- 修复 - 主界面刷新闪烁BUG
- 修改 - 部分UI

##### 已知BUG

- **位置获取**功能在 Android 6.0 上导致崩溃（一加1实测）
- 部分文章显示有问题(使用`html+`模式无影响)

### v0.6.0

- 新增 - FAB刷新
- 新增 - html模式下的页面缓存
- 新增 - json模式(速度更快，但部分文章显示有问题)
- 新增 - 自动清理过期缓存
- 新增 - SQLite数据储存
- 重构 - 使用MVP进行重构
- 新增 - 由[Mao](http://weibo.com/cat93/)提供的Logo
- 修改 - 部分的UI

### v0.5.1

- 抛弃 - CardView，回归朴实，更符合设计规范
- 抛弃 - 第三方 DrawerLayout，那家伙华而不实
- 抛弃 - WebView 顶部栏中文章标题，取而代之为分享按钮
- 新增 - 搜索功能，在跳转页面显示搜索结果
- 新增 - 手势右滑，返回上级页面功能（v0.5.0 中出现的 Bug 已经修复）
- 优化 - 将功能扩展作为抽屉菜单的子选项
- 重构 - 尽可能将代码写得优雅和规整

## Demo - 示例

[快速下载](http://7xk54v.com1.z0.glb.clouddn.com/app/bb/0.7.0.apk)

## Screenshots - 预览
<img src="/screenshots/s1.png" alt="screenshot" title="screenshot" width="270" height="486" />  <img src="/screenshots/s1-1.png" alt="screenshot" title="screenshot" width="270" height="486" />

<img src="/screenshots/s1-2.png" alt="screenshot" title="screenshot" width="270" height="486" />  <img src="/screenshots/s2.png" alt="screenshot" title="screenshot" width="270" height="486" />

<img src="/screenshots/s3.png" alt="screenshot" title="screenshot" width="270" height="486" />  <img src="/screenshots/s4.png" alt="screenshot" title="screenshot" width="270" height="486" />  

<img src="/screenshots/s5.png" alt="screenshot" title="screenshot" width="270" height="486" />  <img src="/screenshots/s6.png" alt="screenshot" title="screenshot" width="270" height="486" />  

<img src="/screenshots/s7.png" alt="screenshot" title="screenshot" width="270" height="486" />  <img src="/screenshots/s8.png" alt="screenshot" title="screenshot" width="270" height="486" />

<img src="/screenshots/s9.png" alt="screenshot" title="screenshot" width="270" height="486" />  <img src="/screenshots/s9-1.png" alt="screenshot" title="screenshot" width="270" height="486" />

## Dependencies - 开源项目

 - [glide](https://github.com/bumptech/glide)
 - [butterknife](https://github.com/JakeWharton/butterknife)
 - [android-crop](https://github.com/jdamcd/android-crop)
 - [android-async-http](https://github.com/loopj/android-async-http)
 - [SwipeBackLayout](https://github.com/ikew0ng/SwipeBackLayout)
 - [Jsoup](http://http://jsoup.org/)

## Thanks - 感谢你们

- 感谢 [drakeet](https://github.com/drakeet) 及他的 [妹纸&gank.io](https://github.com/drakeet/Meizhi)， 其代码写得真的非常漂亮：)，从中学到了很多并运用到了项目中（依葫芦画瓢而已啦）

- 感谢 [Izzy Leung](https://github.com/izzyleung) 及他的 [知乎日报·净化](https://github.com/izzyleung/ZhihuDailyPurify)，项目最初的原型就来自于此，感谢其提供了详细的知乎日报 API 说明

## Contributors

- dev version: [troyliu0105](https://github.com/troyliu0105)
- Logo: [Mao](http://weibo.com/cat93/) & [troyliu0105](https://github.com/troyliu0105)

## Contact - 联系我

- Weibo：[汤奇V](http://weibo.com/qiktang)
- Blog: [http://itangqi.me](http://itangqi.me)
- Gmail：[imtangqi#gmail.com](mailto:imtangqi@gmail.com "欢迎与我联系")

## Project Home - 项目主页

[http://itangqi.me/2015/09/03/building-blocks/](http://itangqi.me/2015/09/03/building-blocks/)

## License - 许可证

源代码在 GPL v3 协议下发布

[LICENSE](/LICENSE)
