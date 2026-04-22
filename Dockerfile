FROM eclipse-temurin:17-jdk-jammy
ADD build/libs/RemiBot-1.0-all.jar /app/remibot.jar
WORKDIR /app
CMD ["java", "-jar", "remibot.jar"]
