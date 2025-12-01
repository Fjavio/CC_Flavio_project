FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

#Folder 'data' where H2 will write the file
RUN mkdir data
# Give permissions to the app's user
RUN chown appuser:appgroup data

USER appuser

ARG JAR_FILE=target/notification-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]