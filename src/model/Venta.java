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
public class Venta {
    private int idVenta,idUsuario;
    private String fecha,nitComprador,nitVendedor;
    private Double total;
    private Conexion c;
    
    public Venta() {
    }

    public Venta(int idVenta, String fecha, int idUsuario, String nitComprador, String nitVendedor) {
        this.idVenta = idVenta;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.nitComprador = nitComprador;
        this.nitVendedor = nitVendedor;
        this.total=total;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNitComprador() {
        return nitComprador;
    }

    public void setNitComprador(String nitComprador) {
        this.nitComprador = nitComprador;
    }

    public String getNitVendedor() {
        return nitVendedor;
    }

    public void setNitVendedor(String nitVendedor) {
        this.nitVendedor = nitVendedor;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
    
    public int maxIdVenta(){
         int x;
         c=new Conexion();
         c.abrir_conexion();
        try{
            ResultSet res;
            res=c.conexionDB.createStatement().executeQuery("SELECT MAX(idVenta) as id FROM db_libreria.venta;");
            res.next();
            x=res.getInt("id");
            c.cerrar_conexion();
            return x;
        }catch(SQLException ex){
            System.out.println("Eror Id:"+ex.getMessage());
            return 0;
        }
    }
    
    public int[] listaIdVenta(){
        int idetalle[];
        c=new Conexion();
        c.abrir_conexion();
        try{
            String query="SELECT COUNT(idVenta) AS id FROM db_libreria.venta;";
            ResultSet count=c.conexionDB.createStatement().executeQuery(query);
            query="SELECT idVenta AS id FROM db_libreria.venta;";
            ResultSet consulta=c.conexionDB.createStatement().executeQuery(query);
            count.next();
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
        
    public String nitClientePorId(int id){
         String x="";
         c=new Conexion();
         c.abrir_conexion();
        try{
            ResultSet res;
            res=c.conexionDB.createStatement().executeQuery("SELECT nitComprador FROM db_libreria.venta WHERE idVenta="+String.valueOf(id)+";");
            res.next();
            x=res.getString("nitComprador");
            c.cerrar_conexion();
            return x;
        }catch(SQLException ex){
            System.out.println("Eror Id:"+ex.getMessage());
            return x;
        }
    }  
    
    public boolean existe (int idventa){
        try{
        c= new Conexion();
        c.abrir_conexion();
        ResultSet res;
        String query="SELECT * from db_libreria.venta where idVenta='"+String.valueOf(idventa)+"';";
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
            String query="INSERT INTO db_libreria.venta(fecha, nitCompraor, nitVendedor, total, idUsuario) VALUES(?,?,?,?,?);";
            parametro=(PreparedStatement) c.conexionDB.prepareStatement(query);
            parametro.setString(1,getFecha());
            parametro.setString(2,getNitComprador());
            parametro.setString(3,getNitVendedor());
            parametro.setDouble(4,getTotal());
            parametro.setInt(5,getIdUsuario());
            int ejecutar=parametro.executeUpdate();
            if(ejecutar>0){
                idVenta=maxIdVenta();                
                c.cerrar_conexion();
                return 1;
            }else{
                return 0;
            }
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
            String query="UPDATE db_libreria.venta SET fecha=?, nitComprador=?, nitVendedor=?, total=?, idUsuario=? where idVenta=?;";
            parametro=(PreparedStatement) c.conexionDB.prepareStatement(query);
            parametro.setString(1,getFecha());
            parametro.setString(2,getNitComprador());
            parametro.setString(3,getNitVendedor());
            parametro.setDouble(4,getTotal());
            parametro.setInt(5,getIdUsuario());
            parametro.setInt(6,getIdVenta());
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
            String query="DELETE FROM db_libreria.venta WHERE idVenta=?;";
            parametro=(PreparedStatement) c.conexionDB.prepareStatement(query);
            parametro.setInt(1,getIdVenta());
            int ejecutar = parametro.executeUpdate();
            c.cerrar_conexion();
            return !existe(getIdVenta());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            c.cerrar_conexion();
            return !existe(getIdVenta());
        }
    }
    
    public DefaultTableModel mostrar(){
        DefaultTableModel model=new DefaultTableModel();
        c=new Conexion();
        Cliente cliente=new Cliente();
        c.abrir_conexion();
        try{
            ResultSet res;
            String encabezado []={"ID Venta","Fecha", "Comprador", "Total de la factura", "ID Usuario","Nit del Comprador","Nit del Vendedor"};
            model.setColumnIdentifiers(encabezado);
            res=c.conexionDB.createStatement().executeQuery("Select * from db_libreria.venta;");
            String datos[]=new String[6];
            while(res.next()){
                datos[0]=res.getString("idVenta");
                datos[1]=res.getString("fecha");
                datos[5]=res.getString("nitComprador");
                datos[6]=res.getString("nitVendedor");
                datos[2]=cliente.nombresPorNIT(datos[5]);
                datos[3]=res.getString("total");   
                datos[4]=res.getString("idUsuario");
                model.addRow(datos);             
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        c.cerrar_conexion();
        return model;
    }
}
