#file beats stage
FROM alpine:3.13.4 as beat

ARG FILEBEAT_VERSION=7.10.0

RUN apk update && \
    apk --no-cache add curl wget && \
    #wget https://download.elastic.co/beats/filebeat/filebeat-${FILEBEAT_VERSION}-x86_64.tar.gz -O /opt/filebeat.tar.gz && \
    wget https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-${FILEBEAT_VERSION}-linux-x86_64.tar.gz -O /opt/filebeat.tar.gz && \
    cd /opt && \
    tar xzvf filebeat.tar.gz && \
    rm -rf filebeat*.gz && \
    mv filebeat-* filebeatsapp && \
    apk del wget
        
#application stage
FROM alpine:3.13.4 as appstage

COPY *.jar /appdir/application.jar
COPY application.sh /sheldir/
COPY filebeat.yml log4j2.xml /configdir/

#final image
FROM openjdk:8
MAINTAINER raju

ARG APP_HOME_DIR=/opt/app
ARG APP_CONFIG_DIR=/opt/config
ARG APP_LOGS_DIR=/var/logs/appLogs

#RUN addgroup --system spring && adduser --system spring -group spring
#USER spring:spring

COPY --from=beat /opt/filebeatsapp/filebeat /bin/filebeat
COPY --from=appstage /appdir/application.jar $APP_HOME_DIR/
COPY --from=appstage /sheldir/application.sh $APP_HOME_DIR/
COPY --from=appstage /configdir/* $APP_CONFIG_DIR/

RUN mkdir -p $APP_LOGS_DIR && \
	chmod 755 ${APP_HOME_DIR}/application.*

VOLUME $APP_CONFIG_DIR
VOLUME $APP_LOGS_DIR

ENV APP_CONFIG_DIR $APP_CONFIG_DIR
WORKDIR $APP_HOME_DIR

ENTRYPOINT ["application.sh"]