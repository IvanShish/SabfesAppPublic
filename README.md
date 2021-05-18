# SabfesApp
REST-API сервис для веб-приложения SabfesApp, который создан по подобию Trello.

*Использованные технологии*: Java 11, Spring Boot, JUnit, Lombok.

## Запросы

REST-API сервис расположен по следющему URL: https://sabfesapp.herokuapp.com/

#### Авторизация и регистрация

- URL: /api/auth/signup. Method: Post. Body: `{"username", "password", "email", "role"}`. Роли могут быть следующими: "user", "admin", "mod". Пример: `{"username":"add", "password":"add", "email":"add@mail.ru","role":["user"]}`. Success response: `code: 200, content: "id": someId`.
