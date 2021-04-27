FROM alpine:3.13.4 as helperimg

ARG FILEBEAT_VERSION=7.10.0
ARG APP_HOME_DIR=/opt/app
ARG APP_CONFIG_DIR=/opt/config
ARG APP_LOGS_DIR=/var/logs/appLogs

ARG TEMP_APP_DIR=/app
ARG TEMP_CONFIG_DIR=/config

RUN mkdir -p $TEMP_APP_DIR $TEMP_CONFIG_DIR

COPY *.jar $TEMP_APP_DIR/application.jar
COPY application.sh $TEMP_APP_DIR/
COPY filebeat.yml log4j2.xml $TEMP_CONFIG_DIR/

RUN apk update && \
    apk --no-cache add curl wget && \
    #wget https://download.elastic.co/beats/filebeat/filebeat-${FILEBEAT_VERSION}-x86_64.tar.gz -O /opt/filebeat.tar.gz && \
    wget https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-${FILEBEAT_VERSION}-linux-x86_64.tar.gz -O /opt/filebeat.tar.gz && \
    cd /opt && \
    tar xzvf filebeat.tar.gz && \
    rm -rf filebeat*.gz && \
    mv filebeat-* filebeatsapp && \
    apk del wget
        
FROM openjdk:8
MAINTAINER raju

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

RUN mkdir -p $APP_HOME_DIR $APP_CONFIG_DIR $APP_LOGS_DIR 

VOLUME $APP_CONFIG_DIR
VOLUME $APP_LOGS_DIR

COPY --from=helperimg /opt/filebeatsapp/filebeat /bin/filebeat
COPY --from=helperimg $TEMP_APP_DIR/* $APP_HOME_DIR/
COPY --from=helperimg $TEMP_CONFIG_DIR/* $APP_CONFIG_DIR/


#RUN mv ${APP_HOME_DIR}/*.jar ${APP_HOME_DIR}/application.jar
RUN chmod 755 ${APP_HOME_DIR}/application.jar \
    chmod 755 ${APP_HOME_DIR}/application.sh

ENV APP_CONFIG_DIR $APP_CONFIG_DIR
WORKDIR $APP_HOME_DIR

ENTRYPOINT ["application.sh"]