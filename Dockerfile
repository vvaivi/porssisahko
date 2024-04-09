FROM ubuntu:latest as build
RUN apt-get update && apt-get install -y openjdk-17-jdk

WORKDIR /usr/src/app
COPY . .

ENV CLASSPATH="packages/javax.mail.jar:packages/javax.activation_1.1.0.v201211130549.jar:.:packages/jsoup-1.17.2.jar"
RUN javac -encoding utf8 *.java

CMD ["java", "JobScheduler"]
