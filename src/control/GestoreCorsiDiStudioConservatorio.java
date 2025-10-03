package control;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

import database.StudenteDAO;
import database.DocenteDAO;
import database.CorsoDAO;
import database.DBManager;
import database.VerbaleDAO;
import database.EsameDAO;
import entity.EntityEsame;
import entity.EntityDocente;
import entity.EntityCorso;
import entity.EntityVerbale;
import entity.EntityStudente;
import exception.DAOException;
import exception.PropedeuticitaException;
import exception.DBConnectionException;
import exception.OperationException;

public class GestoreCorsiDiStudioConservatorio {
	private static GestoreCorsiDiStudioConservatorio gCDSC = null;
	
	public static GestoreCorsiDiStudioConservatorio getInstance() 
	{ 
		if (gCDSC == null) 
			gCDSC = new GestoreCorsiDiStudioConservatorio(); 

		return gCDSC; 
	}
  
	public void AssociazioneDocenteCorso(String codiceCorso, String matricolaDocente) throws OperationException{
		EntityDocente eD = null;
		EntityCorso eC = null;
		
		try {
			eD = DocenteDAO.readDocente(matricolaDocente);
			eC = CorsoDAO.readCorso(codiceCorso);
			if(eD == null || eC == null) {
				throw new OperationException("Teacher or Course not found");
			}
			eC.matricolaDocente=CorsoDAO.readAssociazioneDocenteCorso(codiceCorso);
			
		   if(eC.matricolaDocente != null) {
				throw new OperationException("Course already assigned");
			}
		   CorsoDAO.updateAssociazioneDocenteCorso(codiceCorso, matricolaDocente);
		}catch(DBConnectionException dbEx) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
	}
		
	public void createAndInsertEsame(int voto, boolean lode, String noteDocente, String codiceVerbale, String codiceCorso, String username) throws OperationException {
		try {
		EntityEsame esame = new EntityEsame(voto, lode, noteDocente, null, codiceVerbale, codiceCorso, username);
        EsameDAO.createEsame(esame);
		}catch(DBConnectionException dbEx) {
	        throw new OperationException("\nInternal application problem encountered!\n");
	    } catch(DAOException ex) {
	        throw new OperationException("Oops, something went wrong...");
	    }
    }
	
	public void createAndInsertCorso(String codiceCorso, String denominazione, int CFU, String propDi, String propA) throws OperationException {
	    try {
	        EntityCorso corso = new EntityCorso(codiceCorso, denominazione, CFU, null, propDi, propA);
	        CorsoDAO.createCorso(corso);
	    } catch (DBConnectionException dbEx) {
	        throw new OperationException("\nInternal application problem encountered!\n");
	    } catch (DAOException ex) {
	        throw new OperationException("Oops, something went wrong...");
	    }
	}

	public void createAndInsertDocente(String nomeDocente, String cognomeDocente, String matricola) throws OperationException {
		try {
		EntityDocente docente = new EntityDocente(nomeDocente, cognomeDocente, matricola);
        DocenteDAO.createDocente(docente);
		}catch(DBConnectionException dbEx) {
	        throw new OperationException("\nInternal application problem encountered!\n");
	    } catch(DAOException ex) {
	        throw new OperationException("Oops, something went wrong...");
	    }
    }
	
	public void AperturaVerbale(String codiceVerbale, Date dataVerbale, String matricola) throws OperationException {
	    try {
	        EntityVerbale eB = new EntityVerbale(dataVerbale, codiceVerbale, matricola);
	        VerbaleDAO.createVerbale(eB);
	    } catch(DBConnectionException dbEx) {
	        throw new OperationException("\nInternal application problem encountered!\n");
	    } catch(DAOException ex) {
	        throw new OperationException("Oops, something went wrong...");
	    }
	}
	
	public void verificaVerbale(String codiceVerbale) throws OperationException {    
		EntityVerbale eV = null;
		try {
		    eV = VerbaleDAO.readVerbale(codiceVerbale);
		
		    if(eV == null) {
			   throw new OperationException("Report not opened");
		    }
		    
		    List<EntityEsame> esami = EsameDAO.readEsame(codiceVerbale);
            for (EntityEsame esame : esami) {
               if (esame.getDataSuperamento() != null) {
                  throw new OperationException("Report already closed");
               }
            }
		}catch(DBConnectionException dbEx) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
	}
	
	public boolean controlloStudente(String username) throws OperationException {
		EntityStudente eS = null;
	    try {
	    	eS = StudenteDAO.readStudente(username);
	        if(eS == null) {
				   return false;
			    }
	       return true;
	    } catch(DBConnectionException dbEx) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
	}
	
	public boolean controlloCorso(String codiceCorso) throws OperationException {
		EntityCorso eC = null;
	    try {
	    	eC = CorsoDAO.readCorso(codiceCorso);
	        if(eC == null) {
				   return false;
			    }
	       return true;
	    } catch(DBConnectionException dbEx) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
	}
	
	public boolean controlloDocente(String matricola) throws OperationException {
		EntityDocente eD = null;
	    try {
	    	eD = DocenteDAO.readDocente(matricola);
	        if(eD == null) {
				   return false;
			    }
	       return true;
	    } catch(DBConnectionException dbEx) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
	}
	
	public List<String> getUsernamesByVerbale(String codiceVerbale) throws OperationException {
	    try {
	        return EsameDAO.getUsernamesByVerbale(codiceVerbale);
	    } catch(DBConnectionException dbEx) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
	}
	
	public void controlloPIN(int pinInserito, String codiceVerbale, String username) throws OperationException {
	    try {
	        EsameDAO.controlloPIN(pinInserito, codiceVerbale, username);
	    } catch(DBConnectionException dbEx) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
	}

	public void ChiusuraVerbale1(String codiceVerbale, String username) throws OperationException{
        try {
           EsameDAO.controlloPropedeuticita(codiceVerbale, username);
        } catch (PropedeuticitaException pEx) {
	    System.out.println(pEx.getMessage());
	    try {
			EsameDAO.eliminaEsame(codiceVerbale, username);
		} catch (DAOException e) {
			throw new OperationException("Oops, something went wrong...");
		} catch (DBConnectionException e) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}
	    }
        catch(DBConnectionException dbEx) {
			throw new OperationException("\nInternal application problem encountered!\n");
		}catch(DAOException ex) {
			throw new OperationException("Oops, something went wrong...");
		}
    }
	
    public void ChiusuraVerbale2(String codiceVerbale) throws OperationException{
            try {
               EsameDAO.controlloVoti(codiceVerbale);
            } catch(DBConnectionException dbEx) {
    			throw new OperationException("\nInternal application problem encountered!\n");
    		}catch(DAOException ex) {
    			throw new OperationException("Oops, something went wrong...");
    		}
    }

}
