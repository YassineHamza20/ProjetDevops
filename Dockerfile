FROM openjdk:8-jdk-alpine
EXPOSE 8089
ADD target/achat-1.0.jar achatimg-1.0.jar
ENTRYPOINT ["java","-jar","/achatimg-1.0.jar"]