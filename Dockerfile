FROM eclipse-temurin:17-jre-alpine

COPY target/qurancord-2.0.1-SNAPSHOT-jar-with-dependencies.jar /app/qurancord-server.jar

CMD ["java", "-jar", "/app/qurancord-server.jar"]