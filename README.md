# Recommendation Service

## Описание проекта

Recommendation Service — сервис банковских рекомендаций, разработанный на Java/Spring Boot.

Приложение анализирует информацию о пользователях, банковских продуктах и транзакциях, после чего формирует персональные рекомендации.

Проект поддерживает:

- статические правила рекомендаций;
- динамические правила, хранящиеся в PostgreSQL;
- получение рекомендаций через REST API;
- получение рекомендаций через Telegram-бота;
- сбор статистики срабатывания динамических правил;
- кеширование запросов к транзакционной базе данных;
- технологические endpoints для управления сервисом.

---

## Стек технологий

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring JDBC
- Hibernate
- PostgreSQL
- H2 Database
- Liquibase
- Caffeine Cache
- Telegram Bot API (Pengrad)
- Maven

---

## Архитектура проекта

Проект построен по многослойной архитектуре.

Основные слои:

- Controller
- Service
- Repository
- Entity
- DTO
- Rules
- Query Executors

---

## REST API

Основные endpoints:

| Метод | Endpoint | Назначение |
|--------|----------|------------|
| GET | /recommendation/{user_id} | Получение рекомендаций |
| POST | /rule | Создание динамического правила |
| GET | /rule | Получение списка правил |
| DELETE | /rule/{id} | Удаление правила |
| GET | /rule/stats | Получение статистики |
| POST | /management/clear-caches | Очистка кеша |
| GET | /management/info | Информация о сервисе |

---

## Telegram Bot

Поддерживаемые команды:

```
/start
```

```
/recommend username
```

---

## Сборка проекта

```bash
mvnw package
```

или

```bash
./mvnw package
```

---

## Запуск проекта

```bash
java -jar target/recommendation.jar
```

---

## Конфигурация

Перед запуском необходимо указать параметры подключения к базам данных и Telegram Bot.

Пример:

```
APPLICATION_RECOMMENDATIONS_DB_URL

RECOMMENDATION_DATASOURCE_URL

RECOMMENDATION_DATASOURCE_USERNAME

RECOMMENDATION_DATASOURCE_PASSWORD

TELEGRAM_BOT_TOKEN
```

---

## Документация

Документация проекта включает:

- User Stories
- Нефункциональные требования
- Диаграмму вариантов использования (Use Case)
- Диаграмму компонентов
- Activity Diagram
- OpenAPI
- Инструкцию по развертыванию

---

## Автор

Курсовой проект по Java / Spring Boot.