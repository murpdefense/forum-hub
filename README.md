# ForumHub

![Cover](./.github/cover.png)
![Java CI with Maven Status](https://github.com/soupaulodev/one-forumhub/actions/workflows/maven.yml/badge.svg)

Este projeto é minha aplicação para o desafio da Trilha de Especialização em Spring do Oracle ONE em parceria com Alura.

Acesse: [ONE - ForumHub](https://github.com/soupaulodev/one-forumhub)

## Descrição

O projeto vai além dos requisitos do desafio. Além de permitir a criação de tópicos e a autenticação de usuários,
ele possibilita a interação entre os usuários, permitindo que criem e acessem novos fóruns, participem de discussões
por meio de tópicos e comentários, e distribuam highs em tudo o que gostarem.

## Tecnologias

### Core

- Java 21
- Spring Boot 3.4
- Spring Data JPA
- Spring Validation
- PostgreSQL
- Maven

### Segurança

- Spring Security 6
- Json Web Token

### Testes

- JUnit 5
- Mockito
- H2 Database

### Cache

- Spring Data Redis
- Spring Cache

### Documentação

- SpringDoc OpenAPI

### DevOps

- Spring Actuator

### Ferramentas de Desenvolvimento

- Spring DevTools

## Endpoints

### Documentação

- GET /swagger-ui.html
- GET /v3/api-docs

### Autenticação

- `POST /api/v1/auth/signup`  
  **Descrição**: Cria e autentica um novo usuário no sistema.

- `POST /api/v1/auth/login`  
  **Descrição**: Realiza o login de um usuário no sistema.

- `POST /api/v1/auth/logout`  
  **Descrição**: Realiza o logout do usuário autenticado.

### Usuários

- `GET /api/v1/users/all?page={PageNumber}&size={ItemsPerPage}`  
  **Descrição**: Obtém uma lista de usuários paginada.  
  **Parâmetros**:
  - `page`: Número da página.
  - `size`: Quantidade de itens por página.

- `GET /api/v1/users/{id}`  
  **Descrição**: Obtém os dados de um usuário específico pelo ID.

- `PUT /api/v1/users/{id}`  
  **Descrição**: Atualiza as informações de um usuário específico pelo ID.

- `DELETE /api/v1/users/{id}`  
  **Descrição**: Exclui um usuário específico pelo ID.

- `POST /api/v1/users/high/{id}`  
  **Descrição**: Adiciona um high a um usuário, identificando-o com o ID.

- `DELETE /api/v1/users/unhigh/{id}`  
  **Descrição**: Remove o high dado a um usuário, identificando-o com o ID.

### Fóruns

- `GET /api/v1/forums/all?page={PageNumber}&size={ItemsPerPage}`  
  **Descrição**: Obtém uma lista de fóruns paginada.  
  **Parâmetros**:
  - `page`: Número da página.
  - `size`: Quantidade de itens por página.

- `GET /api/v1/forums/{id}`  
  **Descrição**: Obtém os dados de um fórum específico pelo ID.

- `POST /api/v1/forums`  
  **Descrição**: Cria um novo fórum.

- `PUT /api/v1/forums/{id}`  
  **Descrição**: Atualiza as informações de um fórum específico pelo ID.

- `DELETE /api/v1/forums/{id}`  
  **Descrição**: Exclui um fórum específico pelo ID.

- `POST /api/v1/forums/high/{id}`  
  **Descrição**: Adiciona um high a um tópico, identificando-o com o ID.

- `DELETE /api/v1/forums/unhigh/{id}`  
  **Descrição**: Remove o high dado a um fórum, identificando-o com o ID.

### Tópicos

- `GET /api/v1/topics/all?page={PageNumber}&size={ItemsPerPage}`  
  **Descrição**: Obtém uma lista de tópicos paginada.  
  **Parâmetros**:
  - `page`: Número da página.
  - `size`: Quantidade de itens por página.

- `GET /api/v1/topics/{id}`  
  **Descrição**: Obtém os dados de um tópico específico pelo ID.

- `POST /api/v1/topics`  
  **Descrição**: Cria um novo tópico.

- `PUT /api/v1/topics/{id}`  
  **Descrição**: Atualiza as informações de um tópico específico pelo ID.

- `DELETE /api/v1/topics/{id}`  
  **Descrição**: Exclui um tópico específico pelo ID.

- `POST /api/v1/topics/high/{id}`  
  **Descrição**: Adiciona um high a um tópico, identificando-o com o ID.

- `DELETE /api/v1/topics/unhigh/{id}`  
  **Descrição**: Remove o high dado a um tópico, identificando-o com o ID.

### Comentários

- `GET /api/v1/comments/all?page={PageNumber}&size={ItemsPerPage}`  
  **Descrição**: Obtém uma lista de comentários paginada.  
  **Parâmetros**:
  - `page`: Número da página.
  - `size`: Quantidade de itens por página.

- `POST /api/v1/comments`  
  **Descrição**: Cria um novo comentário.

- `PUT /api/v1/comments/{id}`  
  **Descrição**: Atualiza um comentário específico pelo ID.

- `DELETE /api/v1/comments/{id}`  
  **Descrição**: Exclui um comentário específico pelo ID.

- `POST /api/v1/users/high/{id}`  
  **Descrição**: Adiciona um high a um comentário, identificando-o com o ID.

- `DELETE /api/v1/users/unhigh/{id}`  
  **Descrição**: Remove o high dado a um comentário, identificando-o com o ID.

## Licença

Este projeto está licenciado sob a Licença MIT - veja o
arquivo [license](https://github.com/soupaulodev/one-forumhub/blob/main/LICENSE) para detalhes

## Contribuindo

Primeiramente, obrigado por considerar contribuir para este projeto. Toda ajuda é bem-vinda. Se você deseja contribuir,
siga estas etapas:

1. Fork o projeto
2. Crie uma nova branch (`git checkout -b feature/feature-name`)
3. Realize as alterações
4. Commit suas alterações
5. Push para a branch (`git push origin feature/feature-name`)
6. Abra um Pull Request
