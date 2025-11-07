# 🏢 RoomReservation API

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3-brightgreen)
![JUnit 5](https://img.shields.io/badge/Tests-JUnit%205-blue)
![Mockito](https://img.shields.io/badge/Mocking-Mockito-yellow)
![Flyway](https://img.shields.io/badge/Database-Flyway-red)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

---

## 🧾 Sobre o Projeto

O **RoomReservation** é um sistema de **reservas de salas** desenvolvido como parte do **Checkpoint Nível 1** da trilha **Carreira Desenvolvedor Back-end com Java** da **Alura**.  

A aplicação consiste em uma **API RESTful** que permite o gerenciamento de usuários e reservas de salas, oferecendo operações de CRUD e validações robustas.  
Todo o projeto foi construído seguindo boas práticas de **clean code**, **camadas bem definidas** e **padrões de projeto (Design Patterns)**.

---

## 🚀 Tecnologias Utilizadas

| Tecnologia | Descrição |
|-------------|------------|
| ☕ **Java 21** | Linguagem principal utilizada |
| 🌱 **Spring Boot 3** | Framework para criação da API |
| 🗃️ **Spring Data JPA** | Abstração ORM para operações com banco de dados |
| ✅ **Bean Validation (Jakarta Validation)** | Validação automática de dados |
| 🧩 **Flyway** | Versionamento e migração do banco de dados |
| 🧪 **JUnit 5** | Framework de testes unitários |
| 🎭 **Mockito** | Simulação de dependências para testes |
| 💾 **H2 Database** | Banco de dados em memória para ambiente de testes |

---

## ⚙️ Funcionalidades Principais

✅ CRUD completo de **Usuários**  
✅ CRUD de **Salas**  
✅ Criação e cancelamento de **Reservas de Sala**  
✅ **Validações de dados** (CPF, e-mail e telefone já cadastrados)  
✅ **Tratamento centralizado de exceções** via `ControllerAdvice`  
✅ **Testes unitários e de integração** com `JUnit 5` e `MockMvc`  
✅ Uso de **Design Patterns** para modularidade e reutilização

---

## 🧩 Modelo de Domínio

O domínio da aplicação representa o processo de reserva de salas por usuários, estruturado em três entidades principais — User, Reservation e Room — além dos enumeradores RoomStatus e ReservationStatus para controle de estado.
O modelo foi projetado para manter relações claras entre os objetos e refletir diretamente a lógica de negócio da API.


<img width="1570" height="816" alt="room-reservation-api-domain" src="https://github.com/user-attachments/assets/b069438f-8f91-46e8-b00f-50711826f830" />


## 🧩 Arquitetura do Projeto

O projeto segue uma **arquitetura em camadas** bem definida, promovendo clareza, isolamento e testabilidade.

## 🧠 Padrões de Projeto Utilizados

O projeto aplica diversos **Design Patterns** para garantir modularidade, legibilidade e manutenção de código.

### 🪄 DTO (Data Transfer Object)
Isola a camada de domínio das representações externas (JSON), garantindo segurança e desacoplamento.

- `CreateUserRequestDto` → utilizado para criar um novo usuário  
- `UserDto` → utilizado nas respostas da API

### 🧭 Mapper Pattern
Facilita a conversão entre entidades e DTOs, isolando essa lógica em uma classe responsável.

```java
public class UserMapper {
    public static UserDto toDto(User user) {
        return new UserDto(user.getName(), user.getCpf(), user.getAge(), user.getPhone(), user.getEmail());
    }
}
````

### 🧩 Strategy Pattern — Validações de Usuário
As validações seguem o padrão **Strategy**, permitindo adicionar novas regras sem modificar o código existente.

```
├── validations
│ ├── UserValidation.java → Interface base para todas as validações
│ ├── CpfAlreadyExistsValidation.java → Valida duplicidade de CPF
│ ├── PhoneAlreadyExistsValidation.java → Valida duplicidade de telefone
│ ├── EmailAlreadyExistsValidation.java → Valida duplicidade de e-mail
```

Cada validação implementa a interface `UserValidation` e é executada dinamicamente dentro do serviço, garantindo **baixo acoplamento** e **alta coesão**.

---

## 🧩 Arquitetura de Pastas

O projeto segue uma **arquitetura em camadas** bem definida, promovendo clareza, isolamento e testabilidade.

```
com.alura.br.RoomReservation
│
├── controllers → Camada de entrada (exposição de endpoints REST)
├── dto → Objetos de transferência de dados (Request e Response)
├── models → Entidades do domínio mapeadas pelo JPA
├── repositories → Interfaces de persistência (Spring Data JPA)
├── services → Regras de negócio e orquestração de fluxos
├── validations → Estratégias de validação de usuário (Strategy Pattern)
├── exceptions → Classes de erro, exceções personalizadas e Controller Advice
├── utils → Mappers e classes utilitárias
└── config → Configurações da aplicação (Flyway, banco, etc.)
```


---

## 🧪 Testes Automatizados

Toda a API foi coberta com **testes unitários** utilizando **JUnit 5** e **Mockito**, garantindo confiabilidade e fácil manutenção.

Os testes abrangem:

- ✅ **Camada de Serviço** → validações e regras de negócio.  
- ✅ **Controllers** → testados com MockMvc, validando requisições e respostas.  
- ✅ **Controller Advice** → valida o tratamento de exceções e mensagens de erro.

**Exemplo de teste de controller:**
```java
mockMvc.perform(post("/api/v1/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(createUserDto)))
    .andExpect(status().isCreated())
    .andExpect(jsonPath("$.name").value("Ana"));
```

## 💡 Aprendizados

Durante o desenvolvimento deste projeto foram aplicados e reforçados conceitos como:

- 🧱 **Arquitetura em camadas** e boas práticas de design  
- 🧩 **Aplicação de padrões de projeto** (DTO, Mapper, Strategy)  
- 🧪 **Testes automatizados** com JUnit 5 e Mockito  
- 🧠 **Uso de Flyway** para versionamento de banco de dados  
- ✅ **Validações com Bean Validation**  
- 🌐 **Criação e exposição de uma API RESTful completa** com Spring Boot  

---

## 👨‍💻 Autor

**Vladimir Monteiro Souza de Lima**  
Desenvolvedor Java em formação pela **Alura**

📫 **Contato:**  
[LinkedIn](https://www.linkedin.com/in/seu-linkedin) | [GitHub](https://github.com/seuusuario)



