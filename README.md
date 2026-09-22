# Spring Boot Docker — API REST de Prompts

API REST para gerenciamento de prompts, desenvolvida com **Spring Boot 4** e **Java 24**, com persistência em **H2** e execução em **container Docker**.

---

## 📋 Sumário

- [Visão Geral](#-visão-geral)
- [Tecnologias](#-tecnologias)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Pré-requisitos](#-pré-requisitos)
- [Execução Local (sem Docker)](#-execução-local-sem-docker)
- [Execução com Docker](#-execução-com-docker)
- [Endpoints da API](#-endpoints-da-api)
- [Acessos Úteis](#-acessos-úteis)
- [Variáveis de Ambiente](#-variáveis-de-ambiente)
- [Comandos Docker Úteis](#-comandos-docker-úteis)
- [Notas Técnicas](#-notas-técnicas)
- [Licença](#-Licença)

---

## 🎯 Visão Geral

Este projeto implementa um CRUD completo para a entidade `Prompt`, com os seguintes campos:

| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | Long (serial) | Identificador único gerado automaticamente |
| `nomePrompt` | String | Nome descritivo do prompt |
| `categoria` | String | Categoria à qual o prompt pertence |
| `objetivo` | String (TEXT) | Objetivo do prompt |
| `modelo` | String | Modelo de IA utilizado |
| `status` | String | Status atual do prompt |
| `atualizacao` | LocalDateTime | Data/hora da última atualização |

---

## 🛠 Tecnologias

| Componente | Versão |
|------------|--------|
| Java | 24 |
| Spring Boot | 4.1.1 |
| Spring Data JPA | (via Spring Boot) |
| Spring Data REST | (via Spring Boot) |
| H2 Database | (via Spring Boot) |
| Springdoc OpenAPI (Swagger) | 3.0.3+ |
| Lombok | (via Spring Boot) |
| Maven | 3.9+ |
| Docker | 24+ |
| Docker Compose | v2+ |

---

## 📁 Estrutura do Projeto

```text
spring-boot-docker/
├── src/
│   ├── main/
│   │   ├── java/br/com/sasati/spring_boot_docker/
│   │   │   ├── SpringBootDockerApplication.java
│   │   │   ├── controller/
│   │   │   │   └── PromptController.java
│   │   │   ├── entity/
│   │   │   │   └── Prompt.java
│   │   │   ├── repository/
│   │   │   │   └── PromptRepository.java
│   │   │   └── service/
│   │   │       └── PromptService.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── target/
│   └── spring-boot-docker-0.0.1-SNAPSHOT.jar
├── Dockerfile
├── docker-compose.yml
├── .dockerignore
├── pom.xml
└── README.md
```
---

## ✅ Pré-requisitos

### Para execução local
- **JDK 24** instalado (`java -version` deve retornar 24.x)
- **Maven 3.9+** instalado (`mvn -version`)
- **IntelliJ IDEA** (opcional, mas recomendado)

### Para execução com Docker
- **Docker Desktop** instalado e em execução
- Verifique com: `docker --version` e `docker compose version`

---

## 🖥 Execução Local (sem Docker)

### 1. Compilar o projeto

```shell
  mvn clean package -DskipTests
```
O JAR será gerado em: `target/spring-boot-docker-0.0.1-SNAPSHOT.jar`

### 2. Executar a aplicação
```shell
   java -jar target/spring-boot-docker-0.0.1-SNAPSHOT.jar
```
   Ou, via Maven:

```shell
  mvn spring-boot:run
```
A aplicação estará disponível em `http://localhost:8080`.

---

## 🐳 Execução com Docker

### 1. Compilar o JAR no host (obrigatório)
   O Dockerfile copia o JAR já compilado, então compile antes:
```shell
  mvn clean package -DskipTests
```
#### 2. Build da imagem
```shell
  docker compose build
```
Para ver cada passo do build (útil para estudo):
```shell
  docker compose build --no-cache --progress=plain
```
### 3. Subir o container
O Dockerfile copia o JAR já compilado, então compile antes:
```shell
  docker compose up -d
```
A aplicação estará disponível em `http://localhost:8080`.
### 4. Verificar se o container está rodando
```shell
  docker compose ps
  docker compose logs -f api
```
### 5. Parar o container
```shell
  docker compose down
```
Para remover também a imagem gerada:
```shell
  docker compose down --rmi local
```
---

## 🌐 Endpoints da API

### API customizada (/api/prompts)

| Método | Endpoint | Descrição |
|-------|------|-----------|
|`POST`	|`/api/prompts`	|Cria um novo prompt|
|`GET`	|`/api/prompts`	|Lista todos os prompts|
|`GET`	|`/api/prompts/{id}`	|Busca um prompt pelo ID|
|`PUT`	|`/api/prompts/{id}`	|Atualiza um prompt existente|
|`DELETE`	|`/api/prompts/{id}`	|Remove um prompt pelo ID|

### Spring Data REST (`/data-rest/prompts-auto`)
Endpoints gerados automaticamente pelo Spring Data REST:

| Método | Endpoint | Descrição |
|-------|------|-----------|
|`GET`	|`/data-rest/prompts-auto`	|Lista paginada de prompts|
|`GET`	|`/data-rest/prompts-auto/{id}`	|Busca por ID|
|`POST`	|`/data-rest/prompts-auto`	|Cria prompt|
|`PUT`	|`/data-rest/prompts-auto/{id}`	|Atualiza prompt|
|`PATCH`	|`/data-rest/prompts-auto/{id}`	|Atualização parcial|
|`DELETE`	|`/data-rest/prompts-auto/{id}`	|Remove prompt|
|`GET`	|`/data-rest/prompts-auto/search/findByCategoria?categoria=X`	|Busca por categoria|
|`GET`	|`/data-rest/prompts-auto/search/porStatus?status=X`	|Busca por status|

### Exemplo de payload (POST/PUT)
```json
{
    "nomePrompt": "Resumo de Artigo Científico",
    "categoria": "Acadêmico",
    "objetivo": "Gerar resumo conciso de artigos científicos com base no abstract",
    "modelo": "GPT-4",
    "status": "ATIVO"
}
```

### ⚠️ Atenção: Não envie o campo `id` ao criar um novo registro. O Hibernate 6.6+ (usado pelo Spring Boot 4) lança `StaleObjectStateException` se o ID não for nulo e não existir no banco.

# 🔗 Acessos Úteis
### Após a aplicação estar em execução (local ou container):
|Serviço	|URL|
|---|---|
|Swagger UI	|http://localhost:8080/swagger-ui/index.html |
|OpenAPI JSON	|http://localhost:8080/v3/api-docs |
|H2 Console	|http://localhost:8080/h2-console |
|API de Prompts	|http://localhost:8080/api/prompts |

# H2 Console — credenciais
|Campo|	Valor|
|---|---|
|JDBC URL	|`jdbc:h2:mem:promptdb`|
|User Name	|`sa`|
|Password	|(em branco)|

# ⚙️ Variáveis de Ambiente
As variáveis abaixo podem ser passadas ao container (via `docker run -e` ou pelo bloco `environment` do `docker-compose.yml`):

|Variável|	Descrição|	Valor padrão|
|---|---|---|
|`SPRING_PROFILES_ACTIVE`	|Perfil do Spring ativo	|`default`|
|`SPRING_DATASOURCE_URL`	|URL do datasource	|`jdbc:h2:mem:promptdb`|
|`LOGGING_LEVEL_BR_COM_SASATI`	|Nível de log do pacote da aplicação	|`INFO`|


Exemplo de uso:
```shell 
  docker run -d \
    -p 8080:8080 \
    -e SPRING_PROFILES_ACTIVE=dev \
    -e LOGGING_LEVEL_BR_COM_SASATI=DEBUG \
    --name spring-boot-docker-api \
    spring-boot-docker:1.0
```

# 🧰 Comandos Docker Úteis
## Build manual (sem Compose)
```shell
  docker build -t spring-boot-docker:1.0 .
```

## Executar container
```shell
    docker run -d \
    -p 8080:8080 \
    --name spring-boot-docker-api \
    spring-boot-docker:1.0
```

## Inspecionar
```shell

# Lista containers rodando
docker ps

# Logs em tempo real
docker logs -f spring-boot-docker-api

# Abrir shell dentro do container
docker exec -it spring-boot-docker-api sh

# Ver detalhes (IP, mounts, variáveis)
docker inspect spring-boot-docker-api
```

## Limpar
```shell
# Parar e remover container
docker stop spring-boot-docker-api && docker rm spring-boot-docker-api

# Remover imagem
docker rmi spring-boot-docker:1.0

# Limpeza geral (CUIDADO: remove tudo que não está em uso)
docker system prune -a
```

# 📝 Notas Técnicas

## Hibernate 6.6+ e o ID na criação
A partir do Hibernate 6.6 (usado pelo Spring Boot 3.4+ e, portanto, pelo Spring Boot 4), o comportamento do `merge` mudou: se o ID não for nulo e não existir no banco, é lançada `StaleObjectStateException` em vez de fazer INSERT. Por isso, o método `criar` no `PromptService` força `prompt.setId(null)` antes de salvar.

## Codificação de arquivos `.properties`
Para suportar acentos em `application.properties`, o `pom.xml` configura o `maven-resources-plugin` com `<propertiesEncoding>UTF-8</propertiesEncoding>`. Sem isso, o build falha com `MalformedInputException` no Windows.

## Spring Data REST e endpoints automáticos
O projeto inclui `spring-boot-starter-data-rest`, que expõe os repositórios JPA automaticamente. A configuração em `application.properties` define:

```properties
spring.data.rest.base-path=/data-rest
spring.data.rest.detection-strategy=annotated
```

Isso move os endpoints automáticos para `/data-rest/**` e só expõe repositórios anotados com `@RepositoryRestResource`.

## Swagger no Spring Boot 4
É necessário usar `springdoc-openapi-starter-webmvc-ui` na versão 3.0.3 ou superior. As versões 2.x são incompatíveis com o Spring Boot 4.

# 📄 Licença
Este projeto é de uso livre para fins de estudo.

### 💡 Sobre o conteúdo

- **Sumário navegável:** os links com `#` funcionam em visualizadores Markdown (GitHub, GitLab, VS Code, IntelliJ Preview).
- **Seções didáticas:** incluí as "Notas Técnicas" com os aprendizados das últimas horas (Hibernate 6.6+, encoding, Swagger 3.x). Isso vale ouro quando você voltar no projeto daqui a alguns meses.
- **Comandos Docker manuais e via Compose:** deixei os dois caminhos para reforçar o aprendizado.
- **Exemplo de payload:** inclui o aviso sobre não enviar `id` no POST — evita o erro que você enfrentou.

Se quiser, posso também gerar um `CONTRIBUTING.md`, um `CHANGELOG.md`, ou um `Makefile` com atalhos (`make build`, `make up`, `make down`). Mas por hoje, o README já está redondo. Bom descanso! 🚀
