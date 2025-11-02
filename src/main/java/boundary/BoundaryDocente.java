package boundary;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import control.GestoreCorsiDiStudioConservatorio;
import database.EsameDAO;
import entity.EntityEsame;
import exception.OperationException;

public class BoundaryDocente {
	static Scanner scan = new Scanner(System.in);
	static GestoreCorsiDiStudioConservatorio gestore;

	public static void main(String[] args) {	
		
		//Main creates the REAL instance for normal execution
		gestore = new GestoreCorsiDiStudioConservatorio();
		boolean exit = false;
		
		while(!exit) {
			System.out.println("Teacher");
			System.out.println("1. OpeningReport");
			System.out.println("2. ClosingReport");
			System.out.println("3. Exit");
			
			String op = scan.nextLine();

			if (op.equals("1")) {
				OpeningReport();
            } else if (op.equals("2")) {
            	ClosingReport();
                } else if (op.equals("3")) {
                exit = true;
            } else {
                System.out.println("Operation not available");
                System.out.println();
            }
        }
		
		System.out.println("Goodbye");
		
	}
	
	//Method to allow tests to INJECT the MOCK
    public static void setGestore(GestoreCorsiDiStudioConservatorio gestoreToUse) {
        gestore = gestoreToUse;
    }
	
    public static void OpeningReport() { //public to test with JUNIT
    	
        String reportCode = null;
        String teacherID = null;
        String courseCode = null;
        String username = null;
        Date date = null;
        try {
        	boolean validReport = false;
            while (!validReport) {
            	System.out.println("Enter the report you want to create");
                reportCode = scan.nextLine();
                try {
                	if (reportCode.length() == 5) {
                        if (reportCode.matches("[a-zA-Z0-9]+")) {
                                validReport = true;
                        } else {
                            System.out.println("The report must be without symbols. Try again.");
                        }
                    } else {
                        System.out.println("The report must be 5 characters. Try again.");
                    }
                } catch (Exception e) {
                    System.out.println("Unexpected error, try again..");
                }
            }
            
            boolean validDate = false;
            while (!validDate) {
				try {
					System.out.println("Insert the date (aaaa-MM-gg)");
					String dateTemp = scan.nextLine();
					date = Date.valueOf(dateTemp);

					validDate = true;
				} catch (IllegalArgumentException iE) {
					System.out.println("Error acquiring the date, try again..");
					System.out.println();
				}
			}
			
            boolean validTeacherID = false;
            while (!validTeacherID) {
                System.out.println("Insert your id");
                teacherID = scan.nextLine();
                try {
                	if (teacherID.length() == 7) {
                        if (teacherID.matches("[a-zA-Z0-9]+")) {
                            if (gestore.checkTeacher(teacherID)) {
                                validTeacherID = true;
                            } else {
                                System.out.println("The specified teacher does not exist in the database. Try again.");
                            }
                        } else {
                            System.out.println("Id must be without symbols. Try again.");
                        }
                    } else {
                        System.out.println("Id must be 7 characters long. Try again.");
                    }
                } catch (OperationException oE) {
                    System.out.println(oE.getMessage());
                    System.out.println("Error acquiring data, try again..");
                } catch (Exception e) {
                    System.out.println("Unexpected error, try again..");
                }
            }
            
            gestore.OpeningReport(reportCode, date, teacherID);
            System.out.println("Report " + reportCode + " created successfully.");
            
            boolean insertOtherExam = true;
            while (insertOtherExam) {
                System.out.println("Enter exam details:");
                System.out.println("Vote:");
                int vote = Integer.parseInt(scan.nextLine());
                System.out.println("Honors (true/false):");
                boolean honors = Boolean.parseBoolean(scan.nextLine());
                System.out.println("Teacher notes:");
                String teacherNotes = scan.nextLine();
                boolean validCourse = false;
                while (!validCourse) {
                	System.out.println("Course code:");
                    courseCode = scan.nextLine();
                    try {
                    	if (courseCode.length() == 5) {
                            if (courseCode.matches("[a-zA-Z0-9]+")) {
                                if (gestore.checkCourse(courseCode)) {
                                    validCourse = true;
                                } else {
                                    System.out.println("The specified course does not exist in the database. Try again.");
                                }
                            } else {
                                System.out.println("CourseCode must be without symbols. Try again.");
                            }
                        } else {
                            System.out.println("CourseCode must be 5 characters. Try again.");
                        }
                    } catch (OperationException oE) {
                        System.out.println(oE.getMessage());
                        System.out.println("Error acquiring data, try again..");
                    } catch (Exception e) {
                        System.out.println("Unexpected error, try again..");
                    }
                }
                
                boolean validUsername = false;
                while (!validUsername) {
                	 System.out.println("Username:");
                	 username = scan.nextLine();
                    try {
                        if (gestore.checkStudent(username)) {
                              validUsername = true;
                                } else {
                                    System.out.println("The specified student does not exist in the database. Try again.");
                                }
                    } catch (OperationException oE) {
                        System.out.println(oE.getMessage());
                        System.out.println("Error acquiring data, try again..");
                    } catch (Exception e) {
                        System.out.println("Unexpected error, try again..");
                    }
                }
               
                try {
                	gestore.createAndInsertExam(vote, honors, teacherNotes, reportCode, courseCode, username);
                    System.out.println("Exam added successfully.");
                    System.out.println("Do you want to add another exam? (yes/no):");
                    insertOtherExam = scan.nextLine().equalsIgnoreCase("yes");
                } catch (OperationException oE) {
                    System.out.println(oE.getMessage());
                    System.out.println("Try again..\n");
                } catch (Exception e) {
                    System.out.println("Unexpected exception, try again..");
                    System.out.println();
                }
            }
        } catch (OperationException oE) {
            System.out.println(oE.getMessage());
            System.out.println("Try again..\n");
        } catch (Exception e) {
            System.out.println("Unexpected exception, try again..");
            System.out.println();
        }
    }
            

	public static void ClosingReport() { //public to test with JUNIT
		
        String reportCode = null; 
        int insertedPin = 0;
        try {
        	System.out.println("Enter the report you need to close");
   		    reportCode = scan.nextLine();
   		    gestore.checkReport(reportCode);
            List<String> usernames = gestore.getUsernamesByReport(reportCode);
                for (String username : usernames) {
                	boolean validPin = false;
                    while (!validPin) {
                    	try {
                    	System.out.println("Enter the PIN for the student " + username + ":");
                        insertedPin = Integer.parseInt(scan.nextLine());
                        int length = String.valueOf(insertedPin).length();
                        try {
                        	if (length == 7) {
                                 validPin = true;
                            } else {
                                System.out.println("PIN must be 7 numbers. Try again.");
                            }
                        } catch (Exception e) {
                            System.out.println("Unexpected error, try again..");
                        }
                    	}catch (NumberFormatException nE) {
        					System.out.println("Error, enter a valid number");
        					System.out.println();
        				}
                    }
                    gestore.checkPIN(insertedPin, reportCode, username);
                    gestore.ClosingReport1(reportCode, username);
                }
                gestore.ClosingReport2(reportCode);
                System.out.println("Report closed successfully!");
        } catch (OperationException oE) {
			System.out.println(oE.getMessage());
			System.out.println("Try again..\n");
		} catch (Exception e) {
			System.out.println("Unexpected exception, try again..");
			System.out.println();
		}
    }
	
}


	