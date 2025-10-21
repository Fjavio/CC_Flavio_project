# CC_Flavio_project
Project for Cloud Computing exam
 
# TITLE - Conservatory Degree Courses Management System

## Overview
This project is a Java-based management system for academic degree courses at a Conservatory of Music.
It simulates real-world administrative and academic processes — including teacher/course management, exam registration, and validation — and stores all data in a MySQL relational database.
The project chosen to be implemented in the cloud was taken from a project assigned to me for the Software Engineering exam.

## Description
The platform will support several roles:  
- **Students**: manage personal study plans, access their exam records, view grades and both arithmetic and weighted averages.   
- **Teachers**: manage courses, enter exam results, and validate them through electronic signatures (PIN verification).  
- **Administration**: manage academic data, update students and teachers, and assign courses to teachers each academic year.  

Specifically the system covers five main areas of study: singing, instruments, composition, ensemble music, and music education.  
Some categories, such as singing (lyrical and jazz) and instruments (piano, violin, guitar, etc.), offer different specializations. 

In this implementation, we focus on **a subset of the main functionalities**, which may be part of a larger real case, including:
- Management of courses, teachers, and students through DAO and Entity classes.
- Teacher operations for opening and closing exam reports.
- Administration operations for adding courses, adding teachers, and assigning courses to teachers.
- Exam management with validation of prerequisites (propaedeutic control) and PIN-based verification.
  
## Objectives
- Implement a modular, layered system for academic course management.
- Provide teachers with tools to manage exam sessions (open and close reports, insert exam results).  
- Allow secure and verified exam registration through PIN validation and prerequisite checks.  
- Support administration tasks such as course creation, teacher assignment, and yearly course scheduling.  
- Store all information about students, teachers, courses and any connections between them, in a well-related database
- Demonstrate separation of concerns via the DAO–Entity–Control–Boundary pattern.
  
## Project Architecture
The system is organized according to the **DAO–Entity–Control–Boundary** architectural pattern, a simplified MVC-style organization designed for modularity and testability, and supports basic **CRUD** operations together with specific business rules (e.g., prerequisite validation, vote validation, PIN verification).
- **Java** (core language for implementation)  
- **DAO pattern**: handles all interaction with the database (CRUD operations and custom queries)  
- **Entity classes**: represents real-world objects as data structures: (`Course`, `Teacher`, `Student`, `Exam`, `Report`); mirrors DB tables.   
- **Control classes**: `GestoreCorsiDiStudioConservatorio` implements business rules and coordinates DAO calls: validation and orchestration of operations, enforces prerequisites, ensures integrity.   
- **Boundary classes** for user interaction: console-based menus for Teachers and Administration:  
  - `BoundaryDocente` → Teachers (open/close reports, insert exam results)  
  - `BoundarySegreteriaStudenti` → Administration (add teachers, add courses, associate teacher-course)  
  - GUI menus (extensions of the console menus)
- **MySQL Database** to persist all information (courses, teachers, students, exams, reports)  

## Installation and Setup Guide
1 Requirements:
- Java JDK 17+
- Eclipse IDE (or IntelliJ, NetBeans, etc.)
- MySQL Database (via XAMPP or standalone)
- JDBC Driver for MySQL (mysql-connector-j-8.x.x.jar)

2 Database Setup (via XAMPP):
- Open XAMPP Control Panel → Start Apache and MySQL.
- Open phpMyAdmin (http://localhost/phpmyadmin).
- Create a new database, e.g. conservatory_db, and creates tables that represent the entities

3 Eclipse
- Update your database configuration file in DBManager.java with your connection details: conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionecorsidistudioconservatorio", "root", "");

## Cloud Perspective
This project is designed with a layered architecture that can easily be deployed in the cloud. In a cloud environment:
- The **Control and DAO layers** would be hosted on a remote application server (e.g., AWS EC2, Azure App Service).  
- The **MySQL database** could be migrated to a managed cloud database (e.g., AWS RDS, Google Cloud SQL).  
- **User interfaces** (Boundaries) could be accessed remotely via a web or REST API layer.  

The system’s modularity and business logic make it ideal for cloud deployment, allowing multiple users (students, teachers, administration) to access shared academic data securely and efficiently.

## Project Management
Progress tracking of the project during the Software Engineering Exam
- [Milestone: Hito 0.1 - Base Architecture](https://github.com/Fjavio/CC_Flavio_project/milestone/1):
  - [Issue #1: Implement ENTITY Layer](https://github.com/Fjavio/CC_Flavio_project/issues/1) 
  - [Issue #2: Implement DAO Layer](https://github.com/Fjavio/CC_Flavio_project/issues/2) 
- [Milestone: Hito 0.2 - Advanced DAO functionalities](https://github.com/Fjavio/CC_Flavio_project/milestone/2):
  - [Issue #1: Advanced courseDAO](https://github.com/Fjavio/CC_Flavio_project/issues/4) 
  - [Issue #2: Advanced examDAO](https://github.com/Fjavio/CC_Flavio_project/issues/3) 
- [Milestone: Hito 0.3 - Implement Control and Boundary layers](https://github.com/Fjavio/CC_Flavio_project/milestone/3):
  - [Issue #1: Implement Control layer](https://github.com/Fjavio/CC_Flavio_project/issues/5) 
  - [Issue #2: Implement Boundary layer](https://github.com/Fjavio/CC_Flavio_project/issues/6) 
- [Milestone: Hito 0.4 - Implement GUI menus](https://github.com/Fjavio/CC_Flavio_project/milestone/4)
---------------------------------------------------------------------------------------------------------------
Progress tracking of the project during the Cloud Computing Exam
- Milestone: Hito 1 - Repositorio de prácticas y definición del proyecto: refer to the milestones of the first part
- [Milestone: Hito 2 -  Integración continua](https://github.com/Fjavio/CC_Flavio_project/milestone/5):
  - [Issue #1: Set Up Build Automation with Maven](https://github.com/Fjavio/CC_Flavio_project/issues/7)
  - [Issue #2: Implement Unit Testing Strategy with JUnit 5](https://github.com/Fjavio/CC_Flavio_project/issues/8)
  - [Issue #3: Develop Integration Tests for System Components](https://github.com/Fjavio/CC_Flavio_project/issues/9)
  - [Issue #4: Create BDD Tests for Business Logic](https://github.com/Fjavio/CC_Flavio_project/issues/10)
  - [Issue #5: Configure Continuous Integration with GitHub Actions](https://github.com/Fjavio/CC_Flavio_project/issues/11) 

