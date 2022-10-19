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
    ResultSet resultSet = null;

    public BancoDados(String servidor, String database, String user, String senha) {

        connectionUrl
                = String.format("jdbc:sqlserver://%s:1433;"
                        + "database=%s;"
                        + "user=%s;"
                        + "password=%s;"
                        + "encrypt=false;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;", servidor, database, user, senha);

        System.out.println(connectionUrl);
    }


    public String select(String query, int quantColunas) {
        String resultado = "";
        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {

            String selectSql = query;
            resultSet = statement.executeQuery(selectSql);

            while (resultSet.next()) {
                for (int i = 1; i <= quantColunas; i++) {
                    resultado += resultSet.getString(i) + ",";

                }

                resultado += "\n";

            }
            System.out.println(selectSql);
            connection.close();

            return resultado;
        } catch (SQLException e) {
        }

        return null;
    }

    public void update(String query) {
        try ( Connection connection = DriverManager.getConnection(connectionUrl);  PreparedStatement prepsInsertProduct = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            prepsInsertProduct.execute();
            System.out.println(query);
            connection.close();
            JOptionPane.showMessageDialog(null, "Adicionado com sucesso!");
        } catch (SQLException e) {
        }
    }

}
