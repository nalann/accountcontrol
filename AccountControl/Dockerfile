FROM maven:3.8.5-openjdk-17
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean install
CMD mvn -f /home/app/pom.xml spring-boot:run
EXPOSE 8080
