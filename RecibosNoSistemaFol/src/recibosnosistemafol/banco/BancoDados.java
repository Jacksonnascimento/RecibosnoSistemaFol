/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recibosnosistemafol.banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jacks
 */
public class BancoDados {
      String connectionUrl
                = "jdbc:sqlserver://localhost:1433;" 
                + "database=FPG_WEB_PM_ITAPETINGA;"
                + "user=sa;"
                + "password=87519023;"
                + "encrypt=false;"
                + "trustServerCertificate=false;"
                + "loginTimeout=30;";
    
    
    public void update(String query){
            try ( Connection connection = DriverManager.getConnection(connectionUrl);  PreparedStatement prepsInsertProduct = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
                prepsInsertProduct.execute();
               System.out.println(query);
                connection.close();
            } catch (SQLException e) {
            }
        }
    
}
