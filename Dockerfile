# BUILD STAGE (JDK)
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /src
COPY . .
RUN ./mvnw -q -DskipTests package

# RUN STAGE (JRE)
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /src/target/*.jar app.jar
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]