/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import accesoDatos.*;
import javax.swing.JOptionPane;

/**
 *
 * @author CENTRINO
 */
public class ValidadorAsignatura {
    
    public static boolean checkNombre(String nombre){
        String[] palabras = cleanWhiteSpaces(nombre);
        if(nombre.trim().length()==0){return false;}
        else{
            for(String s: palabras){
                for(int i = 0; i< s.length();i++){
                    if( !(Character.isLetter(s.charAt(i)) || Character.isDigit(s.charAt(i)))){
                        System.out.println(nombre.substring(0,i+1)+"<- simbolo no valido");
                        JOptionPane.showMessageDialog(null, nombre.substring(0,i+1)+"<- simbolo no valido");
                        return false;
                    }
                }
            }   
        }
        return true;
    }


    public static boolean checkCodigo(String codigo){
        if(codigo.length()!=3){return false;}
        else{
            for(int i =0; i<3;i++){
                if(!Character.isDigit(codigo.charAt(i))){
                    System.out.println(codigo.substring(0,i+1)+"<- simbolo no valido");
                    JOptionPane.showMessageDialog(null, codigo.substring(0,i+1)+"<- simbolo no valido");
                    return false;
                }
            }
        }
        return true;
    }

    
    // El codigo solo revisa estos dos atributos ya que los demas el usuario selecciona de una lista pre establecida.
    public static boolean validarAsignatura(String codigo,String nombre){
        if(checkCodigo(codigo) && checkNombre(nombre)){
            if(checkExistence(codigo)){
                return false;
            }
            return true;
        }
        JOptionPane.showMessageDialog(null, "Intente otra vez");
        System.out.println("Intente otra vez");
        return false;
    }
    
    private static boolean checkExistence(String codigo){
        if(DatoAsignatura.indexOf(codigo)!=-1){
            JOptionPane.showMessageDialog(null, "La asignatura con codigo ["+codigo+"] ya existe!" );
            System.out.println("La signatura con codigo ["+codigo+"] ya existe!");
            return true;
        }
        return false;
    }
    
    private static String[] cleanWhiteSpaces(String s){
        s= s.trim(); // Esto no deberia ir, deberian rechazarse los ingresos con espacios al inicio
        String[] words = s.split(" ");
        return words;
    }
}
