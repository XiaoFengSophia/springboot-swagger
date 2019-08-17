FROM java:8 
VOLUME /tmp 
ADD springboot-swagger-0.0.1-SNAPSHOT.jar /swagger.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/swagger.jar"]