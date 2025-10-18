Feature: Report Management by Teacher
  As a teacher
  I want to close an exam report
  So that students exams are validated and recorded correctly based on system rules

  Scenario: A student exam is deleted due to an incorrect PIN
    Given the following student exists:
      | username   | name  | surname | password  | pin     | idCDS |
      | mario_ross | Mario | Rossi   | pass_AXXX | 1234567 | 101   |
    And an open report with code "VER01" contains an exam for student "mario_ross"
    When the teacher closes report "VER01" providing incorrect PIN 9999999 for student "mario_ross"
    Then the exam for "mario_ross" in report "VER01" should be deleted due to the PIN mismatch

  Scenario: A student exam is deleted due to missing prerequisites
    Given the following student exists:
      | username   | name | surname | password  | pin     | idCDS |
      | anna_verdi | Anna | Verdi   | pass_WWWW | 1112222 | 101   |
    And the following course exists:
      | courseCode | name                 | prerequisite |
      | AD002      | Advanced Programming | BA001        |
    And an open report "VER02" for course "AD002" contains an exam for student "anna_verdi"
    And the student "anna_verdi" has not passed preparatory exams
    When the teacher closes report "VER02" providing the correct PIN for "anna_verdi"
    Then the exam for "anna_verdi" is deleted due to missing prerequisites

  Scenario: A report is closed successfully with all checks passed
    Given the following student exists:
      | username   | name | surname | password  | pin     | idCDS |
      | luca_bia02 | Luca | Bianchi | pass_PPPP | 3334444 | 102   |
    And the following course exists:
      | courseCode | name                 | prerequisite |
      | AD002      | Advanced Programming | BA001        |
    And an open report "VER03" for course "AD002" contains a valid exam with vote 28 for student "luca_bia02"
    And the student "luca_bia02" has already passed the following exams:
      | courseCode |
      | BA001      |
    When the teacher closes report "VER03" providing the correct PIN for "luca_bia02"
    Then the report closing is successful and the votes are finalized for report "VER03"