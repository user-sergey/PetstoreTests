**Проект содержит автотесты для сервиса petstore (https://petstore.swagger.io/).**

Стек: Kotlin, JUnit.

**Запуск тестов:**
1. Открыть командную строку и клонировать проект:
```bash
git clone https://github.com/user-sergey/PetstoreTests.git path
```

Замените `path` на путь, куда произойдёт клонирование.

**ВАЖНО!!! ИСПОЛЬЗОВАНИЕ РУССКИХ СИМВОЛОВ В ПУТИ МОЖЕТ ПОМЕШАТЬ ЗАПУСКУ ТЕСТОВ!**  
Возможные корректные образцы:
```bash
C:\Users\Anatoliy\Downloads\PetstoreTests
```
```bash
C:\Users\Veronika\IdeaProjects\PetstoreTests`
```

2. Перейти к проекту:
```bash
cd path
```

`path` такой же, какой был прописан Вами в пункте 1.

3. Выполнить команду
```bash
gradlew.bat clean test
```

**ИЛИ**

1. Загрузить проект и извлечь архив.
2. Открыть проект в IntelliJ IDEA.
3. Открыть терминал (Alt+F12 или View - Tool Windows - Terminal) и прописать:
```bash
./gradlew test
```

---

Файл со всеми тестами расположен здесь: [`src/test/kotlin/ApiTests.kt`](src/test/kotlin/ApiTests.kt)