/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author cana0
 */
public class Usuario {
    private String usuario;
    private int idUsuario;
    private Conexion c;

    public Usuario() {
    }

    public Usuario(String usuario, int idUsuario) {
        this.usuario = usuario;
        this.idUsuario=idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
   
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String encryptPassword(String password){
        String passwordEncrypted="";
        try   
        {  
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(password.getBytes());  
            byte[] bytes = m.digest();  
            StringBuilder s = new StringBuilder();  
            for(int i=0; i< bytes.length ;i++)  
            {  
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));  
            } 
            passwordEncrypted = s.toString();  
        }   
        catch (NoSuchAlgorithmException e)   
        {  
            System.out.println("Error encriptando la contraseña: "+e);
        }
        return passwordEncrypted; 
    }
    
    public boolean esValido(String user, String pass){
        try{
            String encryptedPass=encryptPassword(pass);
            c= new Conexion();
            c.abrir_conexion();
            ResultSet res;
            String query="Select password from db_libreria.usuario where usuario='"+user+"';";
            res=c.conexionDB.createStatement().executeQuery(query);
            res.next();
            return res.getString("password").equals(pass);
        }catch(SQLException ex){
        System.out.println(ex.getMessage());
        return false;
        }
        finally{
            c.cerrar_conexion();
        }
    }
        
    public void cargarIds(){
        try{
        c= new Conexion();
        c.abrir_conexion();
        ResultSet res;
        String query="SELECT idUsuario from db_libreria.usuario where usuario='"+usuario+"';";
        res=c.conexionDB.createStatement().executeQuery(query);
        res.next();       
        idUsuario= res.getInt("idUsuario");
        }catch(SQLException ex){
        System.out.println(ex.getMessage());
        }
        finally{
            c.cerrar_conexion();
        }
    }
    
}