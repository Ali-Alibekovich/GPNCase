# GPNCase
 
### Выходной частью сервиса-адаптера должен быть REST-интерфейс, который принимает значения для передачи в SOAP веб-сервис.
##### Сервис источник: <a href="http://www.dneonline.com/calculator.asmx">calculator.asmx</a>
#### Необходимо предусмотреть валидацию запросов к REST-сервису на предмет их наличия, корректности формата и т.п.
#### Будет плюсом, если у реализованного сервиса будет спецификация в формате OpenAPI (SWAGGER), которая генерируется автоматически из кода.
#### Код должен работать с системой контроля версий GIT.
#### Фреймворки и инструменты, которые помогут реализовать задачу:
#### Spring, Spring Boot, Apache CXF, Apache Axis, Jackson, GSON, Apache Camel
#### Минимальный решение - синхронно работающий рест сервис, преобразующий SOAP в REST запросы.
#### Максимальный решение - запросы кешируются в распределенном кеше, т.е. сначала ищется есть ли в кеше уже аналогичный запрос на расчет с результатом,
#### и если нет, то идет обращение в SOAP калькулятор, и результат отдается потребителю, а также складывается в кеш для последующих запросов.


## Запуск
#### Приложение разделено на 2 точки запуска: <a href="https://github.com/Ali-Alibekovich/GPNCase/blob/main/src/main/java/com/example/task/hazelcast/Server.java">Hazelcast Server</a> для распределенного кеша и основное приложение <a href="https://github.com/Ali-Alibekovich/GPNCase/blob/main/src/main/java/com/example/task/TaskApplication.java">Spring Application</a>
##### <a href="https://github.com/Ali-Alibekovich/GPNCase/blob/main/src/main/java/com/example/task/TaskApplication.java">Spring Application</a> может принимать 2 аргумента - хост и порт для подключения к Hazelcast Server (по умолчанию "127.0.0.1","5701"). (порядок ввода : хост, порт)
##### <a href="https://github.com/Ali-Alibekovich/GPNCase/blob/main/src/main/java/com/example/task/hazelcast/Server.java">Hazelcast Server</a> по умолчанию работает на 127.0.0.1, но публичный хост или порт можно задать и через параметры запуска. (порядок ввода : хост, порт)

