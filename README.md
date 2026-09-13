# Manga Catalog System
Console application developed in Java to manage a manga catalog, featuring a dual-level access system (Admin/User) and data persistence powered by SQLite.
## Overview
This project implements a command-line interface to interact with a local database.
### Key Features
* **Admin Management**
* Connection with password
* Insertion of manga with title, author,... and bulk insertion many tomes
* Delete manga with id which is allocated automatically
* Search in database
* **User Consultation**
* Catalog : Full list of mangas in database
* Search in database
### How to run
* Install Java
* Install Driver JDBC SQLite ('sqlite-jdbc-3.53.4.0.jar')
* Navigate to the src directory
* javac -cp .:../lib/sqlite-jdbc-3.53.4.0.jar *.java
* java -cp .:../lib/sqlite-jdbc-3.53.4.0.jar Main
