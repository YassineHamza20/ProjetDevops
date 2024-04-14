FROM openjdk:17
EXPOSE 8082
ADD target/achat-3.0.jar achatimg-3.0.jar
ENTRYPOINT ["java","-jar","/achatimg-3.0.jar"]