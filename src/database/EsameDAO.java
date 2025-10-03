package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import entity.EntityEsame;
import entity.EntityStudente;
import entity.EntityVerbale;
import exception.DAOException;
import exception.PropedeuticitaException;
import exception.DBConnectionException;

public class EsameDAO {
public static void createEsame(EntityEsame eE) throws DAOException, DBConnectionException {
    try {
        Connection conn = DBManager.getConnection();

        String query = "INSERT INTO ESAME VALUES (?, ?, ?, ?, ?, ?, ?);";
		
        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, eE.getVoto());
            stmt.setBoolean(2, eE.getLode());
            stmt.setString(3, eE.getNoteDocente());
            stmt.setDate(4, null);
            stmt.setString(5, eE.getCodiceVerbale());
            stmt.setString(6, eE.getCodiceCorso());
            stmt.setString(7, eE.getUsername());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Errore scrittura esame");
        } finally {
            DBManager.closeConnection();
        }

    } catch (SQLException e) {
        throw new DBConnectionException("Errore connessione database");
    }
}

public static List<EntityEsame> readEsame(String codiceVerbale) throws DAOException, DBConnectionException {

	List<EntityEsame> esami = new ArrayList<>();

	try {

		Connection conn = DBManager.getConnection();
		String query = "SELECT * FROM ESAME WHERE CODICEVERBALE=?;";
		
		try {

			PreparedStatement stmt = conn.prepareStatement(query);

			stmt.setString(1, codiceVerbale);

			ResultSet result = stmt.executeQuery();

			while(result.next()) {
				EntityEsame esame = new EntityEsame(result.getInt(1), result.getBoolean(2), result.getString(3), result.getDate(4), codiceVerbale, result.getString(6), result.getString(7));
				esami.add(esame);
			}

		}catch(SQLException e) {
			throw new DAOException("Errore lettura esame");
		}finally {
			DBManager.closeConnection();
		}
		
	}catch(SQLException e) {
		throw new DBConnectionException("Errore di connessione DB");
	}

	return esami;
}

public static List<EntityEsame> readEsamiPassati(Connection conn, String username) throws DAOException, DBConnectionException {

	List<EntityEsame> esami = new ArrayList<>();
	
	//try {

		//Connection conn = DBManager.getConnection();
		String query = "SELECT * FROM ESAME WHERE USERNAME=? AND DATASUPERAMENTO IS NOT NULL;";

		try {
	
			PreparedStatement stmt = conn.prepareStatement(query);

			stmt.setString(1, username);

			ResultSet result = stmt.executeQuery();

			while(result.next()) {
				EntityEsame esame = new EntityEsame(result.getInt(1), result.getBoolean(2), result.getString(3), result.getDate(4), result.getString(5), result.getString(6), username);
				esami.add(esame);
			}

		}catch(SQLException e) {
			throw new DAOException("Errore lettura esami dello studente");
		}/*finally {
			DBManager.closeConnection();
		}*/
		
	/*}catch(SQLException e) {
		throw new DBConnectionException("Errore di connessione DB");
	}*/

	return esami;
}

public static List<String> getUsernamesByVerbale(String codiceVerbale) throws DAOException, DBConnectionException {
    List<String> usernames = new ArrayList<>();
    try {
        Connection conn = DBManager.getConnection();
        String query = "SELECT USERNAME FROM ESAME WHERE CODICEVERBALE = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, codiceVerbale);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                usernames.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            throw new DAOException("Errore recupero usernames");
        } finally {
            DBManager.closeConnection();
        }
    } catch (SQLException e) {
        throw new DBConnectionException("Errore di connessione DB");
    }
    return usernames;
}

public static void controlloVoti(String codiceVerbale) throws DAOException, DBConnectionException {     
	  try {  
		  EntityVerbale verbale = VerbaleDAO.readVerbale(codiceVerbale);
		  Connection conn = DBManager.getConnection();  
		  // Recuperiamo tutti gli esami con data superamento null e codice verbale specificato      
		  String querySelect = "SELECT * FROM ESAME WHERE CODICEVERBALE = ?;";   
		  try {
		  PreparedStatement stmtSelect = conn.prepareStatement(querySelect);     
		  stmtSelect.setString(1, codiceVerbale); 
		  ResultSet result = stmtSelect.executeQuery();        
		  while(result.next()) {     
			  int voto = result.getInt(1);            
			  boolean lode = result.getBoolean(2);   
			  String username = result.getString(7);    
			  // Controlliamo i criteri per eliminare gli esami              
			  if (voto < 18 || (lode && voto != 30) || voto>30) {        
				  String queryDelete = "DELETE FROM ESAME WHERE CODICEVERBALE = ? AND USERNAME = ?;";        
				  PreparedStatement stmtDelete = conn.prepareStatement(queryDelete);    
				  stmtDelete.setString(1, codiceVerbale);                   
				  stmtDelete.setString(2, username); 
				  stmtDelete.executeUpdate();          
				  }  
			  } 
		    java.util.Date dataVerbaleUtil = verbale.getDataVerbale();
		    Date dataVerbaleSql = new Date(dataVerbaleUtil.getTime());
		    String queryUpdate = "UPDATE ESAME SET DATASUPERAMENTO = ? WHERE CODICEVERBALE = ?;";
		        PreparedStatement stmtUpdate = conn.prepareStatement(queryUpdate);
		        stmtUpdate.setDate(1, dataVerbaleSql); 
		        stmtUpdate.setString(2, codiceVerbale);
		        stmtUpdate.executeUpdate();
		  }  catch (SQLException e) {
		            throw new DAOException("Errore durante l'aggiornamento degli esami");
		        } finally {
		            DBManager.closeConnection();
		        }

		    } catch (SQLException e) {
		        throw new DBConnectionException("Errore connessione database");
		    }
}

public static void controlloPropedeuticita(String codiceVerbale, String username) throws DAOException, DBConnectionException, PropedeuticitaException {
    try {
    	Connection conn = DBManager.getConnection();
        String querySelect = "SELECT * FROM ESAME WHERE CODICEVERBALE = ? AND USERNAME = ?;";
        try {
        	PreparedStatement stmtSelect = conn.prepareStatement(querySelect);
            stmtSelect.setString(1, codiceVerbale);
            stmtSelect.setString(2, username);
            ResultSet result = stmtSelect.executeQuery();

            if(result.next()) {
                String codiceCorso = result.getString(6);
                
                List<EntityEsame> esamiPassati = readEsamiPassati(conn, username);
                
                String propDiString = CorsoDAO.propDi(conn, codiceCorso);
                
                //niente propedeuticita
                if (propDiString == null || propDiString.trim().isEmpty()) {
                    return;
                }
                
                boolean allPropedeuticiPresenti = true;
                String[] codiciCorsi = propDiString.split(" ");
              
                for (String codice : codiciCorsi) {
                    boolean trovato = false;
                    for (EntityEsame esamePassato : esamiPassati) {
                        if (esamePassato.getCodiceCorso().equals(codice)) {
                            trovato = true;
                            break;
                        }
                    }
                    if (!trovato) {
                        allPropedeuticiPresenti = false;
                        break;
                    }
                }

                if (!allPropedeuticiPresenti) {
                	throw new PropedeuticitaException("Le propedeuticità non sono rispettate");
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Errore durante il controllo dei prerequisiti degli esami");
        } finally {
            DBManager.closeConnection();
        }
    } catch (SQLException e) {
        throw new DBConnectionException("Errore di connessione al DB");
    }
}


public static void controlloPIN(int PinInserito, String codiceVerbale, String username) throws DAOException, DBConnectionException {
    try {
    	EntityStudente studente = StudenteDAO.readStudente(username);
        int pinStudente = studente.getPIN();
        Connection conn = DBManager.getConnection();
        String querySelect = "SELECT * FROM ESAME WHERE USERNAME = ? AND CODICEVERBALE = ?;";
        try {
            PreparedStatement stmtSelect = conn.prepareStatement(querySelect);
            stmtSelect.setString(1, username);
            stmtSelect.setString(2, codiceVerbale);
            ResultSet rs = stmtSelect.executeQuery();
            if (rs.next()) {
                if (pinStudente != PinInserito) {
                    String queryDelete = "DELETE FROM ESAME WHERE USERNAME = ? AND CODICEVERBALE = ?;";
                    PreparedStatement stmtDelete = conn.prepareStatement(queryDelete);
                    stmtDelete.setString(1, username);
                    stmtDelete.setString(2, codiceVerbale);
                    System.out.println("Eliminato esame dello studente " + username + " per errato Pin");
                    stmtDelete.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Errore durante il controllo dei PIN e l'eliminazione degli esami");
        } finally {
            DBManager.closeConnection();
        }
    } catch (SQLException e) {
        throw new DBConnectionException("Errore connessione database");
    }
}

public static void eliminaEsame(String codiceVerbale, String username) throws DAOException, DBConnectionException {
    try {
        Connection conn = DBManager.getConnection();
        String queryDelete = "DELETE FROM ESAME WHERE USERNAME = ? AND CODICEVERBALE = ?;";
        try {
            PreparedStatement stmtDelete = conn.prepareStatement(queryDelete);
            stmtDelete.setString(1, username);
            stmtDelete.setString(2, codiceVerbale);
            stmtDelete.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Errore durante l'eliminazione dell'esame");
        } finally {
            DBManager.closeConnection();
        }
    } catch (SQLException e) {
        throw new DBConnectionException("Errore di connessione al DB");
    }
}

}