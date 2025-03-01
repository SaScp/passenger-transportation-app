# Документация по запуску
###  Docker
Для запуска приложение в docker нужно:
  1. В командной строке зайти в директорию приложение:
  <code>../passanger-transortation-app</code>
  
  2. Ввести команду docker-compose up --bulid для сборки и запуска обоих образов(backend и frontend)

  3. Готово forntend будет доступен по адресу <code>http://localhost</code>,
     <br>
     
     swagger backend -a будет доступен по адресу  <code>http://localhost:8080/api/v1/booking/swagger/swagger-ui/index.html</code>
### Отдельный запуск бэкенда (для разработки)
  1. если вы работаете в Intelij Idea то в конфигурации запуска проекта поставить в поле <code>Active profiles</code> значение <code>dev</code> или <code>prod</code> 
  2. если запуск производиться из консоли, то при запуске проекта указать в качестве параметра  <code>spring.profiles.active</code> значение <code>dev</code> или <code>prod</code>
# envorenment переменные
Для разработки присутвует dev конфигурация храняться в файле <code>.env.dev</code> находится в <code>/passenger-transportation-service/passanger-transportation-backend/src/main/resources/.env.dev</code>
<br>

Для продакшена prod храняться в файле <code>.env</code> находится в <code>/.env</code>

### База данных
 1. <code>SQL_URL</code> - путь до базы данных
 2. <code>SQL_PASSWORD</code> - пароль базы данных
 3. <code>SQL_USERNAME</code> - имя пользователя базы данных
 4. <code>SHOW_SQL</code> - просмотр sql в jpa может быть true или false
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
 3. <code>LRU_CACHE_SIZE</code> - размер lru кэша
