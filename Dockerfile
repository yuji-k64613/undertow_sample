# syntax=docker/dockerfile:1
FROM eclipse-temurin:17-jdk-jammy as build
RUN apt-get update && apt-get install -y \
    curl \
    git \
    vim
WORKDIR /app
COPY ./ ./
RUN ./gradlew stage

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
RUN mkdir -p ./lib/build/libs/
COPY --from=build /app/lib/build/libs/all-in-one-jar.jar ./lib/build/libs/
CMD ["java", "-server", "-XX:+PrintCompilation", "-jar", "./lib/build/libs/all-in-one-jar.jar"]
