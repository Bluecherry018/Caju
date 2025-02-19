# Autorizador Caju

Desafio técnico, tendo 4 etapas nas quais foram aplicadas no projeto.

## Descrição

O desafio é composto por 4 etapas:

* L1. Autorizador simples
* L2. Autorizador com fallback
* L3. Dependente do comerciante
* L4. Questão aberta

## Estrutura do Projeto

| Path                                                                   | Descrição                                                                        |
| ------------------------------------------------------------------------|------------------------------------------------------------------------------------ |
| src/main/kotlin/model/Account.kt                                                | Classes usadas para o projeto                                                 |
| src/main/kotlin/model/AccountRepository.kt                                      | Declaração das funções utilizadas                    |
| src/main/kotlin/model/FakeAccountRepository.kt                                  | Implementação das funções |
| src/main/kotlin/Application.kt                                                  | Main da aplicação                                         |
| src/main/kotlin/Databases.kt                                                    | Configuração do Banco de Dados (MySQL)                                         |
| src/main/kotlin/Serialization.kt                                                | Rotas da API                                             |
| src/test/kotlin/ApplicationTest.kt                                              | Testes da API                                         |
| src/main/resources/static/index.html                                            | Front da API                                        |


## Rodando o Projeto

| Comando                        | Descrição                                                         |
| ------------------------------|---------------------------------------------------------------------- |
| `./gradlew test`              | Roda dos testes                                                        |
| `./gradlew build`             | Build do projeto                                                     |
| `./gradlew run`               | Rodar localmente o projeto |
| `docker compose up --build`   | Iniciar e construir seus containers(caso rode o docker)                     |
| `docker compose down`         | Parar e remover todos os containers(caso já tenha rodado a aplicação)                                     |

## Acessando a aplicação na web

Caso queira acessar Front da aplicação vá no arquivo index.html(Lembre de rodar a aplicação, para acessar o index.html)

<img width="1375" alt="Screenshot 2025-02-18 at 17 27 50" src="https://github.com/user-attachments/assets/f97a3303-17fd-4e59-8fcc-680e1278c397" />

## Rotas da API

| Rota                       | Descrição                                                         |
| ------------------------------|---------------------------------------------------------------------- |
| POST `/authorize`              | Autorização                                                        |
| GET `/balance`             | Saldo da Carteira                                                    |
| GET `/transaction/{id}`               | Buscar transação pelo id |

## Fazendo request na API

Para realizar os requests foi utilizado Postman

<img width="841" alt="Screenshot 2025-02-18 at 17 32 50" src="https://github.com/user-attachments/assets/9abe98a2-7ca8-4525-864f-b53493fdc1b0" />

Lembrando o a URL é <http://localhost:8080>

## Questão L4
 
Transações simultâneas: dado que o mesmo cartão de crédito pode ser utilizado em diferentes serviços online, existe uma pequena mas existente probabilidade de ocorrerem duas transações ao mesmo tempo. O que você faria para garantir que apenas uma transação por conta fosse processada em um determinado momento? Esteja ciente do fato de que todas as solicitações de transação são síncronas e devem ser processadas rapidamente (menos de 100 ms), ou a transação atingirá o timeout.

### Resposta:

Acho que a maneira mais eficaz seria usar Kafka para message queue, pois as transações entrariam em um fila e não de maneira direta, assim as transições são processadas de maneira sequencial. Tem a opção de usar Locks em banco de dados, mas acho que traria sobrecarga no banco, então o uso de kafka seria mais viável, pois é uma solução mais escalável.
