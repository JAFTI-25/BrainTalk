ARG PLATFORM
FROM --platform=$PLATFORM eclipse-temurin:23-jdk

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]