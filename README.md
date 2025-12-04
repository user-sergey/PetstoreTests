**Проект содержит автотесты для сервиса petstore (https://petstore.swagger.io/).**

Стек: Kotlin, JUnit.

**Запуск тестов:**
1. Открыть командную строку и клонировать проект:

`git clone https://github.com/user-sergey/PetstoreTests.git path`

Вместо `path` - путь, куда произойдёт клонирование.

**ВАЖНО!**  
Использование русских символов в пути может помешать запуску тестов!  
Возможные корректные образцы:

`C:\Users\Anatoliy\Downloads\PetstoreTests`

`C:\Users\Veronika\IdeaProjects\PetstoreTests`

2. Перейти к проекту:

`cd path`

`path` такой же, какой был прописан Вами в пункте 1.

3. Выполнить команду

`gradlew.bat clean test`

**ИЛИ**

1. Загрузить проект и извлечь архив.
2. Открыть проект в IntelliJ IDEA.
3. Открыть терминал (Alt+F12 или View - Tool Windows - Terminal) и прописать:

`./gradlew test`

---

Файл со всеми тестами расположен здесь: `src/test/kotlin/ApiTests.kt`