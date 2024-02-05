FROM openjdk:21-jdk-slim
ADD /target/ExilianWinterCreativeCompetition2023.Backend-1.0.jar ExilianWinterCreativeCompetition2023.Backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Xmx512M", "-jar", "/ExilianWinterCreativeCompetition2023.Backend.jar"]