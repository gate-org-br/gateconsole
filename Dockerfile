# Use a imagem base do Java 17
FROM alpine:latest

# Defina variáveis de ambiente para o OpenJDK
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk
ENV PATH="$JAVA_HOME/bin:$PATH"

# Instale o OpenJDK 17 (JRE) e outras dependências
RUN apk add --no-cache openjdk17-jre

# Define a pasta de trabalho dentro do contêiner
WORKDIR /usr/src/app

# Copie o JAR do seu aplicativo Quarkus para o contêiner
COPY target/*-runner.jar gate.jar

# Exponha a porta em que o aplicativo Quarkus está sendo executado
EXPOSE 8080

# Comando para iniciar o aplicativo Quarkus quando o contêiner for iniciado
CMD ["java", "-jar", "gate.jar"]

