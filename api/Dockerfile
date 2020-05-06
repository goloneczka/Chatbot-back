FROM openjdk:13
COPY target/api.jar /home/api.jar
CMD ["java","-Dspring.profiles.active=prod","-jar","/home/api.jar"]