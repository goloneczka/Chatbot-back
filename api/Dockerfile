FROM openjdk:13

ARG JAR_FILE
COPY app/api.jar /home/api.jar
CMD ["java"," -Dspring.profiles.active=prod", "-jar", "/home/api.jar"]