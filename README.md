#PublicCMS 2017 Preview

##获取可运行程序

http://git.oschina.net/sanluan/PublicCMS-war
https://github.com/sanluan/PublicCMS-war

##获取稳定版源码

http://git.oschina.net/sanluan/PublicCMS
https://github.com/sanluan/PublicCMS

##参与研发(预览版)

http://git.oschina.net/sanluan/PublicCMS-preview
https://github.com/sanluan/PublicCMS-preview

##相关下载及文档(知识库)

https://github.com/sanluan/PublicCMS-lib
https://git.oschina.net/sanluan/PublicCMS-lib

###预览版更新

BUG修复:

1. 内容推荐bug修复
1. 用户扩展字段类型bug修复
1. 用户添加bug修复
1. 部分敏感数据接口增加授权限制
1. 扩展字段为空时 显示全部扩展字段bug修复
1. 推荐位前台提交表单匿名提交空指针错误修复
1. 分类管理点击修改时提示需要选择信息bug修复
1. 管理后台新增用户、修改用户提示密码不一致bug修复

框架升级:

1. Spring Framework升级到4.3.4
1. Hibernate Search升级到5.5.5
1. Hibernate升级到5.1.2
1. Jackson升级到2.8.5
1. mysql-connector-java升级到5.1.40
1. 新增mybatis 3.4.1
1. 新增jedis2.9.0

新增功能:

1. 资源文件管理
1. 网站文件管理
1. 新增配置管理

其他提升:

1. 取消大部分匿名类写法
1. 增加内容扩展字段类型
1. 将方法内可复用变量提升为类静态变量
1. 增加工程内置前端页面
1. 配置中心登陆注册设置合并
1. 邮件发送改为线程池执行发送任务
1. 域名取消端口区分
1. 模板默认所有输出进行HTML转义
1. 关联关系表取消自增主键改为联合主键
1. 界面修改，LOGO修改
1. 持久层增加Mybatis
1. 重构内存缓存、增加redis缓存支持
1. 新增Hibernate Redis缓存组件
1. 模型由数据库存储改为文件存储

模板升级所需修改：

1. 需要以HTML输出的字段需要加 ?no_esc
1. 取消所有?html内置函数调用

数据库升级所需修改：

1. 根据database/mysql-change-log.sql时间轴执行最新修改脚本