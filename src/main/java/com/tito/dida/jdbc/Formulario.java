/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tito.dida.jdbc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;


/**
 *
 * @author ferna
 */
public class Formulario extends Application{
  
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent contenedor = new FXMLLoader().load(getClass().getResource("FormularioDepartamento.fxml"));
        Scene escena = new Scene (contenedor,500,700);
        escena.getStylesheets().addAll(getClass().getResource("estilos.css").toExternalForm());
        primaryStage.setScene(escena);
        primaryStage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}
