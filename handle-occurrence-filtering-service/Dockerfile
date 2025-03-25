FROM amazoncorretto:24@sha256:67f62638c2d5d6f3caf4c396434d9de8906e2e7f850f82cfa75b9752b99224c2
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