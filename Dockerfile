FROM openjdk:17-jdk

WORKDIR /app

COPY target/afterglow-0.0.1-SNAPSHOT.jar /app/afterglow.jar

EXPOSE 8080

CMD ["java", "-jar", "afterglow.jar"]