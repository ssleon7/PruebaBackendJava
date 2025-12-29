FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN chmod +x mvnw && ./mvnw clean package -DskipTests

EXPOSE 8082

ENTRYPOINT ["java","-jar","target/InventoryService-0.0.1-SNAPSHOT.jar"]
