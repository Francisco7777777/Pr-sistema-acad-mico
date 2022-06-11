
package Model.dao.impl;

import Model.Professor;
import Modeo.dao.ProfessorDAO;
import dao.DB;
import dao.DbException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.List;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Francisco Mendonça
 */
public class ProfessorDaoJDBC implements ProfessorDAO{
    
    private Connection conn = null;
    
    public ProfessorDaoJDBC(Connection conn) {
	this.conn = conn;
    }

    
    @Override
    public void insert(Professor obj) {
        
        PreparedStatement st = null;
        
        try {
            st = conn.prepareStatement(
                "INSERT INTO professor (num_registro, nome, email, senha, adiministrador)"
                + " VALUES (?, ?, ?, ?, ?)");

            st.setInt(1, obj.getNumRegistro());
            st.setString(2, obj.getNome());
            st.setString(3, obj.getEmail());
            st.setString(4, obj.getSenha());
            st.setBoolean(5, obj.getAdiministrador());

            int l = st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage()); 
        }
        finally {
            DB.closeStatement(st);
        }
    }

    
    @Override
    public void update(Professor obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                "UPDATE professor SET num_registro = ?, nome = ?, email = ?, senha = ?,"
                + " adiministrador = ? WHERE num_registro = ?");

            st.setInt(1, obj.getNumRegistro());
            st.setString(2, obj.getNome());
            st.setString(3, obj.getEmail());
            st.setString(4, obj.getSenha());
            st.setBoolean(5, obj.getAdiministrador());
            st.setInt(6, obj.getNumRegistro());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage()); 
        }
        finally {
            DB.closeStatement(st);
        }
    }

    
    @Override
    public void deletePorNumR(Integer numR) {
        
        PreparedStatement st = null;
		
        try {
            st = conn.prepareStatement("DELETE FROM professor WHERE num_registro = ?");
            st.setInt(1, numR);
            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    
    @Override
    public List<Professor> listarProf() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT * FROM professor");
            rs = st.executeQuery();             
            List<Professor> lista = new ArrayList<>();
            
            while(rs.next()) {
                Professor obj = new Professor();
                obj.setNumRegistro(rs.getInt(1));
                obj.setNome(rs.getString(2));
                obj.setEmail(rs.getString(3));
                obj.setSenha(rs.getString(4));
                obj.setAdiministrador(rs.getBoolean(5));
                
                lista.add(obj);
            }
            return lista;
        } 
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

}
