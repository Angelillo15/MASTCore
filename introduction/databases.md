# Databases

MAStaff supports MySQL and SQLite databases.

## What database I should use ?

|                   | MySQL | SQLite |
| ----------------- | ----- | ------ |
| Sync staff mode   | ✔     | ❌      |
| Sync vanish       | ✔     | ❌      |
| Global staff mode | ✔     | ❌      |
| Global vanish     | ✔     | ❌      |
| Staff Mode        | ✔     | ✔      |
| Staff Chat        | ✔     | ✔      |

* Recomendation, if you have a network use MySQL

## MySQL

To use MySQL you need to have a MySQL or MariaDB server running. You can use a local server or a remote server.

1. First go to the config and change the type from SQLite to MySQL
2.  Fill the params with your host database data acces, then restart the server

    ![](https://i.imgur.com/Ox0lMhQ.png)

## SQLite

1. Change the database type to SQLite and restart the server
2. That's it, you are ready to go
