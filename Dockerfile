FROM alpine:3.20.0

RUN apk --no-cache add openjdk21-jre && \
    addgroup app && adduser -G app -s /bin/sh -D app

COPY --chown=app:app build/libs/echo-0.0.1-SNAPSHOT.jar /home/app/app.jar

EXPOSE 8080

USER app

WORKDIR /home/app

ENV JAVA_OPTS="-Xms256m -Xmx256m"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
