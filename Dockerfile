FROM openjdk:11
ARG SERVICE_NAME=poc-spring
ARG JAR_FILE=build/libs/${SERVICE_NAME}0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/app.jar"]