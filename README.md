# Wallet Service

Приложение для управления кошельками через REST API.  
Позволяет создавать кошельки, проверять баланс и проводить операции (депозит/снятие).

## Цель
Данное тестовое задание предназначено для проверки навыков разработки на Java с использованием Spring Boot, PostgreSQL, Docker и Liquibase.  
Приложение должно корректно работать при высокой нагрузке (1000 RPS на один кошелек) и обрабатывать ошибки.

## Стек технологий
- Java 17
- Spring Boot 3
- PostgreSQL
- Liquibase
- Docker / Docker Compose

## Запуск

1. Создать `.env` файл (если нужно) и задать переменные:

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/walletdb
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
SPRING_LIQUIBASE_ENABLED=true
SERVER_PORT=8080
```

2. Запустить через Docker Compose:

```env
docker-compose up --build
```

3. Сервис будет доступен на http://localhost:8080/api/v1

## API

1️⃣ Создание кошелька

- Метод: POST
- URL: /api/v1/wallet/create?balance=1000
- Параметр: balance через query param
- Ответ: UUID нового кошелька

Пример ответа:
```env
"6e1c990e-e4bd-4113-86dd-b85c482f85bc"
```
2️⃣ Получение баланса

- Метод: GET
- URL: /api/v1/wallet/{walletId}

Ответ:
```env
{
  "walletId": "6e1c990e-e4bd-4113-86dd-b85c482f85bc",
  "balance": 1000
}
```
3️⃣ Операции с кошельком

- Метод: POST
- URL: /api/v1/wallet

Body:
```env
{
  "walletId": "6e1c990e-e4bd-4113-86dd-b85c482f85bc",
  "operationType": "DEPOSIT",
  "amount": 500
}
```
- Поддерживаемые операции: DEPOSIT, WITHDRAW
- Ошибки:
  - Несуществующий кошелек → WALLET_NOT_FOUND
  - Недостаточно средств → INSUFFICIENT_FUNDS
  - Некорректный JSON → INTERNAL_ERROR

## Особенности

- Конкурентная безопасность операций по кошельку через SQL update с проверкой баланса
- Все эндпоинты покрыты тестами (unit и integration)
- Liquibase управляет миграциями базы данных
- Конфигурация приложения и БД настраивается через переменные окружения

## Docker
- wallet-service — сервис на Spring Boot
- db — PostgreSQL
  
Пример команд:
```env
docker-compose up --build
docker-compose down
```
