FROM eclipse-temurin:21-jre

WORKDIR /

ARG JAR_FILE=target/app.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]