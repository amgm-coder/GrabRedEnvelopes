抢红包 Demo
使用 SpringBoot + Redis(lua) + MySQL8 实现

Redis 需要添加 BloomFilter 插件

可以使用 docker 安装 mysql 及 redis

```shell script
docker run -p 3306:3306 --name MYSQL8.0 -e MYSQL_ROOT_PASSWORD=123456 -d mysql/mysql-server:8.0.21-1.1.17
docker run -d -p 6379:6379 --name redis redislabs/rebloom:latest
```


参照 https://www.cnblogs.com/zxporz/p/10813709.html