package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import com.github.cliftonlabs.json_simple.*;
import java.util.ArrayList;

public class DAOUtility {
    
    public static final int TERMID_FA24 = 1;
    
    public static String getResultSetAsJson(ResultSet rs) {
        
        JsonArray records = new JsonArray();
        
        try {
        
            if (rs != null) {
                 ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                
                while (rs.next()){
                    JsonObject jsonObject = new JsonObject();
                    for(int i = 1; i <= columnCount; i++){
                        String columnName = rsmd.getColumnName(i);
                        Object columnValue = rs.getObject(i);
                        jsonObject.put(columnName, columnValue);
                    }
                    records.add(jsonObject);
                }
               

            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return Jsoner.serialize(records);
        
    }
    
}
