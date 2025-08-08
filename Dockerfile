FROM maven:3.9-eclipse-temurin-24

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src  
EXPOSE 8080
CMD ["mvn", "spring-boot:run"]
