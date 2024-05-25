#FROM openjdk:22-jdk-slim
#WORKDIR /app/pde-client
#COPY pom.xml .
#COPY src /app/pde-client/src
#RUN mvn -B clean package
#EXPOSE 8090
#
#ENTRYPOINT ["java"]
#CMD ["-jar", "target/pde-client-0.0.1.jar"]


FROM maven:3.8.1 AS build
WORKDIR /app
COPY pom.xml /app/
COPY . /app/
RUN mvn package

FROM openjdk:22-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/pde-client.jar /app
EXPOSE 8090
CMD ["java", "-jar", "target/pde-client.jar"]