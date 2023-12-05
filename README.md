# gameGradle
Gradle task


1. Создал сервлеты для классов (модуль servlets).

  Для классов medicalKit и weapon дописал методы для вывода существующих в игре этих предметов (то есть, 
    если у нас существует 2 типа оружия - покажем данные по ним, введем новое оружие - соответственно, по нему 
    тоже покажем данные).
   
   В main Создается объект servlet и присваивается переменной типа Wrapper (обертка).
    С помощью метода setLoadOnStartup(1) при запуске сервера загружаем сервлеты.
    С помощью метода addMapping("/path") задается URL-путь для доступа к сервлету.

  Также установил отдельно Postman Agent - нужен для соединения.

   
2. По методам:

   Метод doGet(req, res) отвечает на HTTP GET запросы. Используется для обработки запросов
     на получение информации об объекте. Если в пути запроса содержится "/get", то метод получает параметр "itemId" (имя для игрока)
     из запроса, передает его в объект класса Crud и вызывается поиск. Затем полученные данные возвращаются в виде строки.

  Метод doPost(req, res) отвечает на HTTP POST запросы. Используется для добавления нового объекта. Если в 
    пути запроса содержится "/add", то метод считывает параметры из запроса, создает новый объект класса с этими параметрами и 
    вызывает метод создания объекта класса Crud. Затем возвращается информация о добавленном медицинском наборе в виде строки.

  Метод doPut(req, res) отвечает на HTTP PUT запросы. Используется для обновления информации об объекте. Если в пути запроса 
    содержится "/update", метод получает параметры и вызывает обновление в Crud. Затем возвращается строка "Update successful".

  Метод doDelete(req, res) отвечает на HTTP DELETE запросы. Используется для удаления. Если в пути запроса содержится "/delete", то метод по id 
    из запроса удаляет объект. Затем возвращается строка "Delete successful".



3. Проверка:
   
     По игроку PlayerS:
       doGet (по имени):![image](https://github.com/ChumA007/gameGradle/assets/93035703/f0cfeaa2-6570-4b55-a221-b84bc033135b)
   
       doGet (все):![image](https://github.com/ChumA007/gameGradle/assets/93035703/99e603f8-89fc-45d5-94d9-23119e11e57d)

       doPost:![image](https://github.com/ChumA007/gameGradle/assets/93035703/93a0bde7-d450-4c9a-91c0-c4404f2370d8)
         Проверка: ![image](https://github.com/ChumA007/gameGradle/assets/93035703/c856e25d-d5dd-4e48-82da-fde8455e2c9c)

       doPut(для игрока обновляется только имя): ![image](https://github.com/ChumA007/gameGradle/assets/93035703/3c00a721-10da-457a-9b63-9388aa1d5f44)
         Проверка: ![image](https://github.com/ChumA007/gameGradle/assets/93035703/41c3a349-3fa7-46ef-8e62-749c04744920)

      doDelete: ![image](https://github.com/ChumA007/gameGradle/assets/93035703/c52c00c2-589e-452f-a028-d20cf99cc5cb)

     По оружию weaponS:
       doGet (по id): ![image](https://github.com/ChumA007/gameGradle/assets/93035703/e68918f1-3b6a-4a21-ad46-2c193f72d303)

       doGet (все): ![image](https://github.com/ChumA007/gameGradle/assets/93035703/7395a6a2-7805-4187-b723-72bbf39f740f)

       doPost:![image](https://github.com/ChumA007/gameGradle/assets/93035703/f775ebb4-5205-4f09-b1c8-747b23d09cc1)
         Проверка: ![image](https://github.com/ChumA007/gameGradle/assets/93035703/db437545-9d5a-4e0b-89d1-74d218651f23)

       doPut: ![image](https://github.com/ChumA007/gameGradle/assets/93035703/0c07c9f6-8ed8-4535-8587-e691f00daf1a)
         Проверка:![image](https://github.com/ChumA007/gameGradle/assets/93035703/17192d53-2077-41b1-9f91-2a9bef689671)

      doDelete:![image](https://github.com/ChumA007/gameGradle/assets/93035703/41d15c78-cee2-40fd-91e1-5a8e2201426b)
         Проверка:![image](https://github.com/ChumA007/gameGradle/assets/93035703/a6eb6fc4-5fb6-4c11-a326-289ddd0edb11)

     По медицине medicalKitS:
       doGet (по id):![image](https://github.com/ChumA007/gameGradle/assets/93035703/f1334c5f-cd43-4527-ab22-8d9f96cb7082)

       doGet (все): ![image](https://github.com/ChumA007/gameGradle/assets/93035703/7eece4ad-1075-4fe2-add2-690567d858bf)

       doPost:![image](https://github.com/ChumA007/gameGradle/assets/93035703/92d91b69-4a2d-4330-a037-de7acdc2cc9f)
         Проверка:![image](https://github.com/ChumA007/gameGradle/assets/93035703/a026f03f-eb95-4ffa-8bc4-6916a64d4262)

       doPut:![image](https://github.com/ChumA007/gameGradle/assets/93035703/e7c12207-2d45-4f69-ba55-3e93d945cd88)
         Проверка:![image](https://github.com/ChumA007/gameGradle/assets/93035703/0bf2d47a-ada0-47fb-872d-dc89c24ee0dc)

      doDelete:![image](https://github.com/ChumA007/gameGradle/assets/93035703/fbd43487-69ed-4b3e-8fd7-d77c825c4568)
         Проверка:![image](https://github.com/ChumA007/gameGradle/assets/93035703/02453f86-bac8-40a0-91d6-a4086a6e8fe3)
