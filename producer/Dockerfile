FROM centos:7
ADD target/producer.jar producer.jar
RUN yum install -y java-1.8.0-openjdk
ENTRYPOINT ["java", "-jar", "/producer.jar"]
#EXPOSE 8761

















#FROM centos:7
#ADD target/AdminCoreService-0.0.1-SNAPSHOT.jar /apps/admincoreservice/
#RUN yum install -y java-1.8.0-openjdk
#ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/jre
#ENV PATH=$PATH:$JAVA_HOME/bin
#EXPOSE 8085
#ENTRYPOINT ["java", "-jar", "AdminCoreService-0.0.1-SNAPSHOT.jar", "&" ]
