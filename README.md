# Документация по запуску
###  Docker
Для запуска приложение в docker нужно:
  1. В командной строке зайти в директорию приложение:
  <code>../passanger-transortation-app</code>
  
  2. Ввести команду <code>docker-compose up --build</code> для сборки и запуска обоих образов(backend и frontend)

  3. Готово forntend будет доступен по адресу <code>http://localhost</code>,
     <br>
     
     swagger backend -a будет доступен по адресу  <code>http://localhost:8080/api/v1/booking/swagger/swagger-ui/index.html</code>
### Отдельный запуск бэкенда (для разработки)
  1. если вы работаете в Intelij Idea то в конфигурации запуска проекта поставить в поле <code>Active profiles</code> значение <code>dev</code> или <code>prod</code> 
  2. если запуск производиться из консоли, то
      1. Перейти в консоле <code>../passenger-transportation-app/passenger-transportation-service</code>
      2. выполнить <code>mvn clean package</code> если будут появляться ошибки тестов, то нужно ввести команду <code>mvn install -X</code> в <code>../passenger-transportation-app</code>
      3. найти сгенерированый jar файл (он находиться в по пути: <code>../passenger-transportation-app/passenger-transportation-service/passanger-transportation-backend/target/</code>)
      4. запустить приложение командой  <code>java -jar ../passenger-transportation-app/passenger-transportation-service/passanger-transportation-backend/target/{название файла}.jar</code>
      5.  при запуске проекта указать в качестве параметра  <code>spring.profiles.active</code> значение <code>dev</code> или <code>prod</code> (это можно сделать добавив <code>--spring.profiles.active={профиль}</code>)
  
  > **P.S.** Для данного вида развертывания нужно заполнить пустые данные в <code>.env.dev</code> файле

### Отдельный запуск фронтенда (для разработки)
  1. Перейти в консоле  <code>../passenger-transportation-frontend </code>
  2. введите команду  <code>npm run dev </code>
  3. в случае ошибок связаных с незагруженными зависимостями введите команду  <code>npm install</code>

# Envorenment переменные
Для разработки присутвует <code>dev</code> конфигурация храниться в файле <code>.env.dev</code> находится в <code>/passenger-transportation-service/passanger-transportation-backend/src/main/resources/.env.dev</code>
<br>

Для продакшена <code>prod</code> храняться в файле <code>.env</code> находится в <code>/.env</code>

### База данных
- **POSTGRES_HOST** – хост базы данных
- **POSTGRES_PORT** – порт базы данных
- **POSTGRES_PASSWORD** – пароль базы данных
- **POSTGRES_USERNAME** – имя пользователя базы данных
- **POSTGRES_DATABASE** – имя базы данных
- **SHOW_SQL** – отображение SQL-запросов в JPA (`true`/`false`)
- **SQL_DRIVER** – драйвер JDBC
- **SQL_DIALECT** – диалект Hibernate

### Логирование
- **LOG_FILE_NAME** – имя файла для логов
- **LOG_MAX_FILE_SIZE** – максимальный размер файла логов
- **LOG_LEVEL** – уровень логирования
- **LOG_LEVEL_FILE** – уровень логирования в файл

### Swagger
- **SWAGGER_API_PATH** – путь до документации Swagger
- **SWAGGER_PATH** – путь до Swagger

### Прочие параметры
- **SERVLET_PATH** – базовый путь ко всем эндпоинтам
- **PORT** – порт сервера

    
> **P.S.** По тестовым данным
Даты в бд начинаються с 2023-06-01 07:00:00 и заканчиваються 2023-07-02 07:00:00

# Документация API 

 при запуске через docker-compose
 ```
 http://localhost:8080/api/v1/booking/swagger/swagger-ui/index.html
```
 при обычном запуске 
 ```
 http://localhost:9000/dev/api/v1/booking/swagger/swagger-ui/index.html
```
# Доработки и улучшения

## 1. Оптимизация алгоритма поиска маршрутов

- **Что сделано:**  
Оптимизирован алгоритм поиска маршрутов, добавлены новые индексы для ускорения поиска.

- **Пример запроса:**

![Пример запроса](https://github.com/user-attachments/assets/2a0b4e9a-d363-4480-871c-365bd98f62fb)

- **Сравнение:**

- **До:**

  ![До оптимизации](https://github.com/user-attachments/assets/060a10a5-5ee1-42e3-a270-b0b38b99b6ef)

- **После:**

  ![После оптимизации](https://github.com/user-attachments/assets/919f5eb2-0284-41e3-94ea-62edba3801e0)

---

## 2. Добавление Redis

- Внедрение Redis позволяет значительно повысить производительность за счёт кэширования данных и уменьшения времени отклика.

---

## 3. Кастомизированный пул потоков для асинхронной обработки

- Позволяет большему количеству пользователей одновременно работать с системой, улучшая масштабируемость и общую отзывчивость приложения.

---

## 4. Нагрузочное тестирование системы

### Графики нагрузочного тестирования сервиса

- **С Асинхронностью и кэшом:**

![Graph Results](https://github.com/user-attachments/assets/e31cf64f-f8fd-416c-84a6-c17ae1a439d4)

- **Без асинхронности и кэша:**

![Graph Results2](https://github.com/user-attachments/assets/de59ddeb-925e-4ade-be2d-0c0c106226e5)

### Таблица результатов тестирования

| **Параметр**                         | **С Асинхронностью и кэшом**            | **Без асинхронности и кэша**           |
|--------------------------------------|-----------------------------------|----------------------------------|
| **Количество образцов**              | 40,077                            | 30,616                           |
| **Последний образец**                | 943                               | 5,454                            |
| **Среднее значение**                 | 2,114 ms                          | 4,408 ms                         |
| **Медиана**                          | 2,095 ms                          | 2,343 ms                         |
| **Отклонение**                       | 564                            | 5,930                        |
| **Пропускная способность**           | 12,981.596 запросов/мин           | 2,337.914 запросов/мин           |
| **Максимальное значение (max Y)**    | 3,312 ms                          | 10,039 ms                        |

>  Тестирование проводилось с 92я вершинами и 450ю ребрами графа 
