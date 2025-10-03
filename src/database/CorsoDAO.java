package database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.EntityCorso;
import exception.DAOException;
import exception.DBConnectionException;

public class CorsoDAO {
	public static EntityCorso readCorso(String codiceCorso) throws DAOException, DBConnectionException {

		EntityCorso eC = null;

		try {

			Connection conn = DBManager.getConnection();
			String query = "SELECT * FROM CORSO WHERE CODICECORSO=?;";
			
			try {

				PreparedStatement stmt = conn.prepareStatement(query);

				stmt.setString(1, codiceCorso);

				ResultSet result = stmt.executeQuery();

				if(result.next()) {
					eC = new EntityCorso(codiceCorso, result.getString(2), result.getInt(3), result.getString(4),result.getString(5),result.getString(6));
				}

			}catch(SQLException e) {
				throw new DAOException("Errore lettura corso");
			}finally {
				DBManager.closeConnection();
			}
			
		}catch(SQLException e) {
			throw new DBConnectionException("Errore di connessione DB");
		}

		return eC;
	}
	
	public static void createCorso(EntityCorso eC) throws DAOException, DBConnectionException {
	    try {
	        Connection conn = DBManager.getConnection();

	        String query = "INSERT INTO CORSO VALUES (?, ?, ?, ?, ?, ?);";
			
	        try {
	            PreparedStatement stmt = conn.prepareStatement(query);

	            stmt.setString(1, eC.getCodiceCorso());
                stmt.setString(2, eC.getDenominazione());
                stmt.setInt(3, eC.getCFU());
                stmt.setString(4, null);
                stmt.setString(5, eC.getPropDi());
                stmt.setString(6, eC.getPropA());

	            stmt.executeUpdate();

	        } catch (SQLException e) {
	            throw new DAOException("Errore scrittura corso");
	        } finally {
	            DBManager.closeConnection();
	        }

	    } catch (SQLException e) {
	        throw new DBConnectionException("Errore connessione database");
	    }
	}

	public static String propDi(Connection conn, String codiceCorso) throws DAOException, DBConnectionException {
	    String propDi = null;

	    //try (Connection conn = DBManager.getConnection()) {
	        String query = "SELECT PROPDI FROM CORSO WHERE CODICECORSO = ?;";
	        try (PreparedStatement stmt = conn.prepareStatement(query)) {
	            stmt.setString(1, codiceCorso);
	            ResultSet result = stmt.executeQuery();
	            if (result.next()) {
	                propDi = result.getString("propDi"); 
	                System.out.println(propDi);
	            }
	        } catch (SQLException e) {
	            throw new DAOException("Errore durante il recupero dei corsi propedeutici per l'esame");
	        }
	   /* } catch (SQLException e) {
	        throw new DBConnectionException("Errore di connessione DB");
	    }*/

	    return propDi;
	}

public static String readAssociazioneDocenteCorso(String codiceCorso) throws DAOException, DBConnectionException {
        String matricolaDocente = null;

        try {
            Connection conn = DBManager.getConnection();
            String query = "SELECT MATRICOLADOCENTE FROM CORSO WHERE CODICECORSO = ?;";
            try {
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, codiceCorso);

                ResultSet result = stmt.executeQuery();

                if (result.next()) {
                    matricolaDocente = result.getString("matricolaDocente");
                }
            } catch (SQLException e) {
                throw new DAOException("Errore lettura associazione docente-corso");
            } finally {
                DBManager.closeConnection();
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Errore di connessione DB");
        }

        return matricolaDocente;
    } 
    
public static void updateAssociazioneDocenteCorso(String codiceCorso, String matricolaDocente) throws DAOException, DBConnectionException {
    try {
        Connection conn = DBManager.getConnection();
        String query = "UPDATE CORSO SET MATRICOLADOCENTE = ? WHERE CODICECORSO = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, matricolaDocente);
            stmt.setString(2, codiceCorso);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Errore durante l'aggiornamento dell'associazione del corso al docente");
        } finally {
            DBManager.closeConnection();
        }
    } catch (SQLException e) {
        throw new DBConnectionException("Errore di connessione al DB");
    }
  }
}
 
            
                
        
