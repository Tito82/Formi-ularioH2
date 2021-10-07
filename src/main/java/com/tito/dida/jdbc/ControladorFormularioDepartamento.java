
package com.tito.dida.jdbc;
import java.sql.Connection;
import java.sql.Statement;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
    
    @FXML
    TableView<Departamento>tablaDepartamentos;
    
    int idDepartamentoSeleccionado = 0;
    
    public void editar(){
        Departamento departamento = tablaDepartamentos.getSelectionModel().getSelectedItem();
        nombre.setText(departamento.getNombre());
        descripcion.setText(departamento.getDescripcion());
        idDepartamentoSeleccionado = departamento.getId();
    }
   

    public void guardar(){
        
        try(Connection conexionDataBase = DriverManager.getConnection("jdbc:h2:./departamentosDB","root","")){
        Statement statement = conexionDataBase.createStatement();
        String sql= null;
        if(idDepartamentoSeleccionado ==0){
            sql = "INSERT INTO departamento(nombre ,descripcion)VALUES('"+nombre.getText()+"','"+ descripcion.getText()+"')";
        }else{
            sql = "UPDATE departamento set nombre='"+ nombre.getText()+ "',descripcion='" + descripcion.getText() + "'WHERE id=" +idDepartamentoSeleccionado;
            idDepartamentoSeleccionado = 0;
        }
        statement.executeUpdate(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
        cargarDepartamentosDeLaBase();
        nombre.clear();
        descripcion.clear();
    }
    
    public void eliminar(){
        Departamento departamento = tablaDepartamentos.getSelectionModel().getSelectedItem();
        try (Connection conexionDataBase = DriverManager.getConnection("jdbc:h2:./departamentosDB","root","")){
        Statement statement = conexionDataBase.createStatement();
        String sql = "DELETE FROM departamento WHERE id = " + departamento.getId();
        statement.executeUpdate(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
        cargarDepartamentosDeLaBase();
        
    }

  
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
      cargarDepartamentosDeLaBase();
      configurarTamanhoColumnas();
    }    

    private void cargarDepartamentosDeLaBase() {
    try(Connection conexionDataBase = DriverManager.getConnection("jdbc:h2:./departamentosDB","root","")){
        Statement statement = conexionDataBase.createStatement();
        String sql = "SELECT * FROM departamento";
        ResultSet resultSet = statement.executeQuery(sql);
        ObservableList<Departamento> departamentos = FXCollections.observableArrayList();
        while(resultSet.next()){
            Departamento departamento = new Departamento();
            departamento.setDescripcion(resultSet.getString("descripcion"));
            departamento.setNombre(resultSet.getString("nombre"));
            departamento.setId(resultSet.getInt("id"));
            departamentos.add(departamento);
        }
        tablaDepartamentos.setItems(departamentos);
         }catch (Exception e){
             e.printStackTrace();
        }
    }
    
    private void configurarTamanhoColumnas(){
        tablaDepartamentos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList<TableColumn<Departamento,?>> columnas = tablaDepartamentos.getColumns();
        columnas.get(0).setMaxWidth(1f*Integer.MAX_VALUE*15);
        columnas.get(1).setMaxWidth(1f*Integer.MAX_VALUE*25);
        columnas.get(2).setMaxWidth(1f*Integer.MAX_VALUE*60);
    }
    
}
