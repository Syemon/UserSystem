FROM eclipse-temurin:17-jre-jammy

RUN addgroup usergroup; adduser  --ingroup usergroup --disabled-password appuser
USER appuser

WORKDIR /opt

COPY target/UserSystem.jar application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]