FROM openjdk:8-jdk
COPY ./target/java-fsd-capstone-foodbox-0.0.2-SNAPSHOT.jar java-fsd-capstone-foodbox-0.0.2-SNAPSHOT.jar
CMD ["java" ,"-jar","java-fsd-capstone-foodbox-0.0.2-SNAPSHOT.jar"]
RUN echo "jenkins ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers
