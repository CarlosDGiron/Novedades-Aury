/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author cana0
 */
public class Producto {
    private int idProducto , idCategoria,idMarca,existencia;
    private String nombre, fechaDeIngreso;
    private double precioCompra,precioVenta;
    private Conexion c;
    
    public Producto() {
    }

    public Producto(int idProducto, int idCategoria, int idMarca, int existencia, String nombre, String fechaDeIngreso, double precioCompra, double precioVenta) {
        this.idProducto = idProducto;
        this.idCategoria = idCategoria;
        this.idMarca = idMarca;
        this.existencia = existencia;
        this.nombre = nombre;
        this.fechaDeIngreso = fechaDeIngreso;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaDeIngreso() {
        return fechaDeIngreso;
    }

    public void setFechaDeIngreso(String fechaDeIngreso) {
        this.fechaDeIngreso = fechaDeIngreso;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }
    
     public int getMaxId(){
         int x;
         c=new Conexion();
         c.abrir_conexion();
        try{
            ResultSet res;
            res=c.conexionDB.createStatement().executeQuery("SELECT MAX(idProducto) as id FROM db_libreria.producto;");            res.next();
            x=res.getInt("id");
            c.cerrar_conexion();
            return x;
        }catch(SQLException ex){
            System.out.println("Eror Id:"+ex.getMessage());
            return 0;
        }
    }
    
    public HashMap drop_productoprecio(){
        HashMap<String,String> drop=new HashMap();
        c=new Conexion();
        c.abrir_conexion();
        try{
            String query="select idProducto as id, precioVenta from db_libreria.producto;";
            ResultSet consulta=c.conexionDB.createStatement().executeQuery(query);
            while(consulta.next()){
                drop.put(consulta.getString("id"), consulta.getString("precioVenta"));
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        c.cerrar_conexion();
        return drop;
    }
    
    public HashMap drop_producto(){
        HashMap<String,String> drop=new HashMap();
        c=new Conexion();
        c.abrir_conexion();
        try{
            String query="select idProducto as id, nombre from db_libreria.producto;";
            ResultSet consulta=c.conexionDB.createStatement().executeQuery(query);
            while(consulta.next()){
                drop.put(consulta.getString("id"), consulta.getString("nombre"));
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        c.cerrar_conexion();
        return drop;
    }
    
    public int maxVenta(int id){
        int x=0;
         c=new Conexion();
         c.abrir_conexion();
        try{
            ResultSet res;
            res=c.conexionDB.createStatement().executeQuery("Select existencia from db_libreria.producto where idProducto="+id+";");
            res.next();
            x=res.getInt("existencia");
        }catch(SQLException ex){
            System.out.println("Eror Id:"+ex.getMessage());
        }
        c.cerrar_conexion();
        return x;
    }    
    
    public String getId(String des){
         String x=null;
         c=new Conexion();
         c.abrir_conexion();
        try{
            ResultSet res;
            res=c.conexionDB.createStatement().executeQuery("Select idProducto from db_libreria.producto where nombre =\""+des+"\";");
            res.next();
            x=res.getString("idProducto");
        }catch(SQLException ex){
            System.out.println("Eror Id:"+ex.getMessage());
        }
        c.cerrar_conexion();
        return x;
    }
    
    public String getDes(String id){
        String x=null;  
        c=new Conexion();
        c.abrir_conexion();
        try{
            ResultSet res;
            res=c.conexionDB.createStatement().executeQuery("Select nombre from db_libreria.producto where idProducto ="+id+";");
            res.next();
            x=res.getString("nombre");
        }catch(SQLException ex){
            System.out.println("Eror Des:"+ex.getMessage());
        }
        c.cerrar_conexion();
        return x;
    }
    
    public int agregar(){
        try{
            PreparedStatement parametro;
            c=new Conexion();
            c.abrir_conexion();
            String query="INSERT INTO db_libreria.producto(nombre, idMarca, idCategoria precioCosto, precioVenta, existencia, fechaDeIngreso) VALUES(?,?,?,?,?,?,?,?);";
            parametro=(PreparedStatement) c.conexionDB.prepareStatement(query);
            parametro.setString(1,getNombre());
            parametro.setInt(2,getIdMarca());            
            parametro.setInt(3,getIdCategoria());
            parametro.setDouble(4,getPrecioCompra());
            parametro.setDouble(5,getPrecioVenta());
            parametro.setInt(6,getExistencia());
            parametro.setString(7,getFechaDeIngreso());
            int ejecutar=parametro.executeUpdate();            
            c.cerrar_conexion();
            return ejecutar;
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            c.cerrar_conexion();
            return 0;
        }
    }
    
    public int modificar(){
        try {
            c= new Conexion();
            c.abrir_conexion();
            PreparedStatement parametro;
            String query="UPDATE db_libreria.producto SET nombre=?, idMarca=?, idCategoria=?, precioCompra=?, precioVenta=?, existencia=?, fechaDeIngreso=? where idProducto=?;";
            parametro=(PreparedStatement) c.conexionDB.prepareStatement(query);
            parametro.setString(1,getNombre());
            parametro.setInt(2,getIdMarca());            
            parametro.setInt(3,getIdCategoria());
            parametro.setDouble(4,getPrecioCompra());
            parametro.setDouble(5,getPrecioVenta());
            parametro.setInt(6,getExistencia());
            parametro.setString(7,getFechaDeIngreso());
            parametro.setInt(8,getIdProducto());
            int ejecutar=parametro.executeUpdate();
            c.cerrar_conexion();
            return ejecutar;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            c.cerrar_conexion();
            return 0;
        }
    }
    
    public int eliminar(){        
        try {
            c=new Conexion();
            c.abrir_conexion();
            PreparedStatement parametro;
            String query="DELETE FROM db_libreria.producto WHERE idProducto=?;";
            parametro=(PreparedStatement) c.conexionDB.prepareStatement(query);
            parametro.setInt(1,getIdProducto());
            int ejecutar = parametro.executeUpdate();
            c.cerrar_conexion();
            return ejecutar;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            c.cerrar_conexion();
            return 0;
        }
    }
    
    public DefaultTableModel mostrar(){
        DefaultTableModel model=new DefaultTableModel();
        c=new Conexion();
        c.abrir_conexion();
        try{
            Marca m = new Marca();
            Categoria categoria = new Categoria();
            ResultSet res;
            String encabezado []={"ID Producto","Producto","Marca","Categoria","Existencia","Precio Compra","Precio Venta","Fecha de ingreso","ID Marca","ID Categoria"};
            model.setColumnIdentifiers(encabezado);
            res=c.conexionDB.createStatement().executeQuery("Select * from db_libreria.producto;");
            String datos[]=new String[10];
            while(res.next()){
                datos[0]=res.getString("idProducto");
                datos[1]=res.getString("nombre");
                datos[8]=res.getString("idMarca");                
                datos[9]=res.getString("idCategoria");
                datos[4]=res.getString("existencia");
                datos[5]=res.getString("precioCompra");
                datos[6]=res.getString("precioVenta");  
                datos[7]=res.getString("fechaDeIngreso");
                datos[2]=m.getDes(datos[8]);                
                datos[3]=categoria.getDes(datos[9]);
                model.addRow(datos);             
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        c.cerrar_conexion();
        return model;
    }   
        
}
