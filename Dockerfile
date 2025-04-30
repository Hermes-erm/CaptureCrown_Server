FROM openjdk:18
WORKDIR /app
COPY target/demo-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080 8081
CMD ["java","-jar","demo-0.0.1-SNAPSHOT.jar"]