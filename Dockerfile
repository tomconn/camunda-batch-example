FROM amazoncorretto:17-alpine

ARG version

WORKDIR /opt/camunda

ADD target/offers-batch-settlement-${version}.jar offers-batch-settlement.jar
COPY entrypoint.sh .

# install bash (for the entrypoint script) and curl (for healthchecks).
# add a non-root camunda user and change ownership of the camunda jar.
RUN apk --no-cache add \
    bash \
    curl && \
    addgroup -S camunda && \
    adduser -S -G camunda camunda && \
    chown -R camunda:camunda . && \
    chmod +x entrypoint.sh

USER camunda

# HEALTHCHECK --interval=7s --timeout=3s --retries=3 CMD curl -f http://localhost:8080/health || exit 1

ENV JAVA_OPTS="-Xms4096m -Xmx4096m"
ENV SHARED_RESOURCES_NAMESPACE=""

ENTRYPOINT ["./entrypoint.sh"]
