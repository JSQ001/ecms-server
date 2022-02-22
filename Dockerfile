#FROM java:8
#
#COPY MHWZ-module-1.0-SNAPSHOT.jar /MHWZ-module.jar
#COPY xxl-sso-server-1.0-SNAPSHOT.jar /xxl-sso-server.jar
#COPY YWSJ-BG.jar /YWSJ-BG.jar
#
##COPY start_cloud.sh /start_cloud.sh
##RUN chmod +x /start_cloud.sh
##ENTRYPOINT ["sh","-c","./start_cloud.sh"]
#
#
#COPY start_cloud.sh /usr/bin/start_cloud.sh
#RUN chmod +x /usr/bin/start_cloud.sh
#CMD nohup sh -c "start_cloud.sh && java -jar /YWSJ-BG.jar"

FROM openjdk:11.0.4
EXPOSE 8989
ADD ./target/demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

