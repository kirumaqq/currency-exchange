FROM maven:3.8-eclipse-temurin-17-alpine as build
WORKDIR /application
COPY pom.xml ./
RUN --mount=type=cache,target=/root/.m2 \
  mvn dependency:go-offline

COPY src src/
RUN mvn package -DskipTests=true

FROM eclipse-temurin:17-jre-alpine
WORKDIR /application
COPY --from=build ./application/*.jar rest-api.jar

ENTRYPOINT ["java", "-jar", "rest-api.jar"]


