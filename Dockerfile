FROM library/java:8-jre-alpine

ARG version

WORKDIR /opt/camunda

ADD target/offers-batch-settlement-${version}.jar offers-batch-settlement.jar
COPY entrypoint.sh .

# install bash, python, pip and sb cloud tools.
# add camunda group and user and change ownership of the camunda jar.
# TODO: consider including bash, curl  and python inside the FROM docker image?
# TODO: the url to the sb-cloud-user-tools will eventually change
# TODO: pyyaml will be install via the sb-cloud-user-tools in future version
RUN apk --no-cache add \
    bash \
    curl \
    python \
    py-pip && \
    pip install --upgrade pip && \
    pip install pyyaml && \
    addgroup -S camunda && \
    adduser -S -G camunda camunda && \
    chown -R camunda:camunda . && \
    chmod +x entrypoint.sh

USER camunda

# HEALTHCHECK --interval=7s --timeout=3s --retries=3 CMD curl -f http://localhost:8080/health || exit 1

ENV JAVA_OPTS="-Xms4096m -Xmx4096m"
ENV SHARED_RESOURCES_NAMESPACE=""

ENTRYPOINT ["./entrypoint.sh"]
