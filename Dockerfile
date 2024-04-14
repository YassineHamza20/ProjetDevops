FROM openjdk:17-jdk-alpine
EXPOSE 8082
ADD target/achat-4.0.jar achat-4.0.jar
ENTRYPOINT ["java","-jar","/achat-4.0.jar"]