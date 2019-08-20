FROM anapsix/alpine-java:8_server-jre_unlimited

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /pigx-upms

WORKDIR /pigx-upms

EXPOSE 4000

ADD target/pig-upms-biz.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar pig-upms-biz.jar  --logging.path=/pig/logs/upms
