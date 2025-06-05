# JWT Spring Security - Documentação Inicial

# Desenho da Arquitetura

A arquitetura do projeto segue o padrão Clean Architecture, separando responsabilidades em camadas bem definidas. Veja o fluxo e a relação entre as camadas:

```
┌───────────────────────────────┐
│        Controllers (API)      │
│ ──────────────────────────── │
│ Recebem requisições HTTP      │
│ Delegam para Application      │
└───────────────┬───────────────┘
                │
                ▼
┌───────────────────────────────┐
│   Application Services        │
│ ──────────────────────────── │
│ Orquestram casos de uso       │
│ Aplicam regras de negócio     │
│ Dependem apenas de Ports      │
└───────────────┬───────────────┘
                │
                ▼
┌───────────────────────────────┐
│           Ports               │
│ ──────────────────────────── │
│ Interfaces/contratos          │
│ Desacoplam domínio da infra   │
└───────────────┬───────────────┘
                │
                ▼
┌───────────────────────────────┐
│      Infrastructure           │
│ ──────────────────────────── │
│ Adapters/implementações       │
│ Integração com frameworks     │
└───────────────────────────────┘
```

# Organização de Pastas do Projeto

O projeto segue uma arquitetura limpa (Clean Architecture) e DDD, separando responsabilidades em camadas bem definidas:

```
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/mvbr/jwtspringsecurity/
│   │   │       ├── Application.java                # Classe principal Spring Boot
│   │   │       ├── application/                    # Camada de aplicação (serviços/orquestração)
│   │   │       ├── config/                         # Configurações (ex: segurança, beans)
│   │   │       ├── controller/                     # Controllers REST (entrada HTTP)
│   │   │       ├── dto/                            # Objetos de transferência de dados (requests/responses)
│   │   │       ├── exception/                      # Exceções customizadas e handlers
│   │   │       ├── infrastructure/                 # Adapters/implementações técnicas dos ports
│   │   │       ├── model/                          # Modelos de domínio/entidades
│   │   │       ├── repository/                     # Interfaces e implementações de acesso a dados
│   │   │       ├── service/                        # Serviços auxiliares e utilitários
│   │   │       └── utils/                          # Utilitários gerais
│   │   └── resources/
│   │       ├── application.properties              # Configurações da aplicação
│   │       ├── static/                            # Arquivos estáticos (ex: HTML, JS, CSS)
│   │       └── templates/                         # Templates para e-mails/views
│   └── test/
│       └── java/com/mvbr/jwtspringsecurity/        # Testes automatizados (unitários/integrados)
│           └── controller/                        # Testes dos controllers REST
```

## Comentários
- **application/**: Serviços de orquestração dos casos de uso, dependem apenas de interfaces (ports).
- **infrastructure/**: Adapters/implementações técnicas dos ports, conectam com frameworks, libs e recursos externos.
- **controller/**: Camada de entrada HTTP (REST), expõe endpoints para o frontend ou outros sistemas.
- **dto/**: Objetos para transportar dados entre camadas e expor contratos de API.
- **exception/**: Exceções customizadas e tratamento global de erros.
- **repository/**: Interfaces e implementações para persistência de dados.
- **service/**: Serviços auxiliares, utilitários e lógica de apoio.
- **model/**: Entidades e objetos de domínio.
- **test/**: Testes automatizados para todas as camadas.

Essa organização facilita a manutenção, testes e evolução do sistema, mantendo o domínio desacoplado da infraestrutura.

## O que o projeto oferece?
- **Autenticação e autorização com JWT** (stateless, seguro e escalável)
- **Recuperação de senha, confirmação de e-mail, 2FA, refresh token, blacklist de JWT, controle de tentativas de login, políticas de senha forte, RBAC, consentimento, logout remoto, auditoria, notificações de login suspeito** e muito mais
- **Arquitetura desacoplada**: fácil de evoluir, testar e trocar detalhes técnicos sem afetar o domínio
- **Testes automatizados** para todos os fluxos críticos
- **Documentação automática da API** via Swagger/OpenAPI

## Como rodar o projeto
1. **Pré-requisitos:** Java 17+, Maven 3.8+
2. **Instale as dependências:**
   ```bash
   ./mvnw clean install
   ```
3. **Execute a aplicação:**
   ```bash
   ./mvnw spring-boot:run
   ```
4. **Acesse a API:**
   - Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
   - Endpoints principais: `/api/auth`, `/api/password`, `/api/2fa`, `/api/consent`, etc.

## Segurança
- **Spring Security** protege todos os endpoints, permitindo configuração granular de acesso
- **JWT** garante autenticação stateless, ideal para APIs REST, SPAs e mobile
- **Recursos avançados**: blacklist de tokens, refresh token, 2FA, rate limiting, políticas de senha, RBAC, consentimento, auditoria, etc.

## Organização do Código
- `controller/`: Endpoints REST (entrada HTTP)
- `application/`: Serviços de orquestração dos casos de uso
- `infrastructure/`: Adapters/implementações técnicas dos ports
- `model/`: Entidades e objetos de domínio
- `repository/`: Interfaces e implementações de
