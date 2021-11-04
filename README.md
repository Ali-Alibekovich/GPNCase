# GPNCase
 
<h3> Выходной частью сервиса-адаптера должен быть REST-интерфейс, который принимает значения для передачи в SOAP веб-сервис.
<h4> Сервис источник: <a href="http://www.dneonline.com/calculator.asmx">calculator.asmx</a></h4>
<h4> Необходимо предусмотреть валидацию запросов к REST-сервису на предмет их наличия, корректности формата и т.п.</h4>
<h4> Будет плюсом, если у реализованного сервиса будет спецификация в формате OpenAPI (SWAGGER), которая генерируется автоматически из кода.</h4>
<h4> Код должен работать с системой контроля версий GIT.</h4>
<h4> Фреймворки и инструменты, которые помогут реализовать задачу:</h4>
<h4> Spring, Spring Boot, Apache CXF, Apache Axis, Jackson, GSON, Apache Camel</h4>
<h4> Минимальный решение - синхронно работающий рест сервис, преобразующий SOAP в REST запросы.</h4>
<h4> Максимальный решение - запросы кешируются в распределенном кеше, т.е. сначала ищется есть ли в кеше уже аналогичный запрос на расчет с результатом,</h4>
<h4> и если нет, то идет обращение в SOAP калькулятор, и результат отдается потребителю, а также складывается в кеш для последующих запросов.</h4>

## Использованные технологии
<h4>Spring, Spring boot, Jakson, Apache CXF, Swagger2, HazelCast. </h4> 
 
## Запуск
<h4> Приложение разделено на 2 точки запуска: <a href="https://github.com/Ali-Alibekovich/GPNCase/blob/main/src/main/java/com/example/task/hazelcast/Server.java">Hazelcast Server</a> для распределенного кеша и основное приложение <a href="https://github.com/Ali-Alibekovich/GPNCase/blob/main/src/main/java/com/example/task/TaskApplication.java">Spring Application</a></h4>
<h4> <a href="https://github.com/Ali-Alibekovich/GPNCase/blob/main/src/main/java/com/example/task/TaskApplication.java">Spring Application</a> может принимать 2 аргумента - хост и порт для подключения к Hazelcast Server (по умолчанию "127.0.0.1","5701"). (порядок ввода : хост, порт)</h4>
<h4> <a href="https://github.com/Ali-Alibekovich/GPNCase/blob/main/src/main/java/com/example/task/hazelcast/Server.java">Hazelcast Server</a> по умолчанию работает на 127.0.0.1, но публичный хост или порт можно задать и через параметры запуска. (порядок ввода : хост, порт)</h4>

# Монтиторинг Clinets и Members

<h4>По <a href="https://hazelcast.com/open-source-projects/downloads/#management-center">ссылке</a> можно скачать Hazelcast Management Center zip. Запустив /bin/start.sh(bat) можно мониторить клиентов и member'ов. По умолчанию hazelcast management center запускается на localhost:8080, но можно 1 аргументом исполняемого файла указать порт.</h4>
