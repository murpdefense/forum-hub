# ForumHub
![Cover](./.github/cover.png)
![Java CI with Maven Status](https://github.com/soupaulodev/one-forumhub/actions/workflows/maven.yml/badge.svg)

Este projeto é minha aplicação para o desafio da Trilha de Especialização em Spring do Oracle ONE em parceria com Alura.

Acesse: [ONE - ForumHub](https://github.com/soupaulodev/one-forumhub)

## Descrição

O projeto vai além dos requisitos do desafio, além de criar tópicos e autenticar usuários, possibilita que usuários
interajam entre si, criando e acessando novos foruns, tópicos, comentários e curtindo seus tópicos favoritos.

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
- POST /api/v1/auth/signup
- POST /api/v1/auth/login
  
### Usuários
- GET /api/v1/users/{id}
- PUT /api/v1/users/{id}
- DELETE /api/v1/users/{id}

### Fóruns
- GET   /api/v1/forums
- GET /api/v1/forums/{id}
- POST /api/v1/forums
- PUT /api/v1/forums/{id}
- DELETE /api/v1/forums/{id}

### Tópicos

- GET /api/v1/topics
- GET /api/v1/topics/{id}
- POST /api/v1/topics
- PUT /api/v1/topics/{id}
- DELETE /api/v1/topics/{id}

### Curtidas

- POST /api/v1/likes
- DELETE /api/v1/likes

### Comentários
- GET /api/v1/comments
- GET /api/v1/comments/{id}
- POST /api/v1/comments
- PUT /api/v1/comments/{id}
- DELETE /api/v1/comments/{id}

## Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [license](https://github.com/soupaulodev/one-forumhub/blob/main/LICENSE) para detalhes

## Contribuindo

Primeiramente, obrigado por considerar contribuir para este projeto. Toda ajuda é bem-vinda. Se você deseja contribuir, siga estas etapas:
1. Fork o projeto
2. Crie uma nova branch (`git checkout -b feature/feature-name`)
3. Realize as alterações
4. Commit suas alterações
5. Push para a branch (`git push origin feature/feature-name`)
6. Abra um Pull Request
