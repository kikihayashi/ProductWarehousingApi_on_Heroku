FROM mysql:8.0 AS db

# 設定環境變量
# LANG的設定是讓terminal對mysql編碼為UTF-8，以防資料變成亂碼
ENV LANG=C.UTF-8
ENV MYSQL_DATABASE=productwarehousing
ENV MYSQL_ROOT_PASSWORD=kikihayashi0425

ARG SQL_FILE=src/main/resources/sqlcommand/*.sql

COPY ${SQL_FILE} /docker-entrypoint-initdb.d/

#建構Docker Images
FROM openjdk:11 AS web

# 設定環境變量
#註：在 Dockerfile 中設定環境變量，代表這些環境變量將在建立 Docker 鏡像時就已經設定好了。
#也就是說，在鏡像建立完成後，無論如何修改這些環境變量的值，都無法在這個鏡像上生效。
ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
ENV MYSQL_DATABASE=productwarehousing
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=kikihayashi0425
ENV MYSQL_HOST=mysql-server
ENV MYSQL_PORT=3306
ENV MYSQL_TIMEZONE=Asia/Taipei
ENV MYSQL_ENCODING=utf-8
ENV SPRING_SERVLET_MULTIPART_MAX_FILE_SIZE=10MB
ENV SPRING_SERVLET_MULTIPART_MAX_REQUEST_SIZE=50MB
ENV JACKSON_TIME_ZONE='GMT+8'
ENV JACKSON_DATE_FORMAT='yyyy-MM-dd HH:mm:ss'

#使用了add指令下載了一個名為wait的工具，用於在啟動容器時等待MySQL服務啟動。
#https://github.com/ufoscout/docker-compose-wait
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.9.0/wait /wait
RUN chmod +x /wait

#定義了一個名為JAR_FILE的參數，並將其預設值設置為target/*.jar
ARG JAR_FILE=target/*.jar
#將指定的JAR檔複製到容器的app資料夾中，並將其命名為app.jar。
COPY ${JAR_FILE} /app/app.jar










