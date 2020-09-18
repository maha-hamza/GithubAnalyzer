FROM openjdk:13-alpine
MAINTAINER experto.com
VOLUME /tmp
EXPOSE 8080
ADD build/libs/immoscout24-0.0.1-SNAPSHOT.jar immoscout24.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/immoscout24.jar"]