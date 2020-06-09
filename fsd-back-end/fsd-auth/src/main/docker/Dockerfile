FROM openjdk:8-jre
MAINTAINER Lin ChaoYang <waitswallow@163.com>

EXPOSE 2105

ENV SERVICE_JAR fsd-auth
ENV WORKDIR /usr/share/fsd-service

CMD ["java", "-version"]
#ENTRYPOINT ["java", "-jar", "${WORKDIR}/${SERVICE_JAR}.jar"]
ENTRYPOINT java -jar "${WORKDIR}/${SERVICE_JAR}.jar"

# Add Maven dependencies (not shaded into the artifact; Docker-cached)
#ADD lib ${WORKDIR}/lib
# Add the service itself
ARG JAR_FILE
ADD ${JAR_FILE} ${WORKDIR}/${SERVICE_JAR}.jar