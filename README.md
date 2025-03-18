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
  
  P.S Для данного вида развертывания нужно заполнить пустые данные в <code>.env.dev</code> файле

### Отдельный запуск фронтенда (для разработки)
  1. Перейти в консоле  <code>../passenger-transportation-frontend </code>
  2. введите команду  <code>npm run dev </code>
  3. в случае ошибок связаных с незагруженными зависимостями введите команду  <code>npm install</code>

# Envorenment переменные
Для разработки присутвует <code>dev</code> конфигурация храниться в файле <code>.env.dev</code> находится в <code>/passenger-transportation-service/passanger-transportation-backend/src/main/resources/.env.dev</code>
<br>

Для продакшена <code>prod</code> храняться в файле <code>.env</code> находится в <code>/.env</code>

### База данных
 1. <code>POSTGRES_HOST</code> - хост до базы данных
 2. <code>POSTGRES_PORT</code> - порт базы данных
 3. <code>POSTGRES_PASSWORD</code> - пароль базы данных
 4. <code>POSTGRES_USERNAME</code> - имя пользователя базы данных
 5. <code>POSTGRES_DATABASE</code> - имя базы данных
 6. <code>SHOW_SQL</code> - просмотр sql в jpa может быть true или false
 7. <code>SQL_DRIVER</code> - драйвер jdbc
 8. <code>SQL_DIALECT</code> - диалект hibernate
### Logger
 1. <code>LOG_FILE_NAME</code> - имя файла для логов
 2. <code>LOG_MAX_FILE_SIZE</code> - максимальный размер для файла логов
 3. <code>LOG_LEVEL</code> - уровень логгирование
 4. <code>LOG_LEVEL_FILE</code> - уровень логгирование в файл
### Swagger
 1. <code>SWAGGER_API_PATH</code> - путь до docs swagger
 2. <code>SWAGGER_PATH</code> - путь до swagger
### Другие
 1. <code>SERVLET_PATH</code> - базовый путь ко всем энпдоинтам
 2. <code>PORT</code> - порт сервера
    
## P.S По тестовым данным
Даты в бд начинаються с 2023-06-01 07:00:00 и заканчиваються 2023-07-02 07:00:00


### Пояснение
Из-за моей ошибки после дедлайна были удалены все workflow runs 😓, что сделало невозможной демонстрацию конфигурации CI/CD. По просьбе организаторов я восстановил эту часть проекта.

Также в коммит включено исправление бага, которое было выполнено 16 марта. Поскольку на тот момент я считал, что работы уже не принимаются 😔, я не добавил исправление в репозиторий сразу. Поскольку организаторы разрешили внести изменения, я добавил исправление в этот коммит. 👍

Спасибо за понимание! 😊

