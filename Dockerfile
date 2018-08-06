# fetch basic image
FROM maven:3.3.9-jdk-8

# application placed into /opt/kafkaui
RUN mkdir -p /opt/kafkaui
WORKDIR /opt/kafkaui

# selectively add the POM file and
# install dependencies
COPY pom.xml /opt/kafkaui/
RUN mvn install

# rest of the project
COPY src /opt/kafkaui/src
RUN mvn package

# local application port
EXPOSE 8090

# execute it
CMD ["mvn", "exec:java"]
