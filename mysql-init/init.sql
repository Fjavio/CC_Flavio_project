-- Initialize MySQL Database for Docker (run only the first time the volume is created)

USE gestionecorsidistudioconservatorio;

CREATE TABLE IF NOT EXISTS teacher (
    teacherID VARCHAR(7) PRIMARY KEY,
    teacherName VARCHAR(255),
    teacherSurname VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS student (
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255),
    PIN INT,
    idCDS INT
);

CREATE TABLE IF NOT EXISTS course (
    courseCode VARCHAR(5) PRIMARY KEY,
    courseName VARCHAR(255),
    CFU INT,
    teacherID VARCHAR(7),
    preOf VARCHAR(255),
    preFor VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS report (
    reportCode VARCHAR(5) PRIMARY KEY,
    reportDate DATE,
    teacherID VARCHAR(7)
);

CREATE TABLE IF NOT EXISTS exam (
    vote INT,
    honors BOOLEAN,
    teacherNotes VARCHAR(255),
    passingDate DATE,
    reportCode VARCHAR(5) NOT NULL,
    courseCode VARCHAR(5) NOT NULL,
    username VARCHAR(255) NOT NULL,
    PRIMARY KEY (reportCode, username)
);

-- Foreign Keys
ALTER TABLE course 
ADD CONSTRAINT fk_course_teacher 
FOREIGN KEY (teacherID) REFERENCES teacher(teacherID);

ALTER TABLE report 
ADD CONSTRAINT fk_report_teacher 
FOREIGN KEY (teacherID) REFERENCES teacher(teacherID);

ALTER TABLE exam 
ADD CONSTRAINT fk_exam_report 
FOREIGN KEY (reportCode) REFERENCES report(reportCode);

ALTER TABLE exam 
ADD CONSTRAINT fk_exam_course 
FOREIGN KEY (courseCode) REFERENCES course(courseCode);

ALTER TABLE exam 
ADD CONSTRAINT fk_exam_student 
FOREIGN KEY (username) REFERENCES student(username);