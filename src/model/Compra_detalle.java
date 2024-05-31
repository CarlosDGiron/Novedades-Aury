/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cana0
 */
public class Compra_detalle {
    private int idCompraDetalle, idCompra, idProducto, cantidad;
    private double precioCompra, precioVenta;
    private Conexion c;

    public Compra_detalle() {
    }

    public Compra_detalle(int idCompra_detalle, int idCompra, int idProducto, int cantidad, double precioCompra, double precioVenta) {
        this.idCompraDetalle = idCompra_detalle;
        this.idCompra = idCompra;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioCompra = precioCompra;
        this.precioVenta=precioVenta;
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

    public int getIdCompraDetalle() {
        return idCompraDetalle;
    }

    public void setIdCompraDetalle(int idCompraDetalle) {
        this.idCompraDetalle = idCompraDetalle;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public int[] idDetallePorIdCompra(int idc_){
        int idetalle[];
        int aux=0;
        c=new Conexion();
        c.abrir_conexion();
        try{
            String query="select count(idCompraDetalle) as id from db_libreria.compradetalle where idCompra='"+String.valueOf(idc_)+" GROUP BY idCompraDetalle';";
            ResultSet count=c.conexionDB.createStatement().executeQuery(query);
            query="select idCompraDetalle as id from db_libreria.compradetalle where idCompra='"+String.valueOf(idc_)+"';";
            ResultSet consulta=c.conexionDB.createStatement().executeQuery(query);
            count.next();
            System.out.println(count.getInt("id"));
            idetalle = new int[count.getInt("id")];
            int i=0;
            while(consulta.next()){
                idetalle[i]= consulta.getInt("id");
                i++;
            }
        c.cerrar_conexion();
        return idetalle;
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            c.cerrar_conexion();
            return null;
        }        
    }
    public boolean existe (int idcompra){
        try{
        c= new Conexion();
        c.abrir_conexion();
        ResultSet res;
        String query="SELECT * from db_libreria.compradetalle where idCompra='"+String.valueOf(idcompra)+"';";
        res=c.conexionDB.createStatement().executeQuery(query);
        return res.next();
        }catch(SQLException ex){
        System.out.println(ex.getMessage());
        return false;
        }
        finally{
            c.cerrar_conexion();
        }        
    }
    
    public int agregar(){
        try{
            PreparedStatement parametro;
            c=new Conexion();
            c.abrir_conexion();
            String query="INSERT INTO db_libreria.compradetalle(IdCompra, IdProducto, cantidad, precioCompra, precioVenta) VALUES(?,?,?,?,?);";
            parametro=(PreparedStatement) c.conexionDB.prepareStatement(query);
            parametro.setInt(1,getIdCompra());
            parametro.setInt(2,getIdProducto());
            parametro.setInt(3,getCantidad());
            parametro.setDouble(4,getPrecioCompra());
            parametro.setDouble(5,getPrecioVenta());
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
            String query="UPDATE db_libreria.compradetalle SET idCompra=?, idProducto=?, cantidad=?, precioCompra=?, precioVenta=? where idCompra_detalle=?;";
            parametro=(PreparedStatement) c.conexionDB.prepareStatement(query);
            parametro.setInt(1,getIdCompra());
            parametro.setInt(2,getIdProducto());
            parametro.setInt(3,getCantidad());
            parametro.setDouble(4,getPrecioCompra());
            parametro.setDouble(5,getPrecioVenta());
            parametro.setInt(6,getIdCompraDetalle());
            int ejecutar=parametro.executeUpdate();
            c.cerrar_conexion();
            return ejecutar;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            c.cerrar_conexion();
            return 0;
        }
    }
    
    public boolean eliminar(){        
        try {
            c=new Conexion();
            c.abrir_conexion();
            PreparedStatement parametro;
            String query="DELETE FROM db_libreria.compradetalle WHERE idCompra=?;";
            parametro=(PreparedStatement) c.conexionDB.prepareStatement(query);
            parametro.setInt(1,getIdCompra());
            int ejecutar = parametro.executeUpdate();
            c.cerrar_conexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            c.cerrar_conexion();
        }
        return !existe(getIdCompra());
    }
    
    public DefaultTableModel mostrar(){
        DefaultTableModel model=new DefaultTableModel();
        c=new Conexion();
        c.abrir_conexion();
        try{
            Producto p = new Producto();
            ResultSet res;
            String encabezado []={"ID Compra Detalle","ID Compra","Producto","Cantidad","Precio costo unitario","ID Producto"};
            model.setColumnIdentifiers(encabezado);
            res=c.conexionDB.createStatement().executeQuery("Select * from db_libreria.compradetalle;");
            String datos[]=new String[6];
            while(res.next()){
                datos[0]=res.getString("idCompra_detalle");
                datos[1]=res.getString("idCompra");
                datos[5]=res.getString("idProducto");
                datos[3]=res.getString("cantidad");
                datos[4]=res.getString("precio_costo_unitario");   
                datos[2]=p.getDes(datos[5]);
                model.addRow(datos);             
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        c.cerrar_conexion();
        return model;
    }
    
    public DefaultTableModel mostrarPorId(int idCompra_){
        DefaultTableModel model=new DefaultTableModel();
        c=new Conexion();
        c.abrir_conexion();
        try{
            Producto p = new Producto();
            ResultSet res;
            String encabezado []={"ID Compra Detalle","ID Compra","Producto","Cantidad","Precio Compra","Precio Venta","ID Producto"};
            model.setColumnIdentifiers(encabezado);
            res=c.conexionDB.createStatement().executeQuery("Select * from db_libreria.compradetalle WHERE idCompra="+idCompra_+";");
            String datos[]=new String[8];
            while(res.next()){
                datos[0]=res.getString("idCompra_detalle");
                datos[1]=res.getString("IdCompra");
                datos[6]=res.getString("idProducto");
                datos[3]=res.getString("cantidad");
                datos[4]=res.getString("precioCompra");   
                datos[5]=res.getString("precioVenta");   
                datos[2]=p.getDes(datos[5]);
                model.addRow(datos); 
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        c.cerrar_conexion();
        return model;
    }
    
    public int getCantidadProductos(int IdCompra_){
        c=new Conexion();
        c.abrir_conexion();
         int cant=0;
        try{
            ResultSet res;
            String query="Select COUNT(*) as cant from db_libreria.compradetalle WHERE idCompra="+String.valueOf(IdCompra_)+";";
            res=c.conexionDB.createStatement().executeQuery(query);
            res.next();
            cant =res.getInt("cant");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        c.cerrar_conexion();
        return cant;
    }
}
