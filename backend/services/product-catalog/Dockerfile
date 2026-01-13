FROM maven:3.8-openjdk-17-slim AS build
WORKDIR /app

# Copy shared domain first
COPY backend/shared/domain/pom.xml /app/shared/domain/
COPY backend/shared/domain/src /app/shared/domain/src
RUN cd /app/shared/domain && mvn clean install

# Copy and build service
COPY backend/services/product-catalog/pom.xml .
RUN mvn dependency:go-offline

COPY backend/services/product-catalog/src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]
