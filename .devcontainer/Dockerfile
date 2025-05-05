# Этап 1: Сборка приложения с Gradle
FROM gradle:8.10.2-jdk23 AS builder

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем Gradle файлы для кэширования зависимостей
COPY ./build.gradle.kts settings.gradle.kts ./
COPY ./gradlew ./
COPY ./gradle ./gradle

# Даем права на выполнение gradlew
RUN chmod +x gradlew

# Загружаем зависимости
RUN ./gradlew dependencies --no-daemon

# Копируем исходный код
COPY ./src ./src

# Собираем JAR
RUN ./gradlew bootJar --no-daemon

# Этап 2: Запуск приложения
FROM openjdk:23-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR
COPY --from=builder /app/build/libs/*.jar app.jar

# Открываем порт
EXPOSE 8080

# Запускаем приложение
CMD ["java", "-jar", "app.jar"]