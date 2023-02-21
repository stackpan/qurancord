FROM eclipse-temurin:17-jre-alpine

COPY bot/target/qurancord-bot-2.2-ALPHA-jar-with-dependencies.jar /app/qurancord-server.jar

CMD ["java", "-jar", "/app/qurancord-server.jar"]