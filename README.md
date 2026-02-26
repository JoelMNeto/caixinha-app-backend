<h1>💰 Household Finance API</h1>

<p>
API REST para controle financeiro compartilhado por grupos (<strong>households</strong>).
</p>

<h2>🌐 Base URL</h2>

<pre><code>/api/v1</code></pre>

<p><strong>Autenticação:</strong> JWT obrigatório (exceto login/registro).</p>

<hr/>

<h1>📦 Controllers e Endpoints</h1>

<hr/>

<h2>👤 UsersController</h2>

<pre><code>
POST   /users
POST   /auth/login
GET    /users/me
PUT    /users/me
</code></pre>

<h3>Responsabilidade</h3>

<ul>
  <li>Gerenciar usuários</li>
  <li>Autenticação (login)</li>
  <li>Atualização de perfil</li>
</ul>

<p>
Este controller cuida apenas de <strong>identidade e perfil</strong>.
Não lida com dados financeiros.
</p>

<hr/>

<h2>🏠 HouseholdsController</h2>

<pre><code>
POST   /households
GET    /households
GET    /households/{id}
PUT    /households/{id}
DELETE /households/{id}
</code></pre>

<h3>Responsabilidade</h3>

<ul>
  <li>Gerenciar grupos financeiros</li>
  <li>Criar, listar, atualizar e remover households</li>
</ul>

<p>
Um <strong>household</strong> representa uma família ou grupo que compartilha transações.
</p>

<p>
<strong>Importante:</strong> tudo no sistema financeiro acontece dentro de um household.
</p>

<hr/>

<h2>👥 MembersController</h2>

<pre><code>
POST   /households/{householdId}/members
GET    /households/{householdId}/members
DELETE /households/{householdId}/members/{userId}
</code></pre>

<h3>Responsabilidade</h3>

<ul>
  <li>Adicionar membros ao household</li>
  <li>Listar participantes</li>
  <li>Remover membros</li>
  <li>Controlar papéis (OWNER ou MEMBER)</li>
</ul>

<hr/>

<h2>🗂️ CategoriesController</h2>

<pre><code>
POST   /households/{householdId}/categories
GET    /households/{householdId}/categories
PUT    /categories/{id}
DELETE /categories/{id}
</code></pre>

<h3>Responsabilidade</h3>

<ul>
  <li>Gerenciar categorias financeiras</li>
</ul>

<p>Categorias classificam transações como:</p>

<ul>
  <li><strong>INCOME</strong> (receita)</li>
  <li><strong>EXPENSE</strong> (despesa)</li>
</ul>

<p>
Cada household possui suas próprias categorias.
</p>

<hr/>

<h2>💳 TransactionsController</h2>

<pre><code>
POST   /households/{householdId}/transactions
GET    /households/{householdId}/transactions
PUT    /transactions/{id}
DELETE /transactions/{id}
</code></pre>

<h3>Responsabilidade</h3>

<ul>
  <li>Registrar movimentações financeiras reais</li>
  <li>Criar receitas e despesas</li>
  <li>Consultar com filtros</li>
  <li>Atualizar e excluir transações</li>
</ul>

<p>
Este é o <strong>núcleo do sistema</strong>.
</p>

<p>
Toda transação pertence a um único household.
</p>

<hr/>

<h2>🔁 RecurringController</h2>

<pre><code>
POST   /households/{householdId}/recurring
GET    /households/{householdId}/recurring
PUT    /recurring/{id}
DELETE /recurring/{id}
</code></pre>

<h3>Responsabilidade</h3>

<ul>
  <li>Gerenciar transações recorrentes</li>
</ul>

<p>Define regras automáticas como:</p>

<ul>
  <li>Salário mensal</li>
  <li>Aluguel</li>
  <li>Assinaturas</li>
</ul>

<p>
<strong>RecurringTransaction</strong> define a regra.<br/>
<strong>Transaction</strong> é a execução real gerada.
</p>

<hr/>

<h2>💰 BudgetsController</h2>

<pre><code>
POST   /households/{householdId}/budgets
GET    /households/{householdId}/budgets
</code></pre>

<h3>Responsabilidade</h3>

<ul>
  <li>Gerenciar orçamentos por categoria e período</li>
  <li>Controlar planejamento financeiro</li>
</ul>

<hr/>

<h2>📊 DashboardController</h2>

<pre><code>
GET /households/{householdId}/dashboard
</code></pre>

<h3>Responsabilidade</h3>

<ul>
  <li>Fornecer resumo financeiro consolidado</li>
</ul>

<p>Normalmente retorna:</p>

<ul>
  <li>Total de receitas</li>
  <li>Total de despesas</li>
  <li>Saldo</li>
  <li>Resumo por categoria</li>
</ul>

<p>
Usado na tela principal da aplicação.
</p>

<hr/>

<h2>🔔 NotificationsController</h2>

<pre><code>
GET /notifications
PUT /notifications/{id}/read
</code></pre>

<h3>Responsabilidade</h3>

<ul>
  <li>Gerenciar notificações do usuário</li>
</ul>

<p>Pode ser usado para:</p>

<ul>
  <li>Alertas de orçamento</li>
  <li>Avisos importantes</li>
  <li>Eventos do sistema</li>
</ul>

<p>
Funciona no nível do usuário.
</p>

<hr/>

<h1>🧠 Modelo Mental do Sistema</h1>

<pre><code>
Usuário
  └── Households
        ├── Members
        ├── Categories
        ├── Transactions
        ├── Recurring
        ├── Budgets
        └── Dashboard
</code></pre>

<p>
<strong>Tudo financeiro acontece dentro de um household.</strong>
</p>

<hr/>

<h1>🔐 Regras Fundamentais</h1>

<ul>
  <li>Todo endpoint financeiro exige validação de membership</li>
  <li>Categorias pertencem a um único household</li>
  <li>Transações pertencem a um único household</li>
  <li>Recorrências geram transações</li>
  <li>Histórico financeiro não deve ser alterado retroativamente</li>
</ul>

<hr/>

<h1>🚀 Fluxo Básico de Uso</h1>

<ol>
  <li>Criar usuário / Login</li>
  <li>Criar ou entrar em um household</li>
  <li>Criar categorias (se necessário)</li>
  <li>Registrar transações</li>
  <li>Criar recorrências (opcional)</li>
  <li>Definir orçamentos</li>
  <li>Consultar dashboard</li>
</ol>

<hr/>

<p align="center">
  <strong>Household Finance API</strong><br/>
  Controle financeiro compartilhado de forma simples e estruturada.
</p>
