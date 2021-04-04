FROM openjdk:8-alpine as fbeat
ENV FILEBEAT_VERSION=7.10.0

RUN apk update && \
    apk --no-cache add curl wget && \
    #wget https://download.elastic.co/beats/filebeat/filebeat-${FILEBEAT_VERSION}-x86_64.tar.gz -O /opt/filebeat.tar.gz && \
    wget https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-${FILEBEAT_VERSION}-linux-x86_64.tar.gz -O /opt/filebeat.tar.gz && \
    cd /opt && \
    tar xzvf filebeat.tar.gz && \
    rm -rf filebeat*.gz && \
    mv filebeat-* filebeatsapp
    #cd filebeat-* && \
    #cp filebeatsapp/filebeat /bin && \
    apk del wget
    
    
FROM openjdk:8-alpine
MAINTAINER raju

ARG HOST_APP_JAR_LOC
ARG APP_HOME_DIR=/opt/app
ARG APP_CONFIG_DIR=/opt/config
ARG APP_LOGS_DIR=/var/logs/appLogs

COPY --from=fbeat /opt/filebeatsapp/filebeat /bin/filebeat

RUN mkdir -p $APP_HOME_DIR \
    mkdir -p $APP_CONFIG_DIR \
    mkdir -p $APP_LOGS_DIR \
    #ls -lrt /opt/app \
    echo $HOST_APP_JAR_LOC

VOLUME $APP_CONFIG_DIR
VOLUME $APP_LOGS_DIR

COPY *.jar $APP_HOME_DIR/application.jar
COPY filebeat.yml $APP_CONFIG_DIR/
COPY log4j2.xml $APP_CONFIG_DIR/
COPY application.sh $APP_HOME_DIR/

#RUN mv ${APP_HOME_DIR}/*.jar ${APP_HOME_DIR}/application.jar
RUN chmod 755 ${APP_HOME_DIR}/application.jar
RUN chmod 755 ${APP_HOME_DIR}/application.sh

ENV APP_CONFIG_DIR $APP_CONFIG_DIR

#ENTRYPOINT ["java","-jar","/opt/app/application.jar","--spring.config.location=file:${APP_CONFIG_DIR}/","--logging.config=file:${APP_CONFIG_DIR}/log4j2.xml"]
#CMD ["filebeat", "-e", "-c","${APP_CONFIG_DIR}/filebeat.yml","-e","-d","\"*\""]

ENTRYPOINT ["sh","/opt/app/application.sh"]