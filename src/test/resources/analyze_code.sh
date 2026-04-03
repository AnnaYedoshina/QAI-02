#!/bin/bash

# Скрипт для анализа кода проекта с использованием Gemini API.
#
# Инструкция:
# 1. Замените "YOUR_API_KEY" на ваш реальный API ключ.
# 2. Сделайте скрипт исполняемым: chmod +x analyze_code.sh
# 3. Запустите скрипт: ./analyze_code.sh
#    Для использования своего промпта, передайте его в кавычках:
#    ./analyze_code.sh

API_KEY="AIzaSyBaTxC780RgT3PHuVecC8z7Fibp9BpBfvc"
MODEL="gemini-2.5-flash"
API_URL="https://generativelanguage.googleapis.com/v1beta/models/${MODEL}:generateContent?key=${API_KEY}"

# Промпт для анализа
DEFAULT_PROMPT='Вы — опытный QA-автоматизатор с глубокой экспертизой в Java и автоматизации тестирования. Ваша цель — провести детальный code review предоставленного Java-кода для повышения его качества и поддерживаемости.

Проведите анализ по следующим критериям, следуя указанному формату для каждого найденного пункта:

### 1. Надёжность и стабильность тестов
- Критерии: наличие flaky-тестов, race conditions, зависимостей между тестами, неустойчивых ожиданий (hard waits, polling).
- Формат ответа:
  - **Проблема:** [Описание проблемы]
  - **Фрагмент кода:** ```java [строки кода] ```
  - **Почему проблема:** [Объяснение, почему это является проблемой]
  - **Рекомендация:** [Конкретная рекомендация по исправлению с примером кода, где применимо]

### 2. Ассерты и проверяемость результата
- Критерии: полнота и корректность assertions, читаемость сообщений об ошибках, покрытие граничных случаев.
- Формат ответа: [аналогично пункту 1]

### 3. Читаемость и намерение кода
- Критерии: ясность названий тестов и методов, соответствие принципу "тест как документация", структура Arrange-Act-Assert.
- Формат ответа: [аналогично пункту 1]

### 4. Хардкод и тестовые данные
- Критерии: наличие захардкоженных значений, магических чисел, credentials, URL; возможность параметризации.
- Формат ответа: [аналогично пункту 1]

### 5. Повторяемость
- Критерии: изоляция тестов, управление состоянием, зависимость от внешних систем или порядка выполнения.
- Формат ответа: [аналогично пункту 1]

### 6. Архитектура тестов
- Критерии: применение Page Object, фабрик, хелперов, дублирование кода, соответствие принципам SOLID и DRY.
- Формат ответа: [аналогично пункту 1]

### 7. Потенциальные ошибки и риски
- Критерии: NPE, неверные предположения, пропущенные edge cases, небезопасные операции.
- Формат ответа: [аналогично пункту 1]

### 8. Соответствие лучшим практикам
- Критерии: использование аннотаций JUnit/TestNG, setUp/tearDown, правильный жизненный цикл тестовых объектов.
- Формат ответа: [аналогично пункту 1]

В конце предоставьте **краткое резюме с приоритизированным списком наиболее критичных улучшений**.

Весь анализ предоставьте на русском языке.'
PROMPT="${1:-$DEFAULT_PROMPT}"

# Экранирование PROMPT для JSON
ESCAPED_PROMPT_CHARS=$(echo "$PROMPT" | sed 's/\\/\\\\/g' | sed 's/"/\\"/g')
PROMPT_ESCAPED_JSON=$(echo "$ESCAPED_PROMPT_CHARS" | awk '{ if (NR > 1) printf "\\n"; printf "%s", $0 }')

# Файлы для анализа
FILES=(
    "BaseTest.java"
    "FullE2EScenarioTest.java"
)

# Директория для сохранения результатов
OUTPUT_DIR="gemini_analysis"
mkdir -p "$OUTPUT_DIR"

# Цикл по файлам для анализа
for FILE_PATH in "${FILES[@]}"; do
    if [ ! -f "$FILE_PATH" ]; then
        echo "Файл не найден: $FILE_PATH"
        continue
    fi

    echo "Анализ файла: $FILE_PATH"

    # Чтение содержимого файла
    FILE_CONTENT=$(cat "$FILE_PATH")

    # Экранирование JSON-спецсимволов
    # 1. Экранируем обратные слеши и двойные кавычки
    ESCAPED_CHARS=$(echo "$FILE_CONTENT" | sed 's/\\/\\\\/g' | sed 's/"/\\"/g')
    # 2. Экранируем новые строки (заменяем фактические newlines на '\n')
    JSON_ESCAPED_CONTENT=$(echo "$ESCAPED_CHARS" | awk '{ if (NR > 1) printf "\\n"; printf "%s", $0 }')

    # Формирование JSON-запроса
    JSON_PAYLOAD=$(cat <<EOF
{
    "contents": [{
        "parts": [{
            "text": "${PROMPT_ESCAPED_JSON}\\n\\n${JSON_ESCAPED_CONTENT}"
        }]
    }],
    "generationConfig": {
        "temperature": 0.2,
        "topK": 1,
        "topP": 1,
        "maxOutputTokens": 8192,
        "stopSequences": []
    },
    "safetySettings": [
        { "category": "HARM_CATEGORY_HARASSMENT", "threshold": "BLOCK_MEDIUM_AND_ABOVE" },
        { "category": "HARM_CATEGORY_HATE_SPEECH", "threshold": "BLOCK_MEDIUM_AND_ABOVE" },
        { "category": "HARM_CATEGORY_SEXUALLY_EXPLICIT", "threshold": "BLOCK_MEDIUM_AND_ABOVE" },
        { "category": "HARM_CATEGORY_DANGEROUS_CONTENT", "threshold": "BLOCK_MEDIUM_AND_ABOVE" }
    ]
}
EOF
)

    # Имя файла для сохранения результата
    BASENAME=$(basename "$FILE_PATH")
    OUTPUT_FILE="${OUTPUT_DIR}/${BASENAME}.txt"

    # Отправка запроса к API
    curl -s -H "Content-Type: application/json" -d "${JSON_PAYLOAD}" -X POST "${API_URL}" > "$OUTPUT_FILE"

    echo "Результат анализа сохранен в: $OUTPUT_FILE"
done

echo "Анализ всех файлов завершен."