FROM eclipse-temurin:17-jre-alpine

COPY bot/target/qurancord-bot-2.1-SNAPSHOT-jar-with-dependencies.jar /app/qurancord-server.jar

CMD ["java", "-jar", "/app/qurancord-server.jar"]