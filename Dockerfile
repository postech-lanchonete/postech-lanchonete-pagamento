FROM openjdk:17-jdk-slim AS builder

WORKDIR /app

COPY . .

RUN ./gradlew build

FROM openjdk:17-oracle

WORKDIR /app

COPY --from=builder /app/build/libs/postech-lanchonete-pagamento-1.0.0-POC.jar .

EXPOSE 8080

CMD ["java", "-jar", "postech-lanchonete-pagamento-1.0.0-POC.jar"]