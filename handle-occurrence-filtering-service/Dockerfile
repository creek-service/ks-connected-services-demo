FROM amazoncorretto:22@sha256:52aa61005936fdd8ec782dee63d0ce667532eac941d3822eece83e9f4f397059
ARG APP_NAME
ARG APP_VERSION
ENV VERSION=$APP_VERSION

LABEL org.opencontainers.image.source=https://github.com/creek-service/ks-connected-services-demo/tree/main/handle-occurrence-filtering-service

RUN yum update -y
RUN yum install -y tar lsof

RUN mkdir -p /opt/creek

COPY bin /bin
COPY log4j /log

COPY ${APP_NAME}-${APP_VERSION}.tar /opt/creek
WORKDIR /opt/creek
RUN tar xf ${APP_NAME}-${APP_VERSION}.tar
RUN ln -s ${APP_NAME}-${APP_VERSION} service

ENTRYPOINT ["/bin/run.sh"]