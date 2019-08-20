FROM anapsix/alpine-java:8_server-jre_unlimited

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /pig-codegen

WORKDIR /pig-codegen

EXPOSE 5003

ADD target/pig-codegen.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar pig-codegen.jar  --logging.path=/pig/logs/codegen
