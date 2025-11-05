-- Enter the essential test data

-- Teachers required for the tests
INSERT INTO teacher (ID, teacherName, teacherSurname) VALUES ('C123456', 'Claudia', 'Carrara');
INSERT INTO teacher (ID, teacherName, teacherSurname) VALUES ('DUMMY01', 'July', 'Peach');

-- Student required for the tests
INSERT INTO student (username, password, PIN, idCDS) VALUES ('flavio', 'ugrcorreo', 1234567, 12345);

-- Courses required for the tests:

-- 1)A course without teacher (for the association tests)
INSERT INTO course (COURSECODE, courseName, CFU, teacherID, preOf, preFor) 
VALUES ('A1234', 'CloudComputing', 6, NULL, NULL, NULL);

-- A course already assigned to test failures
INSERT INTO course (COURSECODE, courseName, CFU, teacherID, preOf, preFor) 
VALUES ('A5555', 'MachineLearning', 6, 'DUMMY01', NULL, NULL);