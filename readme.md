# Система управления персоналом (Employee Management System)

**Employee Management System** — это веб-приложение, предназначенное для автоматизации процессов управления персоналом в
организации. Система позволяет вести учет сотрудников, отделов, должностей, рабочего времени, а также управлять
пользователями и ролями с разграничением прав доступа.

---

## Основные функции

1. **Управление сотрудниками:**
    * Добавление нового сотрудника.
    * Редактирование данных сотрудника.
    * Увольнение сотрудника (изменение поля isActive).
    * Просмотр списка сотрудников.
    * Просмотр карточки сотрудника.

2. **Управление отделами:**
    * Добавление нового отдела.
    * Редактирование отдела.
    * Удаление отдела (с проверкой отсутствия сотрудников).
    * Просмотр списка отделов.
    * Просмотр информации об отделе.

3. **Управление должностями:**
    * Добавление новой должности.
    * Редактирование должности.
    * Удаление должности.
    * Просмотр списка должностей.
    * Просмотр информации о должности.

4. **Учет рабочего времени:**
    * Ввод табеля учета рабочего времени.
    * Просмотр табеля учета рабочего времени.
    * Редактирование табеля (с соответствующими правами).

5. **Управление пользователями и ролями:**
    * Создание пользователя (связанного с сотрудником).
    * Редактирование пользователя.
    * Удаление пользователя.
    * Создание/редактирование/удаление ролей.
    * Назначение ролей пользователям.

6. **Расчет зарплаты:**
    * Расчет зарплаты за период (на основе оклада и отработанных часов).
    * Просмотр расчетных листков.

## Роли пользователей

* **Администратор:** Полный доступ ко всем функциям системы.
* **Сотрудник:** Просмотр своих данных и табеля.

---

## Технологии

* **Язык программирования:** Java 23
* **Фреймворк:** Spring Boot 3.x
* **База данных:** PostgreSQL
* **Сборка:** Gradle
* **Архитектура:** REST

---

### Требования

* Установленная Java 23 или выше.
