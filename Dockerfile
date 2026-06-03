# === Etapa 1: Compilación ===
FROM maven:3.8.8-eclipse-temurin-11 AS builder

WORKDIR /app

# Copiamos el pom.xml y descargamos dependencias (buena práctica para aprovechar la caché de Docker)
COPY pom.xml .
RUN mven dependency:go-offline -B

# Copiamos el código fuente y compilamos el archivo .war
COPY src ./src
RUN mvn clean package -DskipTests

# === Etapa 2: Servidor de producción ===
FROM tomcat:9.0-jdk11-corretto

# Railway asigna dinámicamente un puerto mediante la variable de entorno $PORT.
# Modificamos el puerto por defecto de Tomcat (8080) para que escuche el que Railway decida.
RUN sed -i 's/port="8080"/port="${port.http}"/g' /usr/local/tomcat/conf/server.xml
ENV port.http=${PORT:-8080}

# Eliminamos las aplicaciones por defecto de Tomcat para limpiar el entorno
RUN rm -rf /usr/local/tomcat/webapps/*

# Copiamos el archivo .war generado en la etapa anterior. 
# Lo renombramos a ROOT.war para que sea la aplicación principal y responda en la raíz (/)
COPY --from=builder /app/target/demo-web-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

# Iniciamos Tomcat
CMD ["catalina.sh", "run"]