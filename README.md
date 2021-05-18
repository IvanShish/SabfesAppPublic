# SabfesApp
REST-API сервис для веб-приложения SabfesApp, который создан по подобию Trello.

**Использованные технологии**: Java 11, Spring Boot, JUnit, Lombok.

## Запросы

REST-API сервис расположен по следющему URL: https://sabfesapp.herokuapp.com/

#### Авторизация и регистрация

* **URL:** 
  /api/auth/signup 
  **Method:** 
  `Post` 
  **Body:** 
  `{"username", "password", "email", "role"}`
  **Validation:**
  Password size min = 6, max = 40; Username size max = 20; Email must be like real email. 
  Роли могут быть следующими: "user", "admin", "mod". Пример: `{"username":"add", "password":"addddd", "email":"add@mail.ru","role":["user"]}`. 
  **Success response:** 
  `code: 200, content: "message": "User registered successfully!"`.
* **URL:** 
  /api/auth/signin 
  **Method:** 
  `Post` 
  **Body:** 
  `{"username", "password"}`
  Пример: `{"username":"add", "password":"addddd"`.
  **Success response:** 
  `code: 200, content: {"token", "type", "id", "username", "email", "roles"}`.
