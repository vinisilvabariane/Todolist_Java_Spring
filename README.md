# 📝 Gerenciador de Tarefas Diárias

## Descrição

O **Gerenciador de Tarefas Diárias** é uma API REST desenvolvida em Java com **Spring Boot**, focada na organização de atividades diárias.  

Com autenticação simples via UUID, o sistema permite a criação e manipulação de tarefas com validações rigorosas de dados, além de garantir que somente o usuário proprietário da tarefa possa alterá-la ou removê-la.  

### Funcionalidades

- 👤 **Gerenciamento de Usuários:** Cadastro e autenticação via UUID.  
- 📋 **CRUD de Tarefas:** Criação, edição, listagem e exclusão de tarefas diárias.  
- 📅 **Validação de Datas:** Impede criação de tarefas com datas inválidas (fim antes do início).  
- 🔒 **Segurança via Filtro:** Requisições são protegidas por um filtro customizado baseado no cabeçalho `Authorization`.  
- ⚠️ **Tratamento de Erros:** Retorno padronizado para exceções e erros de validação.

---

## Tecnologias Utilizadas

### ☕ Backend

- Java 17+  
- Spring Boot 3+  
- Spring Web  
- Spring Data JPA  
- Spring Validation  
- Lombok  
- H2 Database (in-memory)


---

## 🔐 Autenticação

- A API utiliza um filtro (`FilterTaskAuth`) que intercepta todas as requisições para `/task/**`.
- O cabeçalho `Authorization` deve conter o **UUID** do usuário previamente cadastrado.
- Se o UUID não existir, a requisição é bloqueada com erro `401 Unauthorized`.

---

## ✅ Validações

- `startAt` deve ser anterior a `endAt`.  
- Apenas o dono da tarefa (identificado via UUID) pode modificá-la ou excluí-la.  
- Campos obrigatórios são validados com anotações como `@NotBlank`, `@NotNull`, etc.  

---

## 🧪 Banco de Dados

- O projeto utiliza **H2 em memória**, ideal para testes e desenvolvimento.
- Console H2 ativado por padrão.

