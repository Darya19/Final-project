﻿Сервис по регистрации абитуриентов в Белорусский национальный унииверситет
Учебный проект по курсу Java Web Development
Автор: Щербина Дарья Сергеевна

Общее описание
Веб приложение предоставляет возможность осуществлять онлайн регистрацию польователей на специальности университета.
Абитуриент может отправить заявку на подходящий для себя факультет и специальность. В случае подтверждения заявки абитуриент зачисляется на факультет и начинает обучение в университете.
В случае отказа абитуриент может подать еще одну заявку на спициальность с открытым набором.

Пользователи
Для разграничения уровней доступа и возможностей пользователей онлайн сервисом в приложении были введены роли:

Гость
Неавторизированный пользователь

Функциональные возможности:

Просмотр домашней страницы
Просмотр факультетов 
Просмотр специальностей на каждом факультете
Смена языка сайта
Авторизация/Регистрация(автоматическая отправка заявки)
Клиент
Гостю, прошедшему процедуру регистрации присваивается роль Клиент.

Функциональные возможности:

Просмотр домашней страницы
Просмотр факультетов и специальностей
Смена языка сайта
Просмотр профиля
Редактирование профиля
Повторная отправка завки(при закрытии или удалении специальности)

Администратор
Управляет работой сервиса. В его обязанности входит добавление новых факультетов и специальностей и их удаление, изменение их параметров, управление заявками клиентов, управление статусом специальностей.

Функциональные возможности:

Просмотр домашней страницы
Смена языка сайта
Просмотр списка факультетов
Просмотр списка специальностей
Подтверждение/отмена завок клиентов(смена статуса заявки: рассматривается на принята/откланена)
Изменение статуса спецальности(смена возможна при отсутствии рассмотривающихся заявок)
Добавление специальности
Определение количества свободных мест на специальности
Добавление факультета
Редактирование факультета
Редактирование специальности
Удаление факультета
Удалениее специальности
При удалении специальности или факультета статус ранее поданых заявок меняется на неактивный

Факультет
Являются предметной областью онлайн сервиса. Обладают различными параметрами, такими как название факультета, описание. Так же каждому автомобилю присвоены специальности.

Специальность
Являются предметной областью онлайн сервиса. Обладают различными параметрами, такими как название специальности, количество мест и статус - открыт набор или нет.
