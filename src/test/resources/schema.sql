-- Empty tables if they already exist (to rerun tests)
DROP TABLE IF EXISTS exam;
DROP TABLE IF EXISTS report;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS teacher;

-- Creazione Tabelle
CREATE TABLE teacher (
    ID VARCHAR(7) PRIMARY KEY,
    teacherName VARCHAR(255),
    teacherSurname VARCHAR(255)
);

CREATE TABLE student (
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255),
    PIN INT,
    idCDS INT
);

CREATE TABLE course (
    courseCode VARCHAR(5) PRIMARY KEY,
    courseName VARCHAR(255),
    CFU INT,
    teacherID VARCHAR(7),
    preOf VARCHAR(255),
    preFor VARCHAR(255),
    FOREIGN KEY (teacherID) REFERENCES teacher(ID)
);

CREATE TABLE report (
    reportCode VARCHAR(5) PRIMARY KEY,
    reportDate DATE,
    teacherID VARCHAR(7),
    FOREIGN KEY (teacherID) REFERENCES teacher(ID)
);

CREATE TABLE exam (
    vote INT,
    honors BOOLEAN,
    teacherNotes VARCHAR(255),
    passingDate DATE,
    reportCode VARCHAR(5),
    courseCode VARCHAR(5),
    username VARCHAR(255),
    PRIMARY KEY (reportCode, username),
    FOREIGN KEY (reportCode) REFERENCES report(reportCode),
    FOREIGN KEY (courseCode) REFERENCES course(courseCode),
    FOREIGN KEY (username) REFERENCES student(username)
);