#The Base Image
FROM eclipse-temurin:17-jre-alpine

#Create a working directory inside the container
WORKDIR /app

#Create a non-root user for security (Best Practice)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

#Explicitly create the log folder
RUN mkdir logs
#Assign ownership of the folder to the user 'appuser'
RUN chown appuser:appgroup logs

USER appuser

#Copy the JAR file from computer to the container
ARG JAR_FILE=target/GestoreConservatorio-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

#Expose the port on which the app runs
EXPOSE 8081

#The command to start the application
ENTRYPOINT ["java", "-jar", "app.jar"]