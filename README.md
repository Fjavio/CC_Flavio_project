# CC_Flavio_project
Project for Cloud Computing exam
 
# TITLE - Conservatory Degree Courses Management System

## Reference 
The project chosen to be implemented in the cloud was taken from a project assigned to me for the Software Engineering exam.

## Description
This project aims to develop a small example of a management system for degree courses at a conservatory.

The platform will support several roles:  
- **Students**: manage personal study plans, access their exam records, view grades and both arithmetic and weighted averages. (!!NOT IMPLEMENTED!!)  
- **Teachers**: manage courses, enter exam results, and validate them through electronic signatures (PIN verification).  
- **Administration**: manage academic data, update students and teachers, and assign courses to teachers each academic year.  

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
- Demonstrate the integration of different layers (Entity, DAO, Control, Boundary) in Java.
  
## Project Structure
The system is organized according to the **DAO–Entity–Control–Boundary** architectural pattern and supports basic **CRUD** operations together with specific business rules (e.g., prerequisite validation, vote validation, PIN verification).
- **Java** (core language for implementation)  
- **DAO pattern** for database interaction (CRUD and custom queries)  
- **Entity classes** for domain modeling (`Course`, `Teacher`, `Student`, `Exam`, `Report`)  
- **Control classes** for business logic (validation, orchestration of operations)   
- **Boundary classes** for user interaction: console-based menus for Teachers and Administration:  
  - `BoundaryDocente` → Teachers (open/close reports, insert exam results)  
  - `BoundarySegreteriaStudenti` → Administration (add teachers, add courses, associate teacher-course)  
  - GUI menus (extensions of the console menus)
- **MySQL Database** to persist all information (courses, teachers, students, exams, reports)  
- **XAMPP** used to run and manage the MySQL database locally 
