ARG java_version=17
FROM openjdk:${java_version}
ADD SpringBootDemo1-0.0.1-SNAPSHOT.jar demo.jar
ARG expose_port=8080
EXPOSE ${expose_port}
ENTRYPOINT ["java" ,"-jar", "demo.jar"]
ARG java_version
RUN echo "Hello all we exposed the port no ${expose_port} and java version used is ${java_version}"