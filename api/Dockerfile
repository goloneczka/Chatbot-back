FROM openjdk:13
COPY target/api.jar /home/api.jar
CMD ["java","-jar","-Dspring.profiles.active=prod","/home/api.jar"]