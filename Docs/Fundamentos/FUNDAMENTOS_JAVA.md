# 📚 Fundamentos de Java

## Introdução

Este documento apresenta os fundamentos essenciais da linguagem Java utilizados neste projeto Spring Boot. Java é uma linguagem orientada a objetos, robusta e amplamente utilizada no desenvolvimento de aplicações corporativas.

---

## 1. Configuração e Ambiente

### Java Version
- **Versão utilizada**: Java 17 (LTS - Long Term Support)
- Definida em `pom.xml` via propriedade `<java.version>17</java.version>`
- Java 17 oferece recursos modernos como Records, Pattern Matching e módulos melhorados

### Maven
- **Gerenciador de Dependências**: Apache Maven 3.x+
- **Arquivo de Configuração**: `pom.xml`
- Responsável por compilação, testes e empacotamento da aplicação

---

## 2. Estrutura de Diretórios

```
src/
├── main/
│   ├── java/                  # Código-fonte Java
│   │   └── br/com/dioavanade/springboot/
│   │       ├── config/        # Classes de configuração
│   │       ├── controllers/   # Controllers REST
│   │       ├── models/        # Entidades/Modelos
│   │       ├── repositories/  # Acesso a dados
│   │       └── services/      # Lógica de negócio
│   └── resources/
│       └── application.yml    # Configurações da aplicação
└── test/
    └── java/                  # Testes unitários
```

---

## 3. Conceitos Fundamentais de Java

### 3.1 Classes e Objetos

**Definição**: Classes são templates para criar objetos, que são instâncias das classes.

```java
public class Book {
    private String title;
    private String author;
    private double price;
    
    // Construtor
    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }
    
    // Getters e Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
```

### 3.2 Modificadores de Acesso

| Modificador | Classe | Pacote | Subclasse | Público |
|------------|--------|--------|-----------|---------|
| `public`   | ✓      | ✓      | ✓         | ✓       |
| `protected`| ✓      | ✓      | ✓         | ✗       |
| `default`  | ✓      | ✓      | ✗         | ✗       |
| `private`  | ✓      | ✗      | ✗         | ✗       |

### 3.3 Encapsulamento

Princípio de ocultar detalhes internos da classe:
- Atributos privados
- Métodos públicos (getters e setters) para acesso controlado
- Protege a integridade dos dados

```java
private String email;

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    if (email != null && email.contains("@")) {
        this.email = email;
    }
}
```

### 3.4 Herança

Mecanismo que permite uma classe herdar propriedades e métodos de outra:

```java
public class Animal {
    public void fazer_som() {
        System.out.println("Som genérico");
    }
}

public class Cachorro extends Animal {
    @Override
    public void fazer_som() {
        System.out.println("Au au!");
    }
}
```

### 3.5 Polimorfismo

Capacidade de um objeto ser referenciado de múltiplas formas:

```java
Animal animal = new Cachorro(); // Polimorfismo
animal.fazer_som(); // Executa método da subclasse Cachorro
```

### 3.6 Abstração

Uso de classes abstratas e interfaces para definir contratos:

```java
public abstract class Veiculo {
    public abstract void acelerar();
}

public interface Teletransporte {
    void teleportar();
}
```

---

## 4. Tipos de Dados

### 4.1 Tipos Primitivos

| Tipo    | Tamanho | Valor Padrão | Alcance           |
|---------|---------|--------------|-------------------|
| `byte`  | 1 byte  | 0            | -128 a 127        |
| `short` | 2 bytes | 0            | -32768 a 32767    |
| `int`   | 4 bytes | 0            | -2³¹ a 2³¹-1      |
| `long`  | 8 bytes | 0L           | -2⁶³ a 2⁶³-1      |
| `float` | 4 bytes | 0.0f         | IEEE 754          |
| `double`| 8 bytes | 0.0d         | IEEE 754          |
| `char`  | 2 bytes | '\u0000'     | 0 a 65535         |
| `boolean`| 1 bit  | false        | true/false        |

### 4.2 Tipos Referência

- **Classes**: Objetos criados a partir de classes
- **Arrays**: Coleções de elementos do mesmo tipo
- **String**: Sequência de caracteres imutável
- **Interfaces**: Contratos de implementação

```java
String nome = "Java"; // String é um tipo referência
int[] numeros = {1, 2, 3}; // Array
List<String> lista = new ArrayList<>(); // Coleção genérica
```

---

## 5. Collections Framework

Framework para trabalhar com coleções de objetos:

### 5.1 Principais Interfaces

- **List**: Coleção ordenada, permite duplicatas
- **Set**: Sem duplicatas, sem ordem garantida
- **Map**: Pares chave-valor
- **Queue**: Fila com operações de inserção e remoção

### 5.2 Implementações Comuns

```java
List<String> lista = new ArrayList<>();
Set<Integer> conjunto = new HashSet<>();
Map<String, Integer> mapa = new HashMap<>();
Queue<String> fila = new LinkedList<>();
```

---

## 6. Tratamento de Exceções

Mecanismo para lidar com erros durante execução:

```java
try {
    // Código que pode gerar exceção
    int resultado = 10 / 0;
} catch (ArithmeticException e) {
    // Tratamento específico
    System.out.println("Erro na divisão: " + e.getMessage());
} catch (Exception e) {
    // Tratamento genérico
    System.out.println("Erro geral: " + e.getMessage());
} finally {
    // Sempre executado
    System.out.println("Fim do tratamento");
}
```

### Tipos de Exceções

- **Checked Exceptions**: Verificadas em tempo de compilação
- **Unchecked Exceptions**: Verificadas apenas em tempo de execução

```java
// Checked - deve ser declarada ou capturada
public void lerArquivo() throws IOException {
    FileReader reader = new FileReader("arquivo.txt");
}

// Unchecked
int x = 5 / 0; // ArithmeticException (RuntimeException)
```

---

## 7. Annotations (Anotações)

Metadados que fornecem informações sobre o código:

```java
@Override // Indica que o método sobrescreve método da superclasse
public String toString() {
    return "Livro";
}

@Deprecated // Indica que a funcionalidade é obsoleta
public void metodoAntigo() { }

@SuppressWarnings("unchecked") // Suprime avisos do compilador
List lista = new ArrayList();
```

---

## 8. Modificadores de Classe

### 8.1 `static`

Pertence à classe, não à instância:

```java
public class Contador {
    static int contagem = 0; // Compartilhado entre todas as instâncias
    
    public Contador() {
        contagem++;
    }
    
    static void exibirContagem() {
        System.out.println(contagem);
    }
}

Contador.exibirContagem(); // Chamado pela classe
```

### 8.2 `final`

Previne modificação:

```java
final int CONSTANTE = 10; // Não pode ser modificada
final class ClasseFinal { } // Não pode ser estendida
final void metodoFinal() { } // Não pode ser sobrescrito
```

---

## 9. Genéricos

Permitem criar código reutilizável e type-safe:

```java
public class Caixa<T> {
    private T conteudo;
    
    public void adicionar(T item) {
        this.conteudo = item;
    }
    
    public T obter() {
        return conteudo;
    }
}

// Uso
Caixa<String> caixaString = new Caixa<>();
caixaString.adicionar("Olá");

Caixa<Integer> caixaInt = new Caixa<>();
caixaInt.adicionar(42);
```

---

## 10. Programação Funcional (Java 8+)

### 10.1 Expressões Lambda

Forma concisa de escrever funções anônimas:

```java
// Sem lambda
List<String> lista = Arrays.asList("a", "b", "c");
lista.forEach(new Consumer<String>() {
    public void accept(String s) {
        System.out.println(s);
    }
});

// Com lambda
lista.forEach(s -> System.out.println(s));

// Mais complexo
lista.stream()
    .filter(s -> s.length() > 1)
    .map(String::toUpperCase)
    .forEach(System.out::println);
```

### 10.2 Streams API

Operações em coleções de forma funcional:

```java
List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);

// Operações
long pares = numeros.stream()
    .filter(n -> n % 2 == 0)
    .count();

List<Integer> dobrados = numeros.stream()
    .map(n -> n * 2)
    .collect(Collectors.toList());
```

---

## 11. Padrões de Design

### 11.1 Singleton

Garante uma única instância da classe:

```java
public class Database {
    private static Database instancia;
    
    private Database() { }
    
    public static Database getInstance() {
        if (instancia == null) {
            instancia = new Database();
        }
        return instancia;
    }
}
```

### 11.2 Builder

Constrói objetos complexos passo a passo:

```java
Book book = new Book.Builder()
    .setTitle("Clean Code")
    .setAuthor("Robert Martin")
    .setPrice(50.0)
    .build();
```

### 11.3 Factory

Cria objetos sem especificar suas classes exatas:

```java
public class VeiculoFactory {
    public static Veiculo criar(String tipo) {
        if ("carro".equals(tipo)) {
            return new Carro();
        }
        return new Bicicleta();
    }
}
```

---

## 12. Boas Práticas

1. **Naming Conventions**: Use camelCase para variáveis/métodos, PascalCase para classes
2. **DRY (Don't Repeat Yourself)**: Evite código duplicado
3. **SOLID**: Princípios de design orientado a objetos
4. **Comentários**: Use para explicar o "porquê", não o "quê"
5. **Testes**: Escreva testes para suas classes
6. **Segurança**: Valide entrada, use tipos seguros

---

## Referências

- [Oracle Java Documentation](https://docs.oracle.com/en/java/)
- [Java Language and Virtual Machine Specifications](https://docs.oracle.com/javase/specs/)
- [Effective Java by Joshua Bloch](https://www.oracle.com/java/)

---

**Última atualização**: 17 de janeiro de 2026
