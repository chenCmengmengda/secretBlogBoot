# secretBlogBoot

![JDK](https://img.shields.io/badge/JDK-1.8-green.svg)
![Maven](https://img.shields.io/badge/Maven-3.6.1-green.svg)
![MySQL](https://img.shields.io/badge/MySQL-5.7-green.svg)
![Redis](https://img.shields.io/badge/Redis-3.2.100-green.svg)
[![license](https://img.shields.io/badge/license-GPL%20v3-yellow.svg)](https://gitee.com/secret_C/secretBlogBoot/blob/master/LICENSE)

---

#### 介绍
secretBlog的springboot版本

主要实现功能:

* 权限管理：完备的系统权限管理，包括系统用户，角色，权限，菜单。（完成70%）

* 内容管理：管理系统用户所有文章，包括帖子、博客及文章评论。（已完成50%）

* 文件管理：提供多种方式（本地，七牛，其他OSS）的文件管理和存储方式。（已实现本地存储，可进行新建文件夹、上传文件、重命名、删除）

* 日志管理：用于记录用户对系统的操作。（已完成）

* 代码生成。（已完成）

* 后面想到什么做什么

#### 软件架构
springboot

springSecurity

mybatis-plus

redis

mysql 5.7

后台采用OK-ADMIN模板

#### 截图
![avatar](http://secret_c.gitee.io/secretblogboot/img1.png)
![avatar](http://secret_c.gitee.io/secretblogboot/img2.png)
![avatar](http://secret_c.gitee.io/secretblogboot/img3.png)
![avatar](http://secret_c.gitee.io/secretblogboot/img4.png)
![avatar](http://secret_c.gitee.io/secretblogboot/img5.png)
![avatar](http://secret_c.gitee.io/secretblogboot/img6.png)





#### 安装教程
1.maven导包

2.common工程下application-center中修改数据库连接

3.导入doc/sql下的sql文件

4.运行BlogAdminApplication

5.访问localhost:8086

6.登录:secretC  111111

#### 部署说明
执行命令：
1.mvn package -Dmaven.test.skip=true
2.java -jar 命令运行xxApplication启动类所在工程打包后的jar包文件
例如 java -jar secretBlogBoot-admin-1.0-SNAPSHOT.jar

#### 联系作者
<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=1029693356&site=qq&menu=yes"><img border="0" src="http://wpa.qq.com/pa?p=2:1029693356:41" alt="点击这里给我发消息" title="点击这里给我发消息"/></a>

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request

### 致谢

本项目参考他人的代码，感谢各位大神的付出！

* 广大的开源爱好者

* [ok-admin](http://ok-admin.xlbweb.cn/) 一款开源的layui后台管理模板

* [javabb-fs](https://gitee.com/imqinbao/javabb-fs) 文件服务器，支持本地和第三方云存储

* [OneBlog](https://gitee.com/yadong.zhang/DBlog) 一个简洁美观、功能强大并且自适应的Java博客。

待续...
