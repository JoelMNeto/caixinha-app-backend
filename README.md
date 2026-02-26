💰 Household Finance API

API REST para controle financeiro compartilhado por grupos (households).

Base URL:

/api/v1

Autenticação obrigatória via JWT (exceto login/registro).

📦 Controllers e Endpoints
👤 UsersController
POST   /users
POST   /auth/login
GET    /users/me
PUT    /users/me

Responsabilidade:
Gerenciar usuários e autenticação.

Cuida apenas de identidade e perfil.
Não lida com dados financeiros.

🏠 HouseholdsController
POST   /households
GET    /households
GET    /households/{id}
PUT    /households/{id}
DELETE /households/{id}

Responsabilidade:
Gerenciar grupos financeiros.

Um household representa uma família ou grupo que compartilha transações.

Tudo no sistema financeiro acontece dentro de um household.

👥 MembersController
POST   /households/{householdId}/members
GET    /households/{householdId}/members
DELETE /households/{householdId}/members/{userId}

Responsabilidade:
Gerenciar membros de um household.

Permite adicionar, listar e remover participantes, além de controlar papéis (OWNER ou MEMBER).

🗂️ CategoriesController
POST   /households/{householdId}/categories
GET    /households/{householdId}/categories
PUT    /categories/{id}
DELETE /categories/{id}

Responsabilidade:
Gerenciar categorias financeiras.

Categorias classificam transações como:

INCOME (receita)

EXPENSE (despesa)

Cada household possui suas próprias categorias.

💳 TransactionsController
POST   /households/{householdId}/transactions
GET    /households/{householdId}/transactions
PUT    /transactions/{id}
DELETE /transactions/{id}

Responsabilidade:
Registrar movimentações financeiras reais.

É o núcleo do sistema:

Criação de receitas e despesas

Consulta com filtros

Atualização e exclusão

Toda transação pertence a um household.

🔁 RecurringController
POST   /households/{householdId}/recurring
GET    /households/{householdId}/recurring
PUT    /recurring/{id}
DELETE /recurring/{id}

Responsabilidade:
Gerenciar transações recorrentes.

Define regras automáticas como:

Salário mensal

Aluguel

Assinaturas

RecurringTransaction define a regra.
Transaction é a execução real gerada.

💰 BudgetsController
POST   /households/{householdId}/budgets
GET    /households/{householdId}/budgets

Responsabilidade:
Gerenciar orçamentos por categoria e período.

Permite controlar planejamento financeiro.

📊 DashboardController
GET /households/{householdId}/dashboard

Responsabilidade:
Fornecer resumo financeiro consolidado do household.

Normalmente retorna:

Total de receitas

Total de despesas

Saldo

Resumo por categoria

Usado na tela principal da aplicação.

🔔 NotificationsController
GET /notifications
PUT /notifications/{id}/read

Responsabilidade:
Gerenciar notificações do usuário.

Pode ser usado para:

Alertas de orçamento

Avisos importantes

Eventos do sistema

Funciona no nível do usuário.

🧠 Modelo Mental do Sistema
Usuário
  └── Households
        ├── Members
        ├── Categories
        ├── Transactions
        ├── Recurring
        ├── Budgets
        └── Dashboard

Tudo financeiro acontece dentro de um household.

🔐 Regras Fundamentais

Todo endpoint financeiro exige validação de membership

Categorias pertencem a um único household

Transações pertencem a um único household

Recorrências geram transações

Histórico financeiro não deve ser alterado retroativamente

🚀 Fluxo Básico de Uso

Criar usuário / Login

Criar ou entrar em um household

Criar categorias (se necessário)

Registrar transações

Criar recorrências (opcional)

Definir orçamentos

Consultar dashboard
