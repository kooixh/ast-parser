FROM gcr.io/distroless/java:8
COPY ./target/ast-parser-0.0.1-SNAPSHOT.jar /usr/app/

EXPOSE 8080

WORKDIR /usr/app
ENTRYPOINT ["java", "-jar", "ast-parser-0.0.1-SNAPSHOT.jar"]