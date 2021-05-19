# SabfesApp
REST-API сервис для веб-приложения SabfesApp, который создан по подобию Trello.

## Запросы

REST-API сервис расположен по следющему URL: https://sabfesapp.herokuapp.com/

#### Авторизация и регистрация

* **URL:** 

  `/api/auth/signup` 
  
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

  `/api/auth/signin` 
  
  **Method:** 
  
  `Post` 
  
  **Body:** 
  
  `{"username", "password"}`
  
  Пример: `{"username":"add", "password":"addddd"`.
  
  **Success response:** 
  
  `code: 200, content: {"token", "type", "id", "username", "email", "roles"}`.
  
* **URL:** 

  `/api/auth` 
  
  **Method:** 
  
  `Get` 
  
  **Config:** 
  
  Must be authorized with `Bearer token`.
  
  **Success response:** 
  
  `code: 200, content: "id"`.
  
#### Доски
  
* **URL:** 

  `/api/board/:userId` 
  
  **Method:** 
  
  `Get` 
  
  **Config:** 
  
  Must be authorized with `Bearer token`.
  
  **Success response:** 
  
  `code: 200, content: "boards":[]`.
  
* **URL:** 

  `/api/board/getone/:boardId` 
  
  **Method:** 
  
  `Get` 
  
  **Config:** 
  
  Must be authorized with `Bearer token`.
  
  **Success response:** 
  
  `code: 200, content: "board":[]`.
  
* **URL:** 

  `/api/board/:userId` 
  
  **Method:** 
  
  `Post` 
  
  **Body:** 
  
  `{"boardName", "columns"}`
  
  Пример: `{"boardName":"newBoard", "columns":[]}`.
  
  **Config:** 
  
  Must be authorized with `Bearer token`.
  
  **Success response:** 
  
  `code: 200, content: "userId"`.
  
* **URL:** 

  `/api/board/:boardId` 
  
  **Method:** 
  
  `Put` 
  
  **Body:** 
  
  `{"boardName", "columns"}`
  
  Пример: `{"boardName":"newName"}`.
  
  **Config:** 
  
  Must be authorized with `Bearer token`.
  
  **Success response:** 
  
  `code: 200, content: "boardId"`.

* **URL:** 

  `/api/board/:boardId` 
  
  **Method:** 
  
  `Delete` 
  
  **Config:** 
  
  Must be authorized with `Bearer token`.
  
  **Success response:** 
  
  `code: 200, content: "boardId"`.
  
#### Колонки в досках
  
* **URL:** 

  `/api/board/col/:columnId` 
  
  **Method:** 
  
  `Get` 
  
  **Config:** 
  
  Must be authorized with `Bearer token`.
  
  **Success response:** 
  
  `code: 200, content: "columns":[]`.
  
* **URL:** 

  `/api/board/col/:boardId` 
  
  **Method:** 
  
  `Post` 
  
  **Body:** 
  
  `{"columnName", "tasks"}`
  
  Пример: `{"columnName":"newColumn", "tasks":[]}`.
  
  **Config:** 
  
  Must be authorized with `Bearer token`.
  
  **Success response:** 
  
  `code: 200, content: "boardId"`.
  
* **URL:** 

  `/api/board/col/:columnId` 
  
  **Method:** 
  
  `Put` 
  
  **Body:** 
  
  `{"columnName", "tasks"}`
  
  Пример: `{"columnName":"newName"}`.
  
  **Config:** 
  
  Must be authorized with `Bearer token`.
  
  **Success response:** 
  
  `code: 200, content: "columnId"`.

* **URL:** 

  `/api/board/col/:columnId` 
  
  **Method:** 
  
  `Delete` 
  
  **Config:** 
  
  Must be authorized with `Bearer token`.
  
  **Success response:** 
  
  `code: 200, content: "columnId"`.
  
#### Задания в колонках
  
* **URL:** 

  `/api/board/task/:taskId` 
  
  **Method:** 
  
  `Get` 
  
  **Config:** 
  
  Must be authorized with `Bearer token`.
  
  **Success response:** 
  
  `code: 200, content: "task"`.
  
* **URL:** 

  `/api/board/task/:columnId` 
  
  **Method:** 
  
  `Post` 
  
  **Body:** 
  
  `{"task"}`
  
  Пример: `{"task":"do something"}`.
  
  **Config:** 
  
  Must be authorized with `Bearer token`.
  
  **Success response:** 
  
  `code: 200, content: "columnId"`.
  
* **URL:** 

  `/api/board/task/:taskId` 
  
  **Method:** 
  
  `Put` 
  
  **Body:** 
  
  `{"task"}`
  
  Пример: `{"task":"new hard task"}`.
  
  **Config:** 
  
  Must be authorized with `Bearer token`.
  
  **Success response:** 
  
  `code: 200, content: "taskId"`.

* **URL:** 

  `/api/board/task/:taskId` 
  
  **Method:** 
  
  `Delete` 
  
  **Config:** 
  
  Must be authorized with `Bearer token`.
  
  **Success response:** 
  
  `code: 200, content: "taskId"`.
