#建構Docker Images
FROM spring-boot-app:v1

# 設定環境變量
#註：在 Dockerfile 中設定環境變量，代表這些環境變量將在建立 Docker 鏡像時就已經設定好了。
#也就是說，在鏡像建立完成後，無論如何修改這些環境變量的值，都無法在這個鏡像上生效。
ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME ${DRIVER_CLASS_NAME}
ENV MYSQL_DATABASE ${DATABASE}
ENV MYSQL_USER ${USER}
ENV MYSQL_PASSWORD ${PASSWORD}
ENV MYSQL_HOST ${HOST}
ENV MYSQL_PORT ${PORT}
ENV MYSQL_TIMEZONE ${TIMEZONE}
ENV MYSQL_ENCODING ${ENCODING}
#Max size per file the upload supports is 10MB
ENV SPRING_SERVLET_MULTIPART_MAX_FILE_SIZE ${MAX_FILE_SIZE}
#Max size of the whole request is 50MB;
ENV SPRING_SERVLET_MULTIPART_MAX_REQUEST_SIZE ${MAX_REQUEST_SIZE}
#台灣時區(GMT+8)英國時區(GMT+0)
ENV JACKSON_TIME_ZONE ${JSON_TIME_ZONE}
#時間格式
ENV JACKSON_DATE_FORMAT ${JSON_DATE_FORMAT}

#定義了一個名為JAR_FILE的參數，並將其預設值設置為target/*.jar
ARG JAR_FILE=target/*.jar

#將指定的JAR檔複製到容器的app資料夾中，並將其命名為app.jar。
COPY ${JAR_FILE} /app/app.jar

