
FROM openjdk:11
ARG PROJECT_VERSION=1.0.0
RUN mkdir -p /home/app
WORKDIR /home/app
ENV TZ=Asia/Seoul
## DOCKER BUILD
#ARG JAR_FILE
#COPY ${JAR_FILE} app.jar
#
#EXPOSE 8800
#ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "app.jar"]

# BOOTJAR BUILD
ARG JAR_FILE=build/libs/spring_socket_restapi-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} spring_socket_restapi-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-Duser.timezone=${TZ}", "-jar", "spring_socket_restapi-0.0.1-SNAPSHOT.jar"]


