FROM openjdk:18.0.2.1-jdk-slim-bullseye
ADD chromedriver /app/chromedriver
RUN apt-get update \
    && apt-get install -y \
       unzip \
       wget \
       libnss3 \
       libgconf-2-4 \
       libxss1 \
       libgtk2.0-0 libsm6 libxrender1 libxtst6 libxi6 \
    && rm -rf /var/lib/apt/lists/*

ADD build/libs/RemiBot-1.0-all.jar /app/remibot.jar
WORKDIR /app
CMD ["java", "-jar", "remibot.jar"]
