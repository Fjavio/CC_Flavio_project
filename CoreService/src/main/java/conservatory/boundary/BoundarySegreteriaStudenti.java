//CLIENT WILL USE IT AS A WEB APPLICATION AND NO MORE FROM TERMINAL
//Furthermore, if the goal is to create a microservice, which by definition 
//is a web application without a user interface, boundary is no longer necessary

/*package conservatory.boundary;
import java.util.Scanner;

import conservatory.control.GestoreCorsiDiStudioConservatorio;
import conservatory.exception.OperationException;

public class BoundarySegreteriaStudenti {
	static Scanner scan;
	static GestoreCorsiDiStudioConservatorio gestore;

	public static void main(String[] args) {
		
		scan = new Scanner(System.in);
		//Main creates the REAL instance for normal execution
		gestore = new GestoreCorsiDiStudioConservatorio();
		boolean exit = false;
		
		while(!exit) {
			System.out.println("StudentSecretariat");
			System.out.println("1. AddTeacher");
			System.out.println("2. AddCourse");
			System.out.println("3. AssociationTeacherCourse");
			System.out.println("4. Exit");
			
			String op = scan.nextLine();

			if(op.equals("1")) {
				AddTeacher();
			} else if(op.equals("2")){
				AddCourse();
			} else if(op.equals("3")){
				AssociationTeacherCourse();
			} else if(op.equals("4")){
				exit = true;
			}else{
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
    
    public static void setScanner(Scanner newScanner) {
        scan = newScanner;
    }
	
	public static void AssociationTeacherCourse(){ //public to test with JUNIT
		
		String course = null;
		String teacher = null;
		try {
			boolean validCourse = false;
            while (!validCourse) {
            	System.out.println("Enter the course code");
    			course = scan.nextLine();
                try {
                	if (course.length() == 5) {
                        if (course.matches("[a-zA-Z0-9]+")) {
                            if (gestore.checkCourse(course)) {
                                validCourse = true;
                            } else {
                                System.out.println("The specified course does not exist in the database. Try again.");
                            }
                        } else {
                            System.out.println("CourseCode must be without symbols. Try again.");
                        }
                    } else {
                        System.out.println("CourseCode must be 5 characters long. Try again.");
                    }
                } catch (OperationException oE) {
                    System.out.println(oE.getMessage());
                    System.out.println("Error acquiring data, try again..");
                } catch (Exception e) {
                    System.out.println("Unexpected error, try again..");
                }
            }
	
            boolean validTeacherID = false;
            while (!validTeacherID) {
            	System.out.println("Enter the teacher's ID to be associated with the course");
    			teacher = scan.nextLine();
                try {
                	if (teacher.length() == 7) {
                        if (teacher.matches("[a-zA-Z0-9]+")) {
                            if (gestore.checkTeacher(teacher)) {
                                validTeacherID = true;
                            } else {
                                System.out.println("The specified teacher does not exist in the database. Try again.");
                            }
                        } else {
                            System.out.println("Teacher ID must be without symbols. Try again.");
                        }
                    } else {
                        System.out.println("Teacher ID must be 7 characters long. Try again.");
                    }
                } catch (OperationException oE) {
                    System.out.println(oE.getMessage());
                    System.out.println("Error acquiring data, try again..");
                } catch (Exception e) {
                    System.out.println("Unexpected error, try again..");
                }
            }
			
            gestore.AssociationTeacherCourse(course, teacher);
		}catch (OperationException oE) {
			System.out.println(oE.getMessage());
			System.out.println("Try again..\n");
		} catch (Exception e) {
			System.out.println("Unexpected exception, try again..");
			System.out.println();
		}
	}
	
	public static void AddTeacher() { //public to test with JUNIT
		
        try {
            System.out.println("Teacher name:");
            String name = scan.nextLine();
            System.out.println("surnam:");
            String surname = scan.nextLine();
            System.out.println("teacher id:");
            String id = scan.nextLine();
            gestore.createAndInsertTeacher(name, surname, id);
        }catch (OperationException oE) {
			System.out.println(oE.getMessage());
			System.out.println("Try again..\n");
		} catch (Exception e) {
			System.out.println("Unexpected exception, try again..");
			System.out.println();
		}
	}
	
	public static void AddCourse() { //public to test with JUNIT
		
	    String preOf = "";
	    String preFor = "";
	    //String preOf = null;
		//String preFor = null;
	    try {
	        System.out.println("Course code:");
	        String courseCode = scan.nextLine();
	        System.out.println("Course name:");
	        String courseName = scan.nextLine();
	        System.out.println("CFU:");
	        int CFU = Integer.parseInt(scan.nextLine());

	        System.out.println("Do you want to add prerequisites of? (yes/no):");
	        String answer = scan.nextLine();
	        if (answer.equalsIgnoreCase("yes")) {
	            boolean continueAdding = true;
	            while (continueAdding) {
	                System.out.println("Enter the preparatory course code:");
	                String preparatoryCode = scan.nextLine();
	                if (!preOf.isEmpty()) {
	                    preOf += " ";
	                }
	                preOf += preparatoryCode;

	                System.out.println("Do you want to add another preparatory course? (yes/no):");
	                answer = scan.nextLine();
	                continueAdding = answer.equalsIgnoreCase("yes");
	            }
	        }

	        System.out.println("Do you want to add prerequisites for? (yes/no):");
	        answer = scan.nextLine();
	        if (answer.equalsIgnoreCase("yes")) {
	            boolean continueAdding = true;
	            while (continueAdding) {
	                System.out.println("Enter the preparatory course code:");
	                String preparatoryCode = scan.nextLine();
	                if (!preFor.isEmpty()) {
	                    preFor += " ";
	                }
	                preFor += preparatoryCode;

	                System.out.println("Do you want to add another preparatory course? (yes/no):");
	                answer = scan.nextLine();
	                continueAdding = answer.equalsIgnoreCase("yes");
	            }
	        }

	        gestore.createAndInsertCourse(courseCode, courseName, CFU, preOf, preFor);
	    } catch (OperationException oE) {
	        System.out.println(oE.getMessage());
	        System.out.println("Try again..\n");
	    } catch (Exception e) {
	        System.out.println("Unexpected error, try again..");
	        System.out.println();
	    }
	}


}
*/
	