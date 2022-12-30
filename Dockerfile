# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY ./ ./
RUN ./gradlew stage

CMD ["java", "-XX:+PrintCompilation", "-jar", "./lib/build/libs/all-in-one-jar.jar"]
