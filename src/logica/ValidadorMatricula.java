/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;
import javax.swing.*;
import accesoDatos.*;
import entidades.*;
import java.util.*;
/**
 *
 * @author CENTRINO
 */
public class ValidadorMatricula {
        public static boolean checkEstudiante(String codigo){
        ArrayList<Matricula> matriculas = DatoMatricula.getContenido();
        if(codigo.length()!=10){
            String mensaje = "codigo incorrecto";
            JOptionPane.showMessageDialog(null, mensaje);
            System.out.println(mensaje);
            return false;}
        else{
            for(int i =0; i<10;i++){
                if(!Character.isDigit(codigo.charAt(i))){
                    System.out.println(codigo.substring(0,i+1)+"<- simbolo no valido");
                    JOptionPane.showMessageDialog(null, codigo.substring(0,i+1)+"<- simbolo no valido");
                    return false;
                }
            }
        }
        if(!DetalleMatriculas.condicionEstudiante(codigo)){
            String mensaje = "El alumno ["+codigo+"] no puede ser matriculado, ya que perdio la condicion de estudiante.";
            JOptionPane.showMessageDialog(null, mensaje);
            System.out.println(mensaje);
            return false;
        }
            System.out.println("Check Estudiante retornando True");
        return true;
    }
        //TODO: 
        //     Verficar que solo no se matricule en un curso si es que no lo jaló.
        //     No perminitir que se registren alumnos con 4 jalados.
        
        
        public static boolean validarMatricula(String codigoEstudiante,ArrayList<Asignatura> cursos){
            if(checkEstudiante(codigoEstudiante) && checkCursos(codigoEstudiante, cursos)){
                return true;
            }else{
                String mensaje = "Error...";
                JOptionPane.showMessageDialog(null, mensaje);
                System.out.println(mensaje);
            }
            return false;
        }
        
        public static boolean checkCursos(String codigoEstudiante,ArrayList<Asignatura> cursos){
            int nCursos = cursos.size();
            ArrayList<Matricula> matriculas = DatoMatricula.getContenido();
            if(nCursos == 0 || nCursos > 6){
                String mensaje = "Ingrese una cantidad de cursos valida";
            JOptionPane.showMessageDialog(null, mensaje);
            System.out.println(mensaje);
                return false;
            }
            boolean encontrado = false;
            int i = matriculas.size();
            if(i!=0){
                do{
                    i--;
                    Matricula temp_m = matriculas.get(i);
                    if(temp_m.getEstudiante().equals(new Estudiante(codigoEstudiante,"","","",""))){
                        for(int j = cursos.size()-1;j>=0;j--){
                            ArrayList<Asignatura> tempC = temp_m.getCursosMatriculados();
                            for(int k = 0; k<tempC.size();k++){
                                if(tempC.get(k).equals(cursos.get(j))){
                                    if(temp_m.getCalificaciones().get(k)>=10.5){
                                        String mensaje = "No puede matricularse otra vez al curso: "+cursos.get(j).getNombre();
                                        JOptionPane.showMessageDialog(null, mensaje);
                                        System.out.println(mensaje);
                                        return false;
                                    }
                                    cursos.remove(j);
                                    }
                            }
                        }
                    }
                }while(cursos.size()>0 && i!=0);
            }else{
                String mensaje = "Pero que esta pasando";
                JOptionPane.showMessageDialog(null, mensaje);
                System.out.println(mensaje);
                return false;
            }
            System.out.println("check cursos retornando true;");
            return true;
        }    
}
