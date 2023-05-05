package com.example.app.database

data class Queries(
    val SQL_CREATE_TABLE_USERS: String =
        "CREATE TABLE ${"users"} (" +
                "${"user_id"} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${"user_login"} TEXT," +
                "${"user_email"} TEXT,"+
                "${"user_password "} TEXT)",

    val SQL_CREATE_TABLE_TODOS: String =
        "CREATE TABLE ${"todos"} (" +
                "${"todo_id"} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${"user_id"} INTEGER," +
                "${"todo_name"} TEXT," +
                "${"todo_descr"} TEXT,"+
                "${"todo_deadline"} TEXT,"+
                "${"isDone"} BOOLEAN DEFAULT 0,"+
                "${"isDeleted"} BOOLEAN DEFAULT 0,"+
                "${"createdAtTimestamp "} TIMESTAMP,"+
                "${"doneAtTimestamp  "} TIMESTAMP)",

    val ON_INSERT_TRIGGER: String =
        "CREATE TRIGGER check_duplicate_todos\n" +
                "BEFORE INSERT ON todos\n" +
                "FOR EACH ROW\n" +
                "BEGIN\n" +
                "  IF EXISTS (\n" +
                "    SELECT 1 FROM todos\n" +
                "    WHERE\n" +
                "      user_id = NEW.user_id\n" +
                "      AND todo_name = NEW.todo_name\n" +
                "      AND todo_descr = NEW.todo_descr\n" +
                "      AND todo_deadline = NEW.todo_deadline\n" +
                "      AND isDone = NEW.isDone\n" +
                "      AND isDeleted = NEW.isDeleted\n" +
                "      AND createdAtTimestamp = NEW.createdAtTimestamp\n" +
                "  )\n" +
                "  THEN\n" +
                "    SELECT RAISE(ABORT, 'Duplicate todo found');\n" +
                "  END IF;\n" +
                "END;\n",

    val LOAD_TEST_TODOS: String =
        "INSERT INTO todos (user_id, todo_name, todo_descr, todo_deadline, isDone, isDeleted, createdAtTimestamp) VALUES\n" +
                "(1, 'Стать здоровым!', 'Я обязательно должен выздороветь за эту неделю, так как скоро экзамены', '04-05-2023', true, false, '2023-04-27 11:38:35.607'),\n" +
                "(1, 'Сделать домашнюю работу по математике', 'Нужно закрепить материал по теории вероятности', '02-05-2023', false, false, '2023-04-27 11:38:35.607'),\n" +
                "(1, 'Выполнить проект по Java', 'Необходимо доделать проект и отправить до конца недели', '05-05-2023', false, false, '2023-04-27 11:38:35.607'),\n" +
                "(1, 'Купить продукты в магазине', 'Молоко, хлеб, яйца, масло, овощи', '01-05-2023', false, false, '2023-04-27 11:38:35.607'),\n" +
                "(1, 'Позвонить маме', 'Спросить как дела, поговорить о последних новостях', '29-04-2023', true, false, '2023-04-27 11:38:35.607'),\n" +
                "(1, 'Приготовить обед', 'Сделать картофельное пюре и запеченную рыбу', '28-04-2023', false, false, '2023-04-27 11:38:35.607'),\n" +
                "(1, 'Прочитать главу из книги', 'Глава \"Искусство программирования\"', '06-05-2023', false, false, '2023-04-27 11:38:35.607'),\n" +
                "(1, 'Сделать зарядку', 'Утренняя зарядка для здоровья', '28-04-2023', true, false, '2023-04-27 11:38:35.607'),\n" +
                "(1, 'Провести собрание по проекту', 'Созвать команду и обсудить текущий прогресс проекта', '07-05-2023', false, false, '2023-04-27 11:38:35.607'),\n" +
                "(1, 'Забрать посылку на почте', 'Посылка от Amazon', '03-05-2023', false, false, '2023-04-27 11:38:35.607'),\n" +
                "(1, 'Погулять с собакой', 'Сходить в парк с ретривером', '03-05-2023', false, false, '2023-04-28 12:15:20.240'),\n" +
                "(1, 'Сделать зарядку', 'Совершать упражнения в течение 30 минут', '04-05-2023', false, false, '2023-04-28 12:15:20.240'),\n" +
                "(1, 'Прочитать книгу', 'Прочитать \"1984\" Джорджа Оруэлла', '05-05-2023', false, false, '2023-04-28 12:15:20.240'),\n" +
                "(1, 'Занятия по математике', 'Повторение математических формул', '06-05-2023', false, false, '2023-04-28 12:15:20.240'),\n" +
                "(1, 'Сделать домашнее задание по программированию', 'Написать программу на Java', '07-05-2023', false, false, '2023-04-28 12:15:20.240'),\n" +
                "(1, 'Написать статью', 'Описание результатов исследования', '08-05-2023', false, false, '2023-04-28 12:15:20.240'),\n" +
                "(1, 'Позвонить родителям', 'Обсудить предстоящее семейное мероприятие', '09-05-2023', false, false, '2023-04-28 12:15:20.240'),\n" +
                "(1, 'Сходить на концерт', 'Купить билеты на концерт', '10-05-2023', false, false, '2023-04-28 12:15:20.240'),\n" +
                "(1, 'Сходить на экскурсию', 'Осмотр достопримечательностей города', '11-05-2023', false, false, '2023-04-28 12:15:20.240'),\n" +
                "(1, 'Посетить театр', 'Купить билеты на спектакль', '12-05-2023', false, false, '2023-04-28 12:15:20.240'),\n" +
                "(1, 'Помыть машину', 'Очистить машину от грязи', '13-05-2023', false, false, '2023-04-28 12:15:20.240'),\n" +
                "(1, 'Сходить на рыбалку', 'Собрать рыболовные снасти', '14-05-2023', false, false, '2023-04-28 12:15:20.240');\n"

)
