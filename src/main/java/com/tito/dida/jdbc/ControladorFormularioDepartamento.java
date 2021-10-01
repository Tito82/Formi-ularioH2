/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tito.dida.jdbc;

import java.sql.Connection;
import java.sql.Statement;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ferna
 */
public class ControladorFormularioDepartamento implements Initializable {
    
    @FXML
    TextField nombre;
    
    @FXML
    TextArea descripcion;
   

    public void guardar(){
        
        try(Connection conexionDataBase = DriverManager.getConnection("jdbc:h2:./departamentosDB","root","")){
        Statement statement = conexionDataBase.createStatement();
        String sql = "INSERT INTO departamento(nombre ,descripcion)VALUES('"+nombre.getText()+"','"+ descripcion.getText()+"')";
        statement.executeUpdate(sql);
        System.out.println("informacion guardada en la base de datos H2");
        
        sql = "SELECT COUNT(*) AS cantidad FROM departamento";
        ResultSet resultadoConsulta = statement.executeQuery(sql);
        if(resultadoConsulta.next()){
            System.out.println("Departamentos guardados: "+ resultadoConsulta.getInt("cantidad"));
        }
        }catch(Exception e){
                e.printStackTrace();
        }
    
        nombre.clear();
        descripcion.clear();
       
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      try(Connection conexionDataBase = DriverManager.getConnection("jdbc:h2:./departamentosDB","root","")){
        Statement statement = conexionDataBase.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS departamento"+
                "(id INTEGER auto_increment,"+
                "nombre VARCHAR(255),"+
                "descripcion VARCHAR(255))";
        statement.executeUpdate(sql);
      }catch (Exception e){
          e.printStackTrace();
      }
    }    
    
}
