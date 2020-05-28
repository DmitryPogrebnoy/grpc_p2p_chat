FROM ubuntu:bionic

ENV DEBIAN_FRONTEND noninteractive

RUN apt-get update && apt-get install openjdk-11-jdk wget unzip git -y && apt-get clean -y && wget https://services.gradle.org/distributions/gradle-6.4.1-bin.zip && unzip gradle-6.4.1-bin.zip -d /opt && rm gradle-6.4.1-bin.zip && apt-get autoremove wget unzip -y

ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64
ENV GRADLE_HOME /opt/gradle-6.4.1
ENV PATH $PATH:/opt/gradle-6.4.1/bin

WORKDIR /home
RUN git clone https://github.com/DmitryPogrebnoy/grpc_p2p_chat.git && gradle -p ./grpc_p2p_chat build && gradle -p ./grpc_p2p_chat jar

CMD java -jar /home/grpc_p2p_chat/build/libs/grpc_p2p_chat.jar
