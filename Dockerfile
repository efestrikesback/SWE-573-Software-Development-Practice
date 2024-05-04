FROM openjdk:17.0.2-slim-bullseye
WORKDIR /app
COPY . .
RUN ls -la
RUN chmod +x mvnw
#RUN sed -i 's/\r$//' mvnw
#RUN /bin/sh mvnw dependency:resolve
RUN ./mvnw clean install -DskipTests
CMD java -jar ./target/DEVCOM-0.0.1-SNAPSHOT.jar