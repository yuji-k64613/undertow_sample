# syntax=docker/dockerfile:1
FROM eclipse-temurin:17-jdk-jammy
RUN apt-get update && apt-get install -y \
    curl \
    git \
    vim
WORKDIR /app
COPY ./ ./
RUN ./gradlew stage
CMD ["java", "-server", "-XX:+PrintCompilation", "-jar", "./lib/build/libs/all-in-one-jar.jar"]
