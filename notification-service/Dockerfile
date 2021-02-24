FROM openjdk:8-jdk-alpine
LABEL maintainer="Gagan Dhand"
EXPOSE 8040
ADD ./target/notification-service-0.0.1-SNAPSHOT.jar /
ENTRYPOINT ["java","-jar","notification-service-0.0.1-SNAPSHOT.jar"]
