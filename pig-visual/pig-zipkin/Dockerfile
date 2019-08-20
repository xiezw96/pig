FROM anapsix/alpine-java:8_server-jre_unlimited

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /pig-zipkin

WORKDIR /pig-zipkin

EXPOSE 5002

ADD target/pig-zipkin.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar pig-zipkin.jar  --logging.path=/pig/logs/monitor
