# Sistema de Gestão Logística e E-Commerce — JPA/Hibernate

Projeto integrador do Módulo 12, organizado conforme as cinco fases do roteiro.

## Tecnologias
- Java 11 ou superior
- Maven
- JPA 2.2 (`javax.persistence`)
- Hibernate 5.6.15.Final
- PostgreSQL

## Preparação do banco
Crie o banco no PostgreSQL:

```sql
CREATE DATABASE bdecommerce;
```

Edite `src/main/resources/META-INF/persistence.xml` e substitua `SUA_SENHA` pela senha local do usuário `postgres`. Não envie credenciais reais ao GitHub.

## Importação no Eclipse
1. Extraia o ZIP.
2. Acesse **File > Import > Maven > Existing Maven Projects**.
3. Selecione a pasta `ecommerce-jpa`.
4. Finalize e aguarde o download das dependências.
5. Se necessário, use **Maven > Update Project**.
6. Execute as classes pelo menu **Run As > Java Application**.

## Aplicações incluídas

| Classe | O que demonstra |
|---|---|
| `TesteSetupJpaApp` | Inicialização da persistence-unit e EntityManager |
| `CicloVidaJpaApp` | Transient, Managed, Detached, merge, refresh e Removed |
| `AppRelacionamentosJpa` | One-to-one, one-to-many, map indexado e many-to-many |
| `AppHerancaJpa` | Herança JOINED e consulta polimórfica |
| `AppConsultasJpql` | Named queries, TypedQuery, parâmetros e UPDATE em massa |

## Observações importantes
- As aplicações de demonstração podem inserir registros no banco. `CicloVidaJpaApp` remove o produto de teste que ele próprio cria.
- `AppRelacionamentosJpa` e `AppHerancaJpa` usam códigos de exemplo fixos. Execute cada aplicação uma vez em um banco limpo ou altere os códigos antes de repetir, pois os campos de código/rastreio possuem restrição de unicidade.
- O `UPDATE` JPQL em massa não atualiza automaticamente objetos que já estejam no contexto de persistência; por isso o DAO chama `em.clear()` após a execução.
- O reajuste por categoria é chamado dentro de uma transação em `AppConsultasJpql`.
- `hbm2ddl.auto=update` está configurado para fins didáticos, conforme o roteiro.

## Consultas úteis no pgAdmin

```sql
SELECT * FROM PRODUTO_JPA ORDER BY nome;
SELECT * FROM CATEGORIA;
SELECT * FROM PEDIDO_JPA;
SELECT * FROM ITEM_PEDIDO_ASSOCIACAO;
SELECT * FROM FRETE;
SELECT * FROM FRETE_AEREO;
SELECT * FROM FRETE_RODOVIARIO;
```
