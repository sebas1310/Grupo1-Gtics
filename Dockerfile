FROM openjdk:17.0.2-jdk

VOLUME /tmp
EXPOSE 8083

ADD ./target/ProyectoGtics-Grupo1-0.0.1-SNAPSHOT.jar jarpr.jar

ENTRYPOINT ["java","-jar","jarpr.jar"]
