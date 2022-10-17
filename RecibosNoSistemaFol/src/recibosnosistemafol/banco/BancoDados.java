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
import javax.swing.JOptionPane;


/**
 *
 * @author jacks
 */
public class BancoDados {
    String connectionUrl;
    
    public BancoDados(String endeBanco, String database, String user, String senha){
        
        System.out.println("teste");
        connectionUrl
                
                = String.format("jdbc:sqlserver://%s:1433;" 
                + "database=%s;"
                + "user=%s;"
                + "password=%s;"
                + "encrypt=false;"
                + "trustServerCertificate=false;"
                + "loginTimeout=30;", endeBanco, database, user, senha);
        
        System.out.println(connectionUrl);
    }
      
                
        
    
    public void update(String query){
            try ( Connection connection = DriverManager.getConnection(connectionUrl);  PreparedStatement prepsInsertProduct = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
                prepsInsertProduct.execute();
               System.out.println(query);
                connection.close();  
                JOptionPane.showMessageDialog(null, "Adicionado com sucesso!");
            } catch (SQLException e) {             
            }
        }
    
}
