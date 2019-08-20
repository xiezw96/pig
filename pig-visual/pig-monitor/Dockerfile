FROM anapsix/alpine-java:8_server-jre_unlimited

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /pig-monitor

WORKDIR /pig-monitor

EXPOSE 5001

ADD target/pig-monitor.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar pig-monitor.jar  --logging.path=/pig/logs/monitor
