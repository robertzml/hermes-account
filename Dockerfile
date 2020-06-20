FROM openjdk:11.0.6

VOLUME /tmp

COPY target/hermes-account-0.0.2-SNAPSHOT.jar hermes-account.jar

# SET TIMEZONE
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone

EXPOSE 8080

ENTRYPOINT ["java","-jar","/hermes-account.jar"]