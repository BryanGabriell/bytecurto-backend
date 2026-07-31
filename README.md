# 🔗 ByteCurto

ByteCurto é uma API REST desenvolvida com **Java 17** e **Spring Boot 3** para encurtamento de URLs.

O projeto permite que usuários autenticados criem links curtos, armazenem suas URLs originais e realizem o redirecionamento automaticamente através do código gerado.

---

# 🚀 Tecnologias

- Java 17
- Spring Boot 3
- Spring Security (JWT)
- Spring Data JPA
- PostgreSQL
- Flyway
- Docker
- Docker Compose
- MapStruct
- Bean Validation
- Swagger / OpenAPI
- JUnit 5
- Mockito
- Maven

---

# 📌 Funcionalidades

✅ Cadastro de usuários

✅ Login com autenticação JWT

✅ Criação de URLs encurtadas

✅ Redirecionamento para a URL original

✅ Tratamento global de exceções

✅ Validação de dados

✅ Documentação com Swagger

✅ Logs da aplicação

✅ Ambientes separados (Development e Production)

---

# 🏗 Arquitetura

O projeto segue uma arquitetura em camadas:

```
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

Também utiliza:

- DTOs
- Mappers (MapStruct)
- Exceptions customizadas
- Global Exception Handler

---

# 🔐 Autenticação

A autenticação é realizada utilizando **JWT (JSON Web Token)**.

Fluxo:

1. Cadastro do usuário
2. Login
3. Recebimento do Token JWT
4. Envio do Token nas requisições autenticadas

---

# 📂 Estrutura do Projeto

```
src
 ├── controller
 ├── service
 ├── repository
 ├── entity
 ├── mapper
 ├── dto
 ├── config
 ├── exception
 ├── infrastructure
 └── util
```

---

# 📖 Documentação

Após iniciar a aplicação:

```
http://localhost:8080/swagger-ui.html
```

---

# 🐳 Docker

Para iniciar o banco de dados:

```bash
docker compose up -d
```

---

# ▶️ Executando Localmente

Clone o projeto

```bash
git clone https://github.com/BryanGabriell/bytecurto.git
```

Entre na pasta

```bash
cd bytecurto
```

Suba o banco

```bash
docker compose up -d
```

Execute

```bash
./mvnw spring-boot:run
```

Ou gere o JAR

```bash
./mvnw package
```

Execute

```bash
java -jar target/bytecurto.jar
```

---

# 📡 Principais Endpoints

## Cadastro

```
POST /v1/users
```

---

## Login

```
POST /login
```

---

## Encurtar URL

```
POST /api/links/encurtar
```

(Requer autenticação)

---

## Redirecionar

```
GET /redirecionar/{shortCode}
```

---

# 🧪 Testes

O projeto possui testes unitários utilizando:

- JUnit 5
- Mockito

---

# 📈 Melhorias Futuras

- Dashboard de métricas
- Estatísticas de acessos
- Expiração de links
- QR Code para URLs
- Rate Limiting
- Cache com Redis

---

# 👨‍💻 Autor

Bryan Gabriel

GitHub:
https://github.com/BryanGabriell

LinkedIn:
https://www.linkedin.com/in/bryan-gabriell