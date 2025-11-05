Feature: Course and Teacher Association
  As a secretariat user
  I want to associate teachers with courses
  So that the course has an assigned professor

  Scenario: Successful association of a teacher to an unassigned course
    Given a teacher with ID "C123456" exists
    And a course with code "A1234" exists
    And the course "A1234" is not yet assigned to any teacher
    When the secretariat associates the teacher "C123456" with the course "A1234"
    Then the association is successful and the database is updated

  Scenario: Attempt to associate a teacher to an already assigned course
    Given a teacher with ID "AA12345" exists
    And a course with code "B1234" exists
    And the course "B1234" is already assigned to teacher "BB12345"
    When the secretariat tries to associate teacher "AA12345" with course "B1234"
    Then the operation fails with the message "Course has already been assigned to a teacher"