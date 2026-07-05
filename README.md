# AuthLab

API de estudo criada para revisar autenticação e autorização com Spring Security.

O projeto simula uma API de notas privadas, onde cada usuário pode criar e gerenciar apenas os próprios recursos, enquanto rotas administrativas ficam restritas a perfis com mais privilégio. A proposta é entender o fluxo completo de autenticação em uma aplicação Spring Boot sem adicionar complexidade arquitetural desnecessária.

## Intuito do projeto

Este repositório existe para praticar, de forma objetiva:

- autenticação com login e senha
- geração e validação de JWT
- uso de refresh token e logout
- controle de acesso com roles
- ownership de recursos
- identificação do usuário autenticado no backend
- login social com Google (OAuth2 / ID Token)

## Tecnologias

- Java 21
- Spring Boot 3
- Spring Security 6
- Spring Data JPA
- PostgreSQL
- Flyway
- JWT
- Google API Client (verificação de login social)
- JUnit 5
- Mockito

## Abordagem

A estrutura será simples e tradicional, organizada em camadas como `controller`, `service`, `repository`, `entity`, `dto`, `security` e `config`. O objetivo aqui não é explorar arquitetura avançada, e sim deixar claro como cada parte participa do fluxo de autenticação e autorização.

## Funcionalidades

- Registro e login com e-mail/senha
- Login social com Google, reaproveitando o mesmo fluxo de emissão de JWT do login tradicional (veja [GOOGLE_AUTH.md](./GOOGLE_AUTH.md))
- CRUD de notas com ownership: cada usuário só acessa as próprias notas
- Usuários com role `ROLE_ADMIN` podem visualizar notas de qualquer usuário
- Página de demonstração em `http://localhost:8080/` para testar login (senha e Google) e notas sem precisar de Postman
