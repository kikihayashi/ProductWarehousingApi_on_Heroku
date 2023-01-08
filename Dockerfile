#建構Docker Images
#1.使用 OpenJDK 11 image
FROM openjdk:11

#使用apk命令安裝MySQL客戶端
RUN apk add --no-cache mysql-client

# 設定環境變量
ENV MYSQL_HOST=localhost
ENV MYSQL_PORT=3306
ENV MYSQL_DATABASE=productwarehousing
ENV MYSQL_TIMEZONE=Asia/Taipei
ENV MYSQL_ENCODING=utf-8
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=kikihayashi0425

#將容器中的目錄掛載到主機上，可以在容器外存取容器中的文件
VOLUME /tmp

#定義了一個名為JAR_FILE的參數，並將其預設值設置為target/*.jar
ARG JAR_FILE=target/*.jar

#將指定的JAR檔複製到容器中，並將其命名為app.jar。
COPY ${JAR_FILE} app.jar

#使用了add指令下載了一個名為wait的工具，用於在啟動容器時等待MySQL服務啟動。
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.7.3/wait /wait
RUN chmod +x /wait

#啟動JVM並執行jar檔
ENTRYPOINT ["/wait","--timeout=60","--strict","--","java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

#啟動JVM並執行jar檔
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]



##建立程式資料夾(指的是在容器裡面，非實體主機目錄上)
#RUN mkdir /app
#
##將可執行的程式放在 /app 內
#COPY src/main/java/com/woody/productwarehousingapi/ /app
#
##設定執行工作目錄
#WORKDIR /app
#
##執行程式
#CMD java ProductwarehousingapiApplication
