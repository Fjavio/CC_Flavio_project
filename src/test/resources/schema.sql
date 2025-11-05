-- Disable foreign key constraints temporarily to avoid order errors
SET FOREIGN_KEY_CHECKS=0;

-- Empty tables if they already exist (to rerun tests)
DROP TABLE IF EXISTS teacher, student, course, report, exam;

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
    COURSECODE VARCHAR(5) PRIMARY KEY,
    courseName VARCHAR(255),
    CFU INT,
    teacherID VARCHAR(7),
    preOf VARCHAR(255),
    preFor VARCHAR(255)
    -- Add the constraint for the teacher outside the table (FOREIGN KEY (teacherID) REFERENCES teacher(ID)) 
);

CREATE TABLE report (
    reportCode VARCHAR(5) PRIMARY KEY,
    reportDate DATE,
    teacherID VARCHAR(7)
    -- Add the constraint for the teacher outside the table (FOREIGN KEY (teacherID) REFERENCES teacher(ID))
);

CREATE TABLE exam (
    vote INT,
    honors BOOLEAN,
    teacherNotes VARCHAR(255),
    passingDate DATE,
    reportCode VARCHAR(5),
    courseCode VARCHAR(5),
    username VARCHAR(255),
    -- Composite primary key
    PRIMARY KEY (reportCode, username)
);

-- Constraints Foreign Keys
ALTER TABLE course ADD FOREIGN KEY (teacherID) REFERENCES teacher(ID);
ALTER TABLE report ADD FOREIGN KEY (teacherID) REFERENCES teacher(ID);
ALTER TABLE exam ADD FOREIGN KEY (reportCode) REFERENCES report(reportCode);
ALTER TABLE exam ADD FOREIGN KEY (courseCode) REFERENCES course(COURSECODE);
ALTER TABLE exam ADD FOREIGN KEY (username) REFERENCES student(username);

-- Reactivate constraints
SET FOREIGN_KEY_CHECKS=1;