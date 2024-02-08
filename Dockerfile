FROM maven:3.8-eclipse-temurin-17-alpine as build
WORKDIR /build

COPY pom.xml ./
RUN --mount=type=cache,target=/root/.m2 \
  mvn dependency:go-offline

COPY src src/
RUN --mount=type=cache,target=/root/.m2 \
    mvn package -DskipTests=true

FROM eclipse-temurin:17-jre-alpine
WORKDIR /application

COPY --from=build /build/target/*.jar application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]


