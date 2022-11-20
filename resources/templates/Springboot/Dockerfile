FROM openjdk:11

LABEL developer.name='yeluo' \
      developer.email='yeluo0528@gmail.com' \
      version='1.0.0' \
      description='Springboot Project'

EXPOSE 8080

VOLUME /tmp

ADD *.jar app.jar

ENV TZ=Asia/Shanghai \
    RANDOM="-Djava.security.egd=file:/dev/./urandom" \
    JAVA_OPTS='' \
    ACTIVE="dev"

RUN bash -c 'touch /app.jar'
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENTRYPOINT [ "sh", "-c", "java ${JAVA_OPTS} ${RANDOM} -jar -Dspring.profiles.active=${ACTIVE} /app.jar" ]