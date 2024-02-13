SHIFT_Test_Task

Тестовое задание для ЦФТ:

Language:
Java version: 21

Assembly system:
Apache Maven 3.9.4

Libraries:
1. Apache Commons Lang - 3.14.0
   
https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.14.0</version>
        </dependency>

2. Apache Commons CLI - 1.6.0

https://mvnrepository.com/artifact/commons-cli/commons-cli
        <dependency>
            <groupId>commons-cli</groupId>
            <artifactId>commons-cli</artifactId>
            <version>1.6.0</version>
        </dependency>
   
Сборка проекта:
mvn package

Инструкция к запуску проекта:
java -jar SHIFT_Test_Task-1.0-SNAPSHOT-jar-with-dependencies.jar (опции) (исходные файлы)

Опции запуска:
-o - путь для сохранения результата работы программы
-p (prefix) - задаёт префикс для файлов с результатом
-a - если есть данная опция, записи добавляются в файлы с результатом, по умолчанию перезаписываются
-s - вывод короткой статистики в консоль после окончания работы
-f - вывод полной статистики в консоль после окончания работы
-h - все опции утилиты

