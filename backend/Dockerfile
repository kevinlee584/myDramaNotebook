FROM eclipse-temurin:11.0.19_7-jre-alpine
ARG JAR_FILE="target/demo-0.0.1-SNAPSHOT.jar"
COPY ${JAR_FILE} service.jar
ENTRYPOINT ["java","-jar","/service.jar"]