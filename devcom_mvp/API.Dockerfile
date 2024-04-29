##FROM openjdk:17.0.2-slim-bullseye
##WORKDIR /app
##COPY . .
##RUN ./mvnw clean install -DskipTests
##CMD java -jar ./target/Devcom-0.0.1-SNAPSHOT.jar
#
#
## Using a slim version of OpenJDK 17
#FROM openjdk:17.0.2-slim-bullseye
#
## Set the working directory inside the container
#WORKDIR /app
#
## Copy only the necessary files for building the Maven project
#COPY mvnw pom.xml ./
#COPY .mvn/ .mvn/
#COPY src/ src/
#
## Grant execution rights on the Maven wrapper and build the project
#RUN chmod +x ./mvnw && ./mvnw clean install -DskipTests
#
## Command to run the application
#CMD ["java", "-jar", "./target/DEVCOM-0.0.1-SNAPSHOT.jar"]
#


FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/DEVCOM-0.0.1-SNAPSHOT.jar devcom.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "devcom.jar"]
