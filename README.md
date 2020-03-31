## 项目简介

**项目Github**：<https://github.com/hongfurui2014/exam_online_hongfurui> 

**考试前台**：<http://exam.itdupan.com/> 

**后台管理**：<http://hongfurui.itdupan.com/> 

​	在线考试系统，大四毕设，采用vue与springcloud微服务架构开发，前后端完全分离，json格式数据交互，全部由本人独立开发完成。目前项目已部署在本人的学生版阿里云centos7服务器上。

​	主要功能有：对班级和科目的管理；对试题和试卷的管理（包含excel文件模板大批量一次性上传试题到题库，自动从题库中抽题出试卷）；用户、角色、权限三级设计实现方法级别的细粒度权限控制；aop后置通知实现整个系统重要操作日志的记录。

## 项目技术栈

### 前端

- 前端后台管理系统由vue与elementUI搭建，路由使用vue-router，前端在线考试系统由nuxt搭建，ajax请求均使用axios。
- vsCode工具开发

### 后台

- mysql数据库，mybatis(通用Mapper、PageHelper分页助手)

- springBoot(spring、springMVC)

- springCloud(eureka、zuul、feign)搭建微服务工程

- nginx反向代理域名

- 采用jwt+rsa加密生成token，写入cookie的方式做无状态登录

- maven、idea、postman工具开发

## 说明

### 目录说明

- `exam_online_hfr_admin`为前端后台管理项目，`npm install`安装依赖后`npm run dev`可运行
- `exam_online_hfr_portal`为前端在线开心系统项目，`npm install`安装依赖后`npm run dev`可运行
- `exam_online_hfr_api`为后端微服务项目，maven下载完依赖，修改application.yml配置文件中的数据库密码和jwt配置后即可运行

### 后台api接口说明

- 接口基准地址：`http://zuul.itdupan.com/api/`
  - 示例：`http://zuul.itdupan.com/api/school/grade/findGrades`，其中api为zuul网关前缀，school为zuul转发到的微服务名称路径，/grade/findGrades为controller为controller请求访问路径。所有请求都经过zuul网关，已在网关zuulFilter中做鉴权。
- 服务端已开启cors跨域支持
  - 只允许`http://hongfurui.itdupan.com`和`http://exam.itdupan.com`两个域名对`http://zuul.itdupan.com`进行跨域访问

- 请求方式

```
GET 查询
POST 新增
PUT 更新
DELETE 删除
```

- 使用HttpStatus Code做标识状态

```
200 OK 响应成功
201 CREATED 新增或更新成功
204 DELETED 删除成功
400 BAD_REQUEST 请求參數不合法
401 UNAUTHORIZED 身份不合法
403 FORBIDDEN 权限不足
404 NOT_FOUND 資源服務器未找到
500 INTERNAL_SERVER_ERROR 服務器內部錯誤
```

添加 删除 修改失败将返回状态码自定义状态码：600

- 后台返回格式统一使用json格式，返回样式如下：

```json
{
    "httpCode": 200,
    "message": "查询所有班级成功！",
    "data": [
        {
            "gradeId": 20,
            "gradeName": "16级软件工程2班"
        },
        {
            "gradeId": 21,
            "gradeName": "16级软件工程1班"
        }
    ]
}
```

- 捕获全局500 RuntimeException，返回样式如下： 

```json
{
    "httpCode": 500,
    "message": "操作失败，系统未知异常，请尽快反馈给系统管理员！",
    "exceptionTime": "2020-03-12 17:32:55"
}
```

### 其它说明

- `资料`中的sql文件为最干净的数据库，后台登录账号和密码均为`demo_user`，`导入试题模板.xlsx`按照格式即可导入试题
- 本机SwitchHosts配置

```
# 在线考试系统java后台网关
127.0.0.1 zuul.itdupan.com
# 在线考试系统前端
127.0.0.1 exam.itdupan.com
# 在线考试系统后台管理
127.0.0.1 hongfurui.itdupan.com
```

- nginx代理配置如下

```nginx
server {
    listen       80;
    server_name  zuul.itdupan.com;	# 网关

    proxy_set_header X-Forwarded-Host $host;
    proxy_set_header X-Forwarded-Server $host;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header Host $host;

    location / {
        proxy_pass http://127.0.0.1:10011;

        proxy_connect_timeout 600;
        proxy_read_timeout 600;
    }

}

server {
    listen       80;
    server_name  hongfurui.itdupan.com;		# 后台管理

    proxy_set_header X-Forwarded-Host $host;
    proxy_set_header X-Forwarded-Server $host;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header Host $host;

    location / {
        proxy_pass http://127.0.0.1:8080;

        proxy_connect_timeout 600;
        proxy_read_timeout 600;
    }

}

server {
    listen       80;
    server_name  exam.itdupan.com;	# 前台

    proxy_set_header X-Forwarded-Host $host;
    proxy_set_header X-Forwarded-Server $host;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header Host $host;

    location / {
        proxy_pass http://127.0.0.1:9999;

        proxy_connect_timeout 600;
        proxy_read_timeout 600;
    }

}
```

## 项目待改进优化的一些问题

- axios下载的文件打开出错
- 自动抽题组卷算法待优化
- nuxt的路由导航守卫问题
- mouted()钩子函数未能有效获取vuex中的数据，时机问题
- 正在答题时若刷新则已做试题的状态重置，等以后有时间考虑对答题情况进行watch深度监控，写入redis读取应该就能解决
- 答题时时间倒计时以及计时结束自动提交试卷功能，下一步考虑使用quartz框架解决
- aop记录的日志还是比较粗粒度，以后有时间再考虑优化了
- 分布式事务问题
