FROM openjdk:18.0.2.1-jdk-slim-bullseye
ARG DEBIAN_FRONTEND=noninteractive
ADD chromedriver /app/chromedriver
RUN apt-get update \
    && apt-get install -y \
       unzip \
       wget \
       libnss3 \
       libgconf-2-4 \
       libxss1 \
       libgtk2.0-0 libsm6 libxrender1 libxtst6 libxi6 wget tzdata gnupg2

RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add -
RUN sh -c 'echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list'
RUN apt-get update
RUN apt --fix-broken install
RUN apt-get install google-chrome-stable -y \
 && rm -rf /var/lib/apt/lists/*

ADD build/libs/RemiBot-1.0-all.jar /app/remibot.jar
WORKDIR /app
CMD ["java", "-jar", "remibot.jar"]
