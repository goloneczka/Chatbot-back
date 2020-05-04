FROM openjdk:13

ARG JAR_FILE
COPY app/integration.jar /home/integration.jar
CMD ["java", "-jar"," -Dspring.profiles.active=prod", "/home/integration.jar"]
