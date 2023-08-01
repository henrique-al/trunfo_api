# Definição de build para a imagem do Spring boot
FROM amazoncorretto:17 as build

# Instalar a ferramenta dos2unix
RUN yum install -y dos2unix

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Converter o arquivo mvnw para usar apenas o caractere de quebra de linha LF
RUN dos2unix mvnw

RUN chmod +x ./mvnw
# Faça o download das dependências do pom.xml
RUN ./mvnw dependency:go-offline -B

COPY src src

RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Definição de produção para a imagem do Spring boot
FROM amazoncorretto:17 as production
ARG DEPENDENCY=/app/target/dependency

# Copiar as dependências para o build artifact
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Rodar a aplicação Spring boot
ENTRYPOINT ["java", "-cp", "app:app/lib/*","br.senai.sc.trunfo_api.TrunfoApiApplication"]
