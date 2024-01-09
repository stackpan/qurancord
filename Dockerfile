FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY . .

RUN ./mvnw clean compile assembly:single

COPY bot/target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
