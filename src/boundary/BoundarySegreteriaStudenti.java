package boundary;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import control.GestoreCorsiDiStudioConservatorio;
import exception.OperationException;

public class BoundarySegreteriaStudenti {
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {		
		boolean exit = false;
		
		while(!exit) {
			System.out.println("SegreteriaStudenti");
			System.out.println("1. AggiungiDocente");
			System.out.println("2. AggiungiCorso");
			System.out.println("3. AssociazioneDocenteCorso");
			System.out.println("4. Esci");
			
			String op = scan.nextLine();

			if(op.equals("1")) {
				AggiungiDocente();
			} else if(op.equals("2")){
				AggiungiCorso();
			} else if(op.equals("3")){
				AssociazioneDocenteCorso();
			} else if(op.equals("4")){
				exit = true;
			}else{
				System.out.println("Operazione non disponibile");
				System.out.println();
			}
		}	
		
		System.out.println("Arrivederci");
		
	}
	
	public static void AssociazioneDocenteCorso(){ //public per fare JUNIT
		 GestoreCorsiDiStudioConservatorio gestoreCorsiDiStudioConservatorio = GestoreCorsiDiStudioConservatorio.getInstance();
		 String corso = null;
		 String docente = null;
		try {
			boolean corsoValido = false;
            while (!corsoValido) {
            	System.out.println("Inserisci il codice del corso");
    			corso = scan.nextLine();
                try {
                	if (corso.length() == 5) {
                        if (corso.matches("[a-zA-Z0-9]+")) {
                            if (gestoreCorsiDiStudioConservatorio.controlloCorso(corso)) {
                                corsoValido = true;
                            } else {
                                System.out.println("Corso specificato non esiste nel database. Riprovare.");
                            }
                        } else {
                            System.out.println("CodiceCorso deve essere senza simboli. Riprovare.");
                        }
                    } else {
                        System.out.println("CodiceCorso deve essere di 5 caratteri. Riprovare.");
                    }
                } catch (OperationException oE) {
                    System.out.println(oE.getMessage());
                    System.out.println("Errore nell'acquisizione di dati, riprovare..");
                } catch (Exception e) {
                    System.out.println("Errore inaspettato, riprovare..");
                }
            }
	
            boolean matricolaValida = false;
            while (!matricolaValida) {
            	System.out.println("Inserisci matricola del docente da associare al corso");
    			docente = scan.nextLine();
                try {
                	if (docente.length() == 7) {
                        if (docente.matches("[a-zA-Z0-9]+")) {
                            if (gestoreCorsiDiStudioConservatorio.controlloDocente(docente)) {
                                matricolaValida = true;
                            } else {
                                System.out.println("Docente specificato non esiste nel database. Riprovare.");
                            }
                        } else {
                            System.out.println("Matricola deve essere senza simboli. Riprovare.");
                        }
                    } else {
                        System.out.println("Matricola deve essere di 7 caratteri. Riprovare.");
                    }
                } catch (OperationException oE) {
                    System.out.println(oE.getMessage());
                    System.out.println("Errore nell'acquisizione di dati, riprovare..");
                } catch (Exception e) {
                    System.out.println("Errore inaspettato, riprovare..");
                }
            }
			
			gestoreCorsiDiStudioConservatorio.AssociazioneDocenteCorso(corso, docente);
		}catch (OperationException oE) {
			System.out.println(oE.getMessage());
			System.out.println("Riprovare..\n");
		} catch (Exception e) {
			System.out.println("Unexpected exception, riprovare..");
			System.out.println();
		}
	}
	
	private static void AggiungiDocente() {
        GestoreCorsiDiStudioConservatorio gestoreCorsiDiStudioConservatorio = GestoreCorsiDiStudioConservatorio.getInstance();
        try {
            System.out.println("Nome docente:");
            String nome = scan.nextLine();
            System.out.println("cognome:");
            String cognome = scan.nextLine();
            System.out.println("matricola:");
            String matricola = scan.nextLine();
            gestoreCorsiDiStudioConservatorio.createAndInsertDocente(nome, cognome, matricola);
        }catch (OperationException oE) {
			System.out.println(oE.getMessage());
			System.out.println("Riprovare..\n");
		} catch (Exception e) {
			System.out.println("Unexpected exception, riprovare..");
			System.out.println();
		}
	}
	
	private static void AggiungiCorso() {
	    GestoreCorsiDiStudioConservatorio gestoreCorsiDiStudioConservatorio = GestoreCorsiDiStudioConservatorio.getInstance();
	    String propDi = "";
	    String propA = "";
	    //String propDi = null;
		//String propA = null;
	    try {
	        System.out.println("Codice corso:");
	        String codiceCorso = scan.nextLine();
	        System.out.println("Denominazione:");
	        String denominazione = scan.nextLine();
	        System.out.println("CFU:");
	        int CFU = Integer.parseInt(scan.nextLine());

	        System.out.println("Vuoi aggiungere propedeuticità di? (yes/no):");
	        String risposta = scan.nextLine();
	        if (risposta.equalsIgnoreCase("yes")) {
	            boolean continuaAggiunta = true;
	            while (continuaAggiunta) {
	                System.out.println("Inserisci il codice del corso propedeutico:");
	                String codicePropedeutico = scan.nextLine();
	                if (!propDi.isEmpty()) {
	                    propDi += " ";
	                }
	                propDi += codicePropedeutico;

	                System.out.println("Vuoi aggiungere un altro corso propedeutico? (yes/no):");
	                risposta = scan.nextLine();
	                continuaAggiunta = risposta.equalsIgnoreCase("yes");
	            }
	        }

	        System.out.println("Vuoi aggiungere propedeuticità a? (yes/no):");
	        risposta = scan.nextLine();
	        if (risposta.equalsIgnoreCase("yes")) {
	            boolean continuaAggiunta = true;
	            while (continuaAggiunta) {
	                System.out.println("Inserisci il codice del corso propedeutico:");
	                String codicePropedeutico = scan.nextLine();
	                if (!propA.isEmpty()) {
	                    propA += " ";
	                }
	                propA += codicePropedeutico;

	                System.out.println("Vuoi aggiungere un altro corso propedeutico? (yes/no):");
	                risposta = scan.nextLine();
	                continuaAggiunta = risposta.equalsIgnoreCase("yes");
	            }
	        }

	        gestoreCorsiDiStudioConservatorio.createAndInsertCorso(codiceCorso, denominazione, CFU, propDi, propA);
	    } catch (OperationException oE) {
	        System.out.println(oE.getMessage());
	        System.out.println("Riprovare..\n");
	    } catch (Exception e) {
	        System.out.println("Errore inaspettato, riprovare..");
	        System.out.println();
	    }
	}


}
	