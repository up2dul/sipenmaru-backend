FROM openjdk:17-jdk-alpine

WORKDIR /app
COPY target/sipenmaru-*.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS="-Xms256m -Xmx512m"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
