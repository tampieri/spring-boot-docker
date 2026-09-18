# ============================================================
# Dockerfile — Spring Boot (build manual)
# ============================================================
# Estratégia: você compila o JAR no host com `mvn package`
# e este Dockerfile apenas empacota o JAR já pronto.
#
# Vantagens didáticas:
#   - Build do Docker fica rápido (só copia o JAR)
#   - Você vê claramente a separação entre "compilar" e "executar"
#   - Menos variáveis para aprender de uma vez
#
# Para compilar antes de buildar a imagem:
#   mvn clean package -DskipTests
# ============================================================


# ---------- IMAGEM BASE ----------
# eclipse-temurin é uma distribuição OpenJDK mantida pela Adoptium.
# A variante "jre" contém apenas o runtime (não o compilador).
# A variante "alpine" é uma distro Linux minúscula (~5 MB), o que
# deixa a imagem final bem enxuta (~180 MB em vez de ~450 MB).
#
# Só precisamos do JRE porque o JAR já vem compilado.
FROM eclipse-temurin:24-jre-alpine


# ---------- METADADOS (opcional, boa prática) ----------
LABEL maintainer="higor" \
      description="API REST de Prompts - Spring Boot 4 + H2"


# ---------- DIRETÓRIO DE TRABALHO ----------
# Cria /app no container e define como diretório padrão
# para os próximos comandos (COPY, RUN, ENTRYPOINT, CMD).
WORKDIR /app


# ---------- FUSO HORÁRIO ----------
# Alpine vem com UTC por padrão. Instalamos o pacote tzdata
# (dados de fuso horário) e definimos America/Sao_Paulo.
# Sem isso, logs e timestamps ficariam 3h adiantados.
RUN apk add --no-cache tzdata \
    && cp /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime \
    && echo "America/Sao_Paulo" > /etc/timezone


# ---------- CÓPIA DO JAR ----------
# Pega o JAR do diretório target/ do host e coloca em /app/app.jar
# dentro do container. O wildcard *.jar evita ter que escrever
# a versão exata do arquivo.
COPY target/spring-boot-docker-0.0.1-SNAPSHOT.jar app.jar


# ---------- PORTA ----------
# EXPOSE é apenas DOCUMENTAÇÃO. Não abre a porta de verdade.
# Quem realmente expõe é o `-p 8080:8080` no docker run.
# Serve para deixar claro para quem ler o Dockerfile que o
# container escuta na 8080.
EXPOSE 8080


# ---------- USUÁRIO NÃO-ROOT (boa prática) ----------
# O Alpine já tem o usuário "nobody" (UID 65534).
# Rodar como não-root reduz o risco de escape de container.
# Aqui é opcional, mas aprenda desde já.
USER nobody


# ---------- COMANDO DE START ----------
# ENTRYPOINT é o comando fixo que roda quando o container inicia.
# Usamos a forma "exec" (array JSON) porque ela NÃO passa pelo shell:
# o processo java vira PID 1 do container, o que faz ele receber
# sinais (SIGTERM) corretamente no docker stop.
#
# Flags da JVM:
#   -XX:+UseContainerSupport     → respeita os limites de memória do container
#   -XX:MaxRAMPercentage=75.0    → usa até 75% da RAM do container como heap
#   -Duser.timezone=America/Sao_Paulo → reforça o fuso (defesa extra)
ENTRYPOINT ["java", \
            "-XX:+UseContainerSupport", \
            "-XX:MaxRAMPercentage=75.0", \
            "-Duser.timezone=America/Sao_Paulo", \
            "-jar", "app.jar"]