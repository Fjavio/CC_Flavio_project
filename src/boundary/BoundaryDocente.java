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

	public static void main(String[] args) {		
		boolean exit = false;
		
		while(!exit) {
			System.out.println("Docente");
			System.out.println("1. AperturaVerbale");
			System.out.println("2. ChiusuraVerbale");
			System.out.println("3. Esci");
			
			String op = scan.nextLine();

			if (op.equals("1")) {
                AperturaVerbale();
            } else if (op.equals("2")) {
                    ChiusuraVerbale();
                } else if (op.equals("3")) {
                exit = true;
            } else {
                System.out.println("Operazione non disponibile");
                System.out.println();
            }
        }
		
		System.out.println("Arrivederci");
		
	}
	
            private static void AperturaVerbale() {
                GestoreCorsiDiStudioConservatorio gestoreCorsiDiStudioConservatorio = GestoreCorsiDiStudioConservatorio.getInstance();
                String codiceVerbale = null;
                String matricola = null;
                String codiceCorso = null;
                String username = null;
                Date data = null;
                try {
                	boolean verbaleValido = false;
                    while (!verbaleValido) {
                    	System.out.println("Inserisci il verbale che vuoi creare");
                        codiceVerbale = scan.nextLine();
                        try {
                        	if (codiceVerbale.length() == 5) {
                                if (codiceVerbale.matches("[a-zA-Z0-9]+")) {
                                        verbaleValido = true;
                                } else {
                                    System.out.println("Verbale deve essere senza simboli. Riprovare.");
                                }
                            } else {
                                System.out.println("Verbale deve essere di 5 caratteri. Riprovare.");
                            }
                        } catch (Exception e) {
                            System.out.println("Errore inaspettato, riprovare..");
                        }
                    }
                    
                    boolean dataValida = false;
                    while (!dataValida) {
        				try {
        					System.out.println("Inserisci la data (aaaa-MM-gg)");
        					String dataTemp = scan.nextLine();
        					data = Date.valueOf(dataTemp);

        					dataValida = true;
        				} catch (IllegalArgumentException iE) {
        					System.out.println("Errore nell'acquisizione della data, riprovare..");
        					System.out.println();
        				}
        			}
					
                    boolean matricolaValida = false;
                    while (!matricolaValida) {
                        System.out.println("Inserisci la tua matricola");
                        matricola = scan.nextLine();
                        try {
                        	if (matricola.length() == 7) {
                                if (matricola.matches("[a-zA-Z0-9]+")) {
                                    if (gestoreCorsiDiStudioConservatorio.controlloDocente(matricola)) {
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
                    
                    gestoreCorsiDiStudioConservatorio.AperturaVerbale(codiceVerbale, data, matricola);
                    System.out.println("Verbale " + codiceVerbale + " creato correttamente.");
                    
                    boolean inserisciAltroEsame = true;
                    while (inserisciAltroEsame) {
                        System.out.println("Inserisci i dettagli dell'esame:");
                        System.out.println("Voto:");
                        int voto = Integer.parseInt(scan.nextLine());
                        System.out.println("Lode (true/false):");
                        boolean lode = Boolean.parseBoolean(scan.nextLine());
                        System.out.println("Note docente:");
                        String noteDocente = scan.nextLine();
                        boolean corsoValido = false;
                        while (!corsoValido) {
                        	System.out.println("Codice Corso:");
                            codiceCorso = scan.nextLine();
                            try {
                            	if (codiceCorso.length() == 5) {
                                    if (codiceCorso.matches("[a-zA-Z0-9]+")) {
                                        if (gestoreCorsiDiStudioConservatorio.controlloCorso(codiceCorso)) {
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
                        
                        boolean usernameValido = false;
                        while (!usernameValido) {
                        	 System.out.println("Username:");
                        	 username = scan.nextLine();
                            try {
                                if (gestoreCorsiDiStudioConservatorio.controlloStudente(username)) {
                                      usernameValido = true;
                                        } else {
                                            System.out.println("Studente specificato non esiste nel database. Riprovare.");
                                        }
                            } catch (OperationException oE) {
                                System.out.println(oE.getMessage());
                                System.out.println("Errore nell'acquisizione di dati, riprovare..");
                            } catch (Exception e) {
                                System.out.println("Errore inaspettato, riprovare..");
                            }
                        }
                       
                        try {
                            gestoreCorsiDiStudioConservatorio.createAndInsertEsame(voto, lode, noteDocente, codiceVerbale, codiceCorso, username);
                            System.out.println("Esame aggiunto con successo.");
                            System.out.println("Vuoi inserire un altro esame? (yes/no):");
                            inserisciAltroEsame = scan.nextLine().equalsIgnoreCase("yes");
                        } catch (OperationException oE) {
                            System.out.println(oE.getMessage());
                            System.out.println("Riprovare..\n");
                        } catch (Exception e) {
                            System.out.println("Unexpected exception, riprovare..");
                            System.out.println();
                        }
                    }
                } catch (OperationException oE) {
                    System.out.println(oE.getMessage());
                    System.out.println("Riprovare..\n");
                } catch (Exception e) {
                    System.out.println("Unexpected exception, riprovare..");
                    System.out.println();
                }
            }

	public static void ChiusuraVerbale() { //public per fare JUNIT
        GestoreCorsiDiStudioConservatorio gestoreCorsiDiStudioConservatorio = GestoreCorsiDiStudioConservatorio.getInstance();
        String codiceVerbale = null; 
        int pinInserito = 0;
        try {
        	System.out.println("Inserisci il verbale che devi chiudere");
   		    codiceVerbale = scan.nextLine();
   		    gestoreCorsiDiStudioConservatorio.verificaVerbale(codiceVerbale);
            List<String> usernames = gestoreCorsiDiStudioConservatorio.getUsernamesByVerbale(codiceVerbale);
                for (String username : usernames) {
                	boolean pinValido = false;
                    while (!pinValido) {
                    	try {
                    	System.out.println("Inserisci il PIN per lo studente" + username + ":");
                        pinInserito = Integer.parseInt(scan.nextLine());
                        int length = String.valueOf(pinInserito).length();
                        try {
                        	if (length == 7) {
                                 pinValido = true;
                            } else {
                                System.out.println("Pin deve essere di 7 numeri. Riprovare.");
                            }
                        } catch (Exception e) {
                            System.out.println("Errore inaspettato, riprovare..");
                        }
                    	}catch (NumberFormatException nE) {
        					System.out.println("Errore, inserire un numero valido");
        					System.out.println();
        				}
                    }
                gestoreCorsiDiStudioConservatorio.controlloPIN(pinInserito, codiceVerbale, username);
                gestoreCorsiDiStudioConservatorio.ChiusuraVerbale1(codiceVerbale, username);
                }
                gestoreCorsiDiStudioConservatorio.ChiusuraVerbale2(codiceVerbale);
                System.out.println("Verbale chiuso con successo!");
        } catch (OperationException oE) {
			System.out.println(oE.getMessage());
			System.out.println("Riprovare..\n");
		} catch (Exception e) {
			System.out.println("Unexpected exception, riprovare..");
			System.out.println();
		}
    }
	
}


	