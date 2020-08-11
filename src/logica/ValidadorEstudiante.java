/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;
import javax.swing.*;
import accesoDatos.*;
/**
 *
 * @author CENTRINO
 */
public class ValidadorEstudiante {
    public static boolean checkNombre(String nombre){
        if(nombre.length()==0){return false;}
        for(int i = 0; i< nombre.length();i++){
            
            if( !Character.isLetter(nombre.charAt(i))){
                System.out.println(nombre.substring(0,i+1)+"<- simbolo no valido");
                JOptionPane.showMessageDialog(null, nombre.substring(0,i+1)+"<- simbolo no valido");
                return false;
            }
        }
        return true;
    }
    public static boolean checkApellidos(String apellidos){
        String mensaje = "";
        String[] apellido = cleanWhiteSpaces(apellidos);
        if(apellidos.length()==0){return false;}
        else if(apellido.length<2){
            return false;
        }
        else{
            for(String s: apellido){
                for(int i = 0; i< s.length();i++){
                    mensaje+=s.charAt(i);
                    if( !Character.isLetter(s.charAt(i))){
                        System.out.println(mensaje +"<- simbolo no valido");
                        JOptionPane.showMessageDialog(null, mensaje+"<- simbolo no valido");
                        return false;
                    }
                }
                mensaje+=" ";
            }
        }
        return true;
    }
    
    public static boolean checkDni(String dni){
        if(dni.length()!=7){return false;}
        else{
            for(int i = 0; i<7;i++){
                if(!Character.isDigit(dni.charAt(i))){
                    System.out.println(dni.substring(0,i+1)+"<- simbolo no valido");
                    JOptionPane.showMessageDialog(null, dni.substring(0,i+1)+"<- simbolo no valido");
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean checkCodigo(String codigo){
        if(codigo.length()!=10){return false;}
        else{
            for(int i =0; i<10;i++){
                if(Character.isDigit(codigo.charAt(i))){
                    System.out.println(codigo.substring(0,i+1)+"<- simbolo no valido");
                    JOptionPane.showMessageDialog(null, codigo.substring(0,i+1)+"<- simbolo no valido");
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean checkDireccion(String direccion){
        if(direccion.trim().length()<0){
            System.out.println("por favor ingrese una direccion");
                    JOptionPane.showMessageDialog(null, "por favor ingrese una direccion");
            return false;
        }
        return true;
    }
    
    public static boolean validarEstudiante(String codigo,String dni,String apellidos, String nombre, String direccion){
        if(checkCodigo(codigo) && checkDni(dni) && checkApellidos(apellidos) && checkNombre(nombre) &&checkDireccion(direccion)){
            if(checkExistence(codigo)){
                return false;
            }
            return true;
        }
        JOptionPane.showMessageDialog(null, "Intente otra vez");
        System.out.println("Intente otra vez");
        return false;
    }
    
    public static boolean checkExistence(String codigo){
        if(DatoEstudiante.indexOf(codigo)!=-1){
            JOptionPane.showMessageDialog(null, "El estudiante con codigo ["+codigo+"] ya existe!" );
            System.out.println("El estudiante con codigo ["+codigo+"] ya existe!");
            return true;
        }
        return false;
    }
    
    private static String[] cleanWhiteSpaces(String s){
        s= s.trim();
        String[] words = s.split(" ");
        return words;
    }
}
