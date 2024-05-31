
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
public class Compra {
    private int idCompra;
    private String fecha,NIT;
    private double total;
    private Conexion c;
    
    public Compra() {
    }

    public Compra(int idCompra, String fecha, String NIT, double total) {
        this.idCompra = idCompra;
        this.fecha = fecha;
        this.NIT = NIT;
        this.total = total;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNIT() {
        return NIT;
    }

    public void setNIT(String NIT) {
        this.NIT = NIT;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    
    
    public int[] listaIdCompra(){
        int idetalle[];
        c=new Conexion();
        c.abrir_conexion();
        try{
            String query="SELECT COUNT(idCompra) AS id FROM db_libreria.compra;";
            ResultSet count=c.conexionDB.createStatement().executeQuery(query);
            query="SELECT idCompra AS id FROM db_libreria.compra;";
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
    
    
    public int maxIdCompra(){
         int x;
         c=new Conexion();
         c.abrir_conexion();
        try{
            ResultSet res;
            res=c.conexionDB.createStatement().executeQuery("SELECT MAX(idCompra) as id FROM db_libreria.compra;");
            res.next();
            x=res.getInt("id");
            c.cerrar_conexion();
            return x;
        }catch(SQLException ex){
            System.out.println("Eror Id:"+ex.getMessage());
            return 0;
        }
    }
    
    public String ordenPorId(int id){
         String x;
         c=new Conexion();
         c.abrir_conexion();
        try{
            ResultSet res;
            res=c.conexionDB.createStatement().executeQuery("SELECT no_orden_compra FROM db_libreria.compra WHERE idCompra="+String.valueOf(id)+";");
            res.next();
            x=res.getString("no_orden_compra");
            c.cerrar_conexion();
            return x;
        }catch(SQLException ex){
            System.out.println("Eror Id:"+ex.getMessage());
            return null;
        }
    }
    
    public int nitPorIdVenta(int id){
         int x=0;
         c=new Conexion();
         c.abrir_conexion();
        try{
            ResultSet res;
            res=c.conexionDB.createStatement().executeQuery("SELECT NIT FROM db_libreria.compra WHERE idCompra="+String.valueOf(id)+";");
            res.next();
            x=res.getInt("NIT");
            c.cerrar_conexion();
            return x;
        }catch(SQLException ex){
            System.out.println("Eror Id:"+ex.getMessage());
            return x;
        }
    }
      public String fechaPorId(int id){
         String x;
         c=new Conexion();
         c.abrir_conexion();
        try{
            ResultSet res;
            res=c.conexionDB.createStatement().executeQuery("SELECT fecha FROM db_libreria.compra WHERE idCompra="+String.valueOf(id)+";");
            res.next();
            x=res.getString("fecha");
            c.cerrar_conexion();
            return x;
        }catch(SQLException ex){
            System.out.println("Eror Id:"+ex.getMessage());
            return null;
        }
    }
    
    public boolean existe (int idcompra){
        try{
        c= new Conexion();
        c.abrir_conexion();
        ResultSet res;
        String query="SELECT * from db_libreria.compra where idCompra='"+String.valueOf(idcompra)+"';";
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
            String query="INSERT INTO db_libreria.compra(fecha,NIT,total) VALUES(?,?,?);";
            parametro=(PreparedStatement) c.conexionDB.prepareStatement(query);
            parametro.setString(1,getFecha());
            parametro.setString(2,getNIT());
            parametro.setDouble(3,getTotal());
            int ejecutar=parametro.executeUpdate();
            if(ejecutar>0){
                idCompra=maxIdCompra();
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
            String query="UPDATE db_libreria.compra SET no_orden_compra=?, idProveedor=?, fecha_orden=?, fechaingreso=? where idCompra=?;";
            parametro=(PreparedStatement) c.conexionDB.prepareStatement(query);
            parametro.setString(1,getFecha());
            parametro.setString(2,getNIT());
            parametro.setDouble(3,getTotal());
            parametro.setInt(4,getIdCompra());
            parametro.setInt(5,getIdCompra());
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
            String query="DELETE FROM db_libreria.compra WHERE idCompra=?;";
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
            Vendedor p = new Vendedor();
            ResultSet res;
            String encabezado []={"ID Compra","NIT","Vendedor","Fecha de compra","Total"};
            model.setColumnIdentifiers(encabezado);
            res=c.conexionDB.createStatement().executeQuery("Select * from db_libreria.compra;");
            String datos[]=new String[6];
            while(res.next()){
                datos[0]=res.getString("idCompra");
                datos[1]=res.getString("NIT");
                datos[5]=res.getString("fecha");
                datos[3]=res.getString("total");
                datos[2]=p.getDes(datos[5]);
                model.addRow(datos);             
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        c.cerrar_conexion();
        return model;
    }
}
