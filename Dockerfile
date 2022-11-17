FROM openjdk:17-alpine
MAINTAINER baeldung.com
COPY target/spring-reactive-0.0.1-SNAPSHOT.jar spring-reactive-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/spring-reactive-0.0.1-SNAPSHOT.jar"]