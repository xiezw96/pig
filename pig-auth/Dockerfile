FROM anapsix/alpine-java:8_server-jre_unlimited

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /pig-auth

WORKDIR /pig-auth

EXPOSE 3000

ADD target/pig-auth.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar pig-auth.jar  --logging.path=/pig/logs/auth
