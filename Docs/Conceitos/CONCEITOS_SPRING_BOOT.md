# 🚀 Conceitos Spring Boot

## Introdução

Este documento apresenta os principais conceitos e padrões do **Spring Boot 4.0.1** utilizados neste projeto. Spring Boot é um framework que simplifica o desenvolvimento de aplicações Java, fornecendo configuração automática e dependências pré-configuradas.

---

## 1. O que é Spring Boot?

### Definição
Spring Boot é uma abstração sobre o framework Spring que facilita a criação de aplicações standalone, production-ready com mínima configuração.

### Benefícios
- ✅ Configuração automática (auto-configuration)
- ✅ Embedded servers (Tomcat, Jetty, Undertow)
- ✅ Starter dependencies
- ✅ Monitoring e management (Actuator)
- ✅ Rápida inicialização
- ✅ Externalização de configuração

---

## 2. Arquitetura do Projeto

### 2.1 Camadas da Aplicação

```
┌─────────────────────────────────────────────┐
│         Presentation Layer                  │
│      (Controllers/REST Endpoints)           │
└──────────────────┬──────────────────────────┘
                   │
┌──────────────────▼──────────────────────────┐
│      Business Logic Layer                   │
│      (Services/Service Implementation)      │
└──────────────────┬──────────────────────────┘
                   │
┌──────────────────▼──────────────────────────┐
│      Data Access Layer                      │
│      (Repositories/Data Access)             │
└──────────────────┬──────────────────────────┘
                   │
┌──────────────────▼──────────────────────────┐
│      Database Layer                         │
│      (MongoDB/Banco de Dados)               │
└─────────────────────────────────────────────┘
```

### 2.2 Estrutura de Pacotes

```
br.com.dioavanade.springboot
├── config/              # Configuração da aplicação
│   ├── MongoConfig.java
│   ├── SecurityConfig.java
│   └── OpenAPISecurityConfig.java
├── controllers/         # Endpoints REST
│   └── BookController.java
├── models/              # Entidades/DTOs
│   └── Book.java
├── repositories/        # Acesso a dados
│   └── IBookRepository.java
├── services/            # Lógica de negócio
│   ├── IBookService.java
│   └── BookServiceImplement.java
└── SpringbootApplication.java  # Classe principal
```

---

## 3. Componentes Principais

### 3.1 @SpringBootApplication

Anotação que marca a classe principal da aplicação:

```java
@SpringBootApplication
public class SpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
```

**O que faz:**
- `@Configuration`: Marca a classe como source de bean definitions
- `@EnableAutoConfiguration`: Ativa auto-configuration do Spring
- `@ComponentScan`: Escaneia componentes no pacote e subpacotes

### 3.2 Controllers (@RestController)

Responsáveis por tratar requisições HTTP:

```java
@RestController
@RequestMapping("/api/books")
public class BookController {
    
    @Autowired
    private IBookService bookService;
    
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable String id) {
        Book book = bookService.findById(id);
        return ResponseEntity.ok(book);
    }
    
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book created = bookService.save(book);
        return ResponseEntity.status(201).body(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
        @PathVariable String id, 
        @RequestBody Book book) {
        Book updated = bookService.update(id, book);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

**Anotações Principais:**
- `@RestController`: Combina `@Controller` + `@ResponseBody`
- `@RequestMapping`: Define o caminho base da rota
- `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`: Métodos HTTP
- `@PathVariable`: Extrai variáveis da URL
- `@RequestBody`: Mapeia JSON para objeto Java
- `@ResponseEntity`: Retorno com status e corpo customizado

---

## 4. Injeção de Dependência (Dependency Injection)

### 4.1 Conceito

Padrão que desacopla classes fornecendo dependências externamente:

```java
// ❌ Acoplamento forte
public class BookService {
    private BookRepository repository = new BookRepository();
}

// ✅ Desacoplamento - Injeção de dependência
public class BookService {
    private BookRepository repository;
    
    // Injeção via construtor (recomendado)
    public BookService(BookRepository repository) {
        this.repository = repository;
    }
}
```

### 4.2 Tipos de Injeção no Spring

#### Por Construtor (Recomendado)
```java
@Service
public class BookServiceImplement implements IBookService {
    private final IBookRepository repository;
    
    // Constructor Injection
    public BookServiceImplement(IBookRepository repository) {
        this.repository = repository;
    }
}
```

#### Por Setter
```java
@Service
public class BookServiceImplement implements IBookService {
    private IBookRepository repository;
    
    @Autowired
    public void setRepository(IBookRepository repository) {
        this.repository = repository;
    }
}
```

#### Por Atributo
```java
@Service
public class BookServiceImplement implements IBookService {
    @Autowired
    private IBookRepository repository;
}
```

### 4.3 @Autowired

Anotação que marca pontos de injeção automática:

```java
@Autowired
private IBookService bookService;

// Ou com qualifiers
@Autowired
@Qualifier("bookServiceImpl")
private IBookService bookService;
```

---

## 5. Camada de Serviço (Services)

### 5.1 Padrão Service

Encapsula a lógica de negócio:

```java
public interface IBookService {
    Book findById(String id);
    List<Book> findAll();
    Book save(Book book);
    Book update(String id, Book book);
    void delete(String id);
}

@Service
public class BookServiceImplement implements IBookService {
    
    private final IBookRepository repository;
    
    public BookServiceImplement(IBookRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Book findById(String id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado"));
    }
    
    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }
    
    @Override
    public Book save(Book book) {
        // Validações de negócio
        if (book.getPrice() < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
        return repository.save(book);
    }
    
    @Override
    public Book update(String id, Book book) {
        Book existing = findById(id);
        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        existing.setPrice(book.getPrice());
        return repository.save(existing);
    }
    
    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}
```

**Responsabilidades:**
- Validação de dados
- Aplicação de regras de negócio
- Chamada a repositórios
- Tratamento de exceções

---

## 6. Camada de Dados (Repositories)

### 6.1 Repository Pattern

Interface para abstrair acesso aos dados:

```java
public interface IBookRepository extends MongoRepository<Book, String> {
    // Métodos automáticos (herdados de MongoRepository)
    // findById, findAll, save, deleteById, etc.
    
    // Métodos customizados
    List<Book> findByAuthor(String author);
    Book findByTitle(String title);
    List<Book> findByPriceGreaterThan(Double price);
}
```

### 6.2 Spring Data JPA/MongoDB

Spring Data oferece implementações automáticas:

```java
// Operações CRUD automáticas
repository.findById(id);           // SELECT * WHERE id = ?
repository.findAll();               // SELECT *
repository.save(entity);            // INSERT ou UPDATE
repository.deleteById(id);          // DELETE WHERE id = ?
repository.count();                 // COUNT(*)

// Query Methods - derivados do nome do método
findByAuthor(String author)
findByTitle(String title)
findByPriceBetween(double min, double max)
findByAuthorOrderByTitleAsc(String author)
findByPriceGreaterThanAndAuthor(double price, String author)
```

---

## 7. Modelos (Models/Entities)

### 7.1 Entidade com Lombok

```java
@Document(collection = "books")
@Data                              // @Getter @Setter @ToString @EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    
    @Id
    private String id;
    
    @Field("title")
    private String title;
    
    @Field("author")
    private String author;
    
    @Field("price")
    private Double price;
    
    @Field("createdAt")
    private LocalDateTime createdAt;
}
```

**Anotações:**
- `@Document`: Marca como documento MongoDB
- `@Id`: Define chave primária
- `@Field`: Mapeia para campo no banco
- `@Data`: Gera getters, setters, toString, equals, hashCode

---

## 8. Configuração (Configuration)

### 8.1 MongoConfig

Configuração do MongoDB:

```java
@Configuration
public class MongoConfig {
    
    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "databaseName");
    }
}
```

### 8.2 SecurityConfig

Configuração de segurança:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/secure/**").authenticated()
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer((oauth2) -> oauth2
                .jwt(Customizer.withDefaults())
            );
        return http.build();
    }
}
```

### 8.3 OpenAPISecurityConfig

Configuração do Swagger/OpenAPI:

```java
@Configuration
public class OpenAPISecurityConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Book API")
                .version("1.0.0")
                .description("API para gerenciamento de livros"))
            .components(new Components()
                .addSecuritySchemes("bearer-jwt",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
    }
}
```

---

## 9. Dependências Principais

### 9.1 Spring Boot Starters

```xml
<!-- Web MVC para APIs REST -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webmvc</artifactId>
</dependency>

<!-- MongoDB -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>

<!-- Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- OAuth2 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>
```

### 9.2 Bibliotecas Auxiliares

```xml
<!-- Lombok - Reduz boilerplate -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>

<!-- SpringDoc OpenAPI - Swagger integrado -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
</dependency>

<!-- DevTools - Hot reload -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
</dependency>
```

---

## 10. Anotações Importantes

| Anotação | Local | Finalidade |
|----------|-------|-----------|
| `@SpringBootApplication` | Classe main | Marca ponto de entrada |
| `@Configuration` | Classe | Define configuração |
| `@Bean` | Método | Registra bean no contexto |
| `@Component` | Classe | Componente genérico |
| `@Service` | Classe | Serviço de negócio |
| `@Repository` | Interface | Acesso a dados |
| `@Controller` | Classe | Controlador MVC |
| `@RestController` | Classe | Controlador REST |
| `@RequestMapping` | Classe/Método | Define rota |
| `@GetMapping`, `@PostMapping`, etc | Método | Métodos HTTP |
| `@Autowired` | Propriedade/Construtor | Injeção de dependência |
| `@Transactional` | Método | Gerencia transações |
| `@Validated` | Classe | Valida entradas |
| `@CrossOrigin` | Classe/Método | Permite CORS |

---

## 11. Fluxo de Requisição HTTP

```
1. Cliente envia requisição HTTP
   ↓
2. DispatcherServlet recebe requisição
   ↓
3. Mapeamento para Controller apropriado (@RequestMapping)
   ↓
4. Controller invoca Service
   ↓
5. Service executa lógica de negócio
   ↓
6. Service chama Repository
   ↓
7. Repository acessa banco de dados
   ↓
8. Dados retornam pelo mesmo caminho
   ↓
9. Controller retorna ResponseEntity com resultado
   ↓
10. DispatcherServlet serializa resposta para JSON
    ↓
11. Cliente recebe resposta HTTP (JSON)
```

---

## 12. Configuração de Aplicação (application.yml)

```yaml
spring:
  application:
    name: springboot
  
  data:
    mongodb:
      uri: mongodb://localhost:27017/library
      database: library
  
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: https://seu-provedor-oauth.com
          jwk-set-uri: https://seu-provedor-oauth.com/.well-known/jwks.json

server:
  port: 8080
  servlet:
    context-path: /api

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
```

---

## 13. Tratamento de Erros

### 13.1 Exception Handler Global

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
        ResourceNotFoundException ex,
        HttpServletRequest request) {
        
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(
        Exception ex,
        HttpServletRequest request) {
        
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Erro interno do servidor",
            request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
```

### 13.2 Exceções Customizadas

```java
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
```

---

## 14. Segurança

### 14.1 OAuth2 + JWT

Fluxo de autenticação:

```
1. Cliente envia credenciais
   ↓
2. Servidor valida e emite JWT token
   ↓
3. Cliente envia token em cada requisição
   (Authorization: Bearer <token>)
   ↓
4. Servidor valida token
   ↓
5. Acesso permitido/negado
```

### 14.2 Proteção de Endpoints

```java
@RestController
@RequestMapping("/api/books")
public class BookController {
    
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<Book> getAll() { }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Book create(@RequestBody Book book) { }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable String id) { }
}
```

---

## 15. Padrões e Melhores Práticas

### 15.1 RESTful API Design

```
GET    /api/books           → Listar todos
GET    /api/books/{id}      → Obter um
POST   /api/books           → Criar novo
PUT    /api/books/{id}      → Atualizar
DELETE /api/books/{id}      → Deletar
```

### 15.2 Versionamento de API

```java
@RestController
@RequestMapping("/api/v1/books")
public class BookControllerV1 { }

@RestController
@RequestMapping("/api/v2/books")
public class BookControllerV2 { }
```

### 15.3 Logging

```java
@Service
public class BookServiceImplement {
    
    private static final Logger logger = 
        LoggerFactory.getLogger(BookServiceImplement.class);
    
    public Book findById(String id) {
        logger.info("Buscando livro com ID: {}", id);
        try {
            return repository.findById(id).orElseThrow();
        } catch (Exception e) {
            logger.error("Erro ao buscar livro", e);
            throw e;
        }
    }
}
```

---

## 16. Testes

### 16.1 Teste Unitário

```java
@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    
    @Mock
    private IBookRepository repository;
    
    @InjectMocks
    private BookServiceImplement service;
    
    @Test
    void testFindById() {
        Book book = new Book("id1", "Clean Code", "Robert Martin", 50.0);
        
        when(repository.findById("id1")).thenReturn(Optional.of(book));
        
        Book result = service.findById("id1");
        
        assertEquals("Clean Code", result.getTitle());
        verify(repository, times(1)).findById("id1");
    }
}
```

### 16.2 Teste de Integração

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void testGetAllBooks() {
        ResponseEntity<List> response = 
            restTemplate.getForEntity("/api/books", List.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
```

---

## 17. Ciclo de Vida do Bean

```
1. Instantiation
   ↓
2. Populate Properties (Dependency Injection)
   ↓
3. BeanNameAware.setBeanName()
   ↓
4. BeanFactoryAware.setBeanFactory()
   ↓
5. ApplicationContextAware.setApplicationContext()
   ↓
6. BeanPostProcessor.postProcessBeforeInitialization()
   ↓
7. InitializingBean.afterPropertiesSet()
   ↓
8. Custom init-method
   ↓
9. BeanPostProcessor.postProcessAfterInitialization()
   ↓
10. Bean pronto para usar
    ↓
11. Destruição (Shutdown)
    ↓
12. DisposableBean.destroy()
    ↓
13. Custom destroy-method
```

---

## 18. Actuator

Monitoramento e management da aplicação:

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
```

**Endpoints disponíveis:**
- `/actuator/health` - Status da aplicação
- `/actuator/info` - Informações da app
- `/actuator/metrics` - Métricas
- `/actuator/env` - Variáveis de ambiente
- `/actuator/beans` - Beans registrados

---

## 19. Referências

- [Spring Boot Official Documentation](https://spring.io/projects/spring-boot)
- [Spring Framework Documentation](https://spring.io/projects/spring-framework)
- [Spring Data MongoDB](https://spring.io/projects/spring-data-mongodb)
- [Spring Security](https://spring.io/projects/spring-security)
- [SpringDoc OpenAPI](https://springdoc.org/)

---

## 20. Conclusão

Spring Boot simplifica significativamente o desenvolvimento de aplicações Java modernas, oferecendo:

✅ Configuração automática e sensata  
✅ Dependências pré-configuradas  
✅ Embedded servers  
✅ Produtividade aumentada  
✅ Manutenibilidade e escalabilidade  
✅ Ecossistema robusto  

Este projeto demonstra uma arquitetura limpa e escalável utilizando os melhores práticas do Spring Boot.

---

**Última atualização**: 17 de janeiro de 2026
