# 📚 DioMe Avanade Spring Boot

Projeto educacional desenvolvido como parte do programa de aceleração da **DIO em parceria com Avanade**, focado em demonstrar as melhores práticas do desenvolvimento de aplicações com **Spring Boot 4.0.1** e **Java 17**.

## 📋 Visão Geral

Este projeto implementa uma **API RESTful** completa para gerenciamento de livros (Books) com integração a banco de dados **MongoDB**, segurança com **OAuth2/JWT**, e documentação automática com **Swagger/OpenAPI**.

### Tecnologias Principais

```
┌─────────────────────────────────────────────┐
│     Linguagem & Framework                   │
│  - Java 17 (LTS)                            │
│  - Spring Boot 4.0.1                        │
│  - Spring Data MongoDB                      │
└─────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────┐
│     Segurança & Autenticação                │
│  - Spring Security                          │
│  - OAuth2 Resource Server                   │
│  - JWT (JSON Web Tokens)                    │
└─────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────┐
│     Documentação & Ferramentas              │
│  - SpringDoc OpenAPI / Swagger              │
│  - Spring Boot Actuator                     │
│  - Lombok (Reduz boilerplate)               │
│  - Maven (Build & Dependency Management)    │
└─────────────────────────────────────────────┘
```

---

## 🚀 Começando Rápido

### Pré-requisitos

- **Java 17+** instalado
- **Maven 3.6+** instalado
- **MongoDB** rodando localmente ou remoto
- **Git** para controle de versão

### Instalação

1. **Clone o repositório**
```bash
git clone https://github.com/gabrielsalesdavid/DioMe-Avanade-Spring-Boot.git
cd DioMe-Avanade-Spring-Boot
```

2. **Configure MongoDB** (application.yml)
```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/library
```

3. **Compile o projeto**
```bash
mvn clean install
```

4. **Execute a aplicação**
```bash
mvn spring-boot:run
```

5. **Acesse a aplicação**
- API: `http://localhost:8080/api`
- Swagger: `http://localhost:8080/swagger-ui.html`
- Health: `http://localhost:8080/actuator/health`

---

## 📁 Estrutura do Projeto

```
DioMe-Avanade-Spring-Boot/
├── src/
│   ├── main/
│   │   ├── java/br/com/dioavanade/springboot/
│   │   │   ├── config/                      # Configurações da app
│   │   │   │   ├── MongoConfig.java         # Configuração MongoDB
│   │   │   │   ├── SecurityConfig.java      # Segurança & OAuth2
│   │   │   │   └── OpenAPISecurityConfig.java # Swagger/OpenAPI
│   │   │   ├── controllers/
│   │   │   │   └── BookController.java      # Endpoints REST
│   │   │   ├── models/
│   │   │   │   └── Book.java                # Entidade/Documento
│   │   │   ├── repositories/
│   │   │   │   └── IBookRepository.java     # Acesso a dados
│   │   │   ├── services/
│   │   │   │   ├── IBookService.java        # Interface do serviço
│   │   │   │   └── BookServiceImplement.java# Implementação
│   │   │   └── SpringbootApplication.java   # Classe principal
│   │   └── resources/
│   │       ├── application.yml              # Configurações
│   │       ├── static/                      # Recursos estáticos
│   │       └── templates/                   # Templates
│   └── test/
│       └── java/                            # Testes unitários
├── pom.xml                                  # Dependências Maven
├── FUNDAMENTOS_JAVA.md                      # Documentação Java
├── CONCEITOS_SPRING_BOOT.md                 # Documentação Spring Boot
└── README.md                                # Este arquivo
```

---

## 🔌 Endpoints da API

### Livros (Books)

```http
# Listar todos os livros
GET /api/books

# Obter um livro por ID
GET /api/books/{id}

# Criar novo livro
POST /api/books
Content-Type: application/json
{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "price": 89.90
}

# Atualizar livro existente
PUT /api/books/{id}
Content-Type: application/json
{
  "title": "Clean Code (Atualizado)",
  "author": "Robert C. Martin",
  "price": 99.90
}

# Deletar livro
DELETE /api/books/{id}
```

### Autenticação

```http
# OAuth2 Token
POST /oauth2/authorize
Authorization: Bearer <token>
```

---

## 🔒 Segurança

### Implementado

- ✅ **Spring Security** para proteção
- ✅ **OAuth2 Resource Server** para validação de tokens
- ✅ **JWT (JSON Web Tokens)** para autenticação stateless
- ✅ **Proteção CSRF** desabilitada para APIs REST
- ✅ **Validação de permissões** por role/autoridade

### Exemplo de Uso Seguro

```bash
# 1. Obter token (seu provedor OAuth2)
TOKEN=$(curl -X POST https://seu-oauth-provider/token \
  -d "client_id=...&client_secret=...")

# 2. Usar token nas requisições
curl -H "Authorization: Bearer $TOKEN" \
  http://localhost:8080/api/books
```

---

## 📊 Monitoramento (Actuator)

A aplicação expõe endpoints de gerenciamento via Actuator:

```
GET /actuator                    # Lista todos os endpoints
GET /actuator/health             # Status da aplicação
GET /actuator/info               # Informações da app
GET /actuator/metrics            # Métricas
GET /actuator/metrics/{metric}   # Métrica específica
GET /actuator/env                # Variáveis de ambiente
GET /actuator/beans              # Beans registrados
```

### Exemplo

```bash
curl http://localhost:8080/actuator/health
```

Response:
```json
{
  "status": "UP",
  "components": {
    "mongodb": {
      "status": "UP",
      "details": {
        "version": "7.0.0"
      }
    }
  }
}
```

---

## 📚 Documentação

Duas documentações detalhadas foram criadas para facilitar o aprendizado:

### 1. [FUNDAMENTOS_JAVA.md](./FUNDAMENTOS_JAVA.md)
Guia completo sobre **Fundamentos de Java 17**, incluindo:
- Estrutura e ambiente
- Orientação a Objetos (OOP)
- Tipos de dados
- Collections Framework
- Tratamento de exceções
- Annotations
- Genéricos
- Programação Funcional (Lambdas/Streams)
- Padrões de Design

**Para quem:** Iniciantes em Java que desejam compreender os conceitos fundamentais.

### 2. [CONCEITOS_SPRING_BOOT.md](./CONCEITOS_SPRING_BOOT.md)
Documentação avançada sobre **Spring Boot 4.0.1**, cobrindo:
- Arquitetura em camadas
- Controllers e REST APIs
- Injeção de Dependência
- Padrão Service & Repository
- Modelos com Lombok
- Configurações (MongoDB, Security, OpenAPI)
- Segurança (OAuth2/JWT)
- Tratamento de erros
- Testes unitários e integração
- Anotações importantes
- Melhores práticas

**Para quem:** Desenvolvedores que desejam dominar Spring Boot e arquitetura de aplicações.

---

## 🛠️ Dependências Principais

```xml
<!-- Spring Boot Web MVC -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webmvc</artifactId>
</dependency>

<!-- Spring Data MongoDB -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>

<!-- Spring Security + OAuth2 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- Swagger/OpenAPI -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
</dependency>

<!-- Lombok (Reduz boilerplate) -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>

<!-- Spring Boot Actuator (Monitoramento) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

<!-- Spring Boot DevTools (Hot reload) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
</dependency>
```

---

## 🧪 Testes

Execute os testes com:

```bash
# Testes unitários
mvn test

# Testes com cobertura
mvn test jacoco:report

# Teste específico
mvn test -Dtest=BookServiceTest
```

Exemplo de teste unitário:

```java
@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    
    @Mock
    private IBookRepository repository;
    
    @InjectMocks
    private BookServiceImplement service;
    
    @Test
    void testFindById() {
        Book book = new Book("1", "Clean Code", "Robert Martin", 50.0);
        when(repository.findById("1")).thenReturn(Optional.of(book));
        
        Book result = service.findById("1");
        
        assertEquals("Clean Code", result.getTitle());
        verify(repository, times(1)).findById("1");
    }
}
```

---

## 📦 Build e Deploy

### Build JAR

```bash
mvn clean package
java -jar target/springboot-0.0.1-SNAPSHOT.jar
```

### Build Docker Image

```bash
mvn spring-boot:build-image
docker run -p 8080:8080 springboot:0.0.1-SNAPSHOT
```

### Variáveis de Ambiente

Configure via `application.yml` ou variáveis de ambiente:

```bash
export SPRING_DATA_MONGODB_URI=mongodb://seu-host:27017/sua-db
export SERVER_PORT=8080
mvn spring-boot:run
```

---

## 📖 Referências Oficiais

### Spring Boot
- [Spring Boot Official Documentation](https://spring.io/projects/spring-boot)
- [Spring Framework Reference](https://spring.io/projects/spring-framework)
- [Spring Boot Guides](https://spring.io/guides)

### MongoDB
- [Spring Data MongoDB](https://spring.io/projects/spring-data-mongodb)
- [MongoDB Documentation](https://docs.mongodb.com/)

### Segurança
- [Spring Security](https://spring.io/projects/spring-security)
- [OAuth2 Authorization Framework](https://oauth.net/2/)

### Ferramentas
- [Maven Documentation](https://maven.apache.org/)
- [Swagger/OpenAPI](https://swagger.io/)
- [Lombok Documentation](https://projectlombok.org/)

---

## 🎓 Sobre o Curso

Este projeto foi desenvolvido como parte do programa de **Aceleração DIO com Avanade**, focando em:

- Práticas modernas de desenvolvimento Java
- Arquitetura e design patterns
- Desenvolvimento de APIs RESTful
- Segurança em aplicações
- Testes e qualidade de código
- DevOps e containerização

---

## 👨‍💻 Autor

**Gabriel Sales David**

- GitHub: [@gabrielsalesdavid](https://github.com/gabrielsalesdavid)
- Portfolio: [Seu Portfolio]

---

## 📝 Licença

Este projeto é fornecido como material educacional.

---

## 🤝 Contribuições

Sugestões e melhorias são bem-vindas! Sinta-se à vontade para:

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

---

## ❓ FAQ

### Como conectar ao MongoDB?
Edite o arquivo `src/main/resources/application.yml` e configure a URI do MongoDB.

### Como usar OAuth2/JWT?
Configure as propriedades do OAuth2 no `application.yml` ou implemente um provedor OAuth2 local.

### Swagger não está acessível?
Acesse `http://localhost:8080/swagger-ui.html` ou `http://localhost:8080/swagger-ui/index.html`.

### Como desabilitar segurança para testes?
Você pode criar um perfil de teste (application-test.yml) com configurações diferentes.

---

**Última atualização**: 17 de janeiro de 2026  
**Versão**: 0.0.1-SNAPSHOT  
**Java Version**: 17 LTS  
**Spring Boot Version**: 4.0.1

