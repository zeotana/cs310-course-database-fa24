package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;

public class RegistrationDAO {
    
    private final DAOFactory daoFactory;
    
    RegistrationDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
   public boolean create(int studentid, int termid, int crn) {

    boolean result = false;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {

        Connection conn = daoFactory.getConnection();

        if (conn.isValid(0)) {

            String query = "INSERT INTO registration (studentid, termid, crn) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, studentid);
            ps.setInt(2, termid);
            ps.setInt(3, crn);

            int updateCount = ps.executeUpdate();

            if (updateCount > 0) {
                rs = ps.getGeneratedKeys();
                result = true; // Indicate successful insertion
            }

        }

    } 
    catch (SQLException e) { e.printStackTrace(); } 
    finally {
        if (rs != null) { try { rs.close(); } catch (SQLException e) { e.printStackTrace(); } }
        if (ps != null) { try { ps.close(); } catch (SQLException e) { e.printStackTrace(); } }
    }

    return result;
}

    public boolean delete(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                String query = "DELETE FROM registration WHERE studentid = ? AND termid = ? AND crn = ?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
                // execute update
                int affectedRows = ps.executeUpdate();
               result = (affectedRows > 0);
            
               
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
    public boolean delete(int studentid, int termid) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            
           conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                String query = "DELETE FROM registration WHERE studentid = ? AND termid = ?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                
                // execute update
                int updateCount = ps.executeUpdate();
                if (updateCount > 0) {
                result = true;
            }
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }if (conn != null) {
            try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        
        return result;
        
    }

    public String list(int studentid, int termid) {
        
        String result = null; 
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                String sql = "SELECT course_name, grade FROM enrollments WHERE student_id = ? AND term_id = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                rs = ps.executeQuery();
                
                result = DAOUtility.getResultSetAsJson(rs);

            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
}
