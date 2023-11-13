1. 创建项目，项目的pom.xml设置，目录结构设置,   配置文件设置. 

1。 创建数据库及表，插入数据


2. dao层重新开发
3. biz层的测试

-------
3. Controller
4. 前端页面的测试

------
新增功能:
1. 使用mock测试 controller
2. 利用kafka完成邮件发送.

---------
controller测试方式:
1. 启服务器，用浏览器测试:
     问题:  记地址
2. API web接口: 框架,
    swagger  -> springMVC
   问题:安全性
3. 客户端工具  postman
     可以写脚本.
4. 集成测试:  mock  ->  模拟web请求


session管理： spring session + redis实现
问题：将应用服务器tomcat的session转移到redis上去，实现分布式会话管理，并解决微服务下的session管理问题
1.依赖
#
    <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    <dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
    </dependency>

redis配置
#
      redis:
    #默认第0号数据库
    database: 0  #redis默认0-15号库
    host: 127.0.0.1  #使用docker后还需加上用户名和密码，且redis中配置访问的主机的ip，以提高安全性
    port: 6379
    lettuce:
      pool:
        #最大空闲连接数
        max-idle: 8
        #最小空闲连接数
        min-idle: 0
        #最大活动连接数
        max-active: 8
        #最大等待时间
        max-wait: -1ms
    #建立连接的超时时间
    timeout: 180
    #session 存储方式
    session: redis #将session保存到redis中





=================分布式文件服务 fastDFS ==================
fastDFS的dockers容器，在容器测试上传 + 测试远程访问
1) tracker 安装
                    容器名      加入到ycnetwork 网络     将那个本地目录挂载到linux中                                             镜像名         容器名
docker run -d --name tracker --network=ycnetwork -v C:\Users\28894\Documents\dockercontainer\fastdfs\tracker:/var/fdfs delron/fastdfs tracker
以上成功后 ycnetwork给tracker容器分配了 172.18.0.2
2) storage 安装：实际上安装了两个组件  storage + nginx（http服务器）
                将本机的8888映射到nginx端口8888  加入到ycnetwork网络    storage将细腻些
docker run -d -p 8888:8888 --name storage --network=ycnetwork -e TRACKER_SERVER=192.168.32.128:22122 -v /mydata/fastdfs/storage:/var/fdfs -e GROUP_NAME=group1 delron/fastdfs storage

  
小萌神后台

==================== 复习 ==========================
1. springboot profile
    如何定义 配置文件名，
   如何在不同环境下
