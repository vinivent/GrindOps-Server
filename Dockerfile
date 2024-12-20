# Etapa de construção com Maven
FROM maven:3.9.9-eclipse-temurin-21 as BUILD
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

# Etapa de execução com Java
FROM eclipse-temurin:21-alpine
WORKDIR /app
COPY --from=BUILD /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
