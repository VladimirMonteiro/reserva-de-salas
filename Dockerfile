# Etapa 1 - Build do projeto
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o pom.xml e baixa dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código fonte e compila
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2 - Imagem final
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
