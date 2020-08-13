/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesoDatos;

import entidades.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
/**
 *
 * @author CENTRINO
 */
public class DetalleMatriculas {
    private static RandomAccessFile flujo;
    private static final int  TAMREG = 46;
    private static int numRegistros;
    private static String nArchivo = "DetalleMatriculas.txt";
    //CodigoM 13*2 +2       =28 bytes
    //CodigoA 4*2 +2        =10 bytes
    //nVeces  4             =4  bytes
    //Calificacion  4       =4  bytes
    public static String crearArchivo() {
        try {
            flujo = new RandomAccessFile(nArchivo, "rw");
            numRegistros = (int) Math.ceil((double) flujo.length() / (double) TAMREG);
        } catch (IOException ex) {
            return "Problema al crear el flujo: " + ex.getMessage();
        }
        return null;
    }
    
    public static String escribirMatricula(Matricula matricula){
        System.out.println("Escribiendo en DetallesMatriculas");
        int n = 0;
        String mensaje = "";
        try {
            crearArchivo();
            flujo.close();
            n = setMatricula(numRegistros, matricula);
            if(n==matricula.getCursosMatriculados().size()){
                numRegistros++;
            }
        } catch (IOException ex) {
            mensaje = "Excepcion : "+ex.getMessage();
        } finally {
            return mensaje;
        }
    }
    
    public static boolean modifyNota(String codMatricula, String codAsignatura,float nota){
        System.out.println("modifyNota]]]]");
        boolean validacion = true;
        try{
            crearArchivo();
            for(int i = numRegistros-1;i>=0;i--){
                flujo.seek(i*TAMREG);
                String tempCodM = flujo.readUTF();
                if(tempCodM.equals(codMatricula)){
                    String tempCodA = flujo.readUTF();
                    if(tempCodA.equals(codAsignatura)){
                        flujo.readInt();
                        System.out.println("Escribiendo: " + nota);
                        flujo.writeFloat(nota);
                    }
                }
            }

        }catch(IOException ex){
            System.out.println("[DetalleMatriculas]Problema de E/S: " + ex.getMessage());
            System.out.println(ex.getCause());
            validacion = false;
        }finally{
            try {
                flujo.close();
            } catch (IOException ex) {
                System.out.println("El flujo ya estaba cerrado: " + ex.getMessage());
            }
        }
        return validacion;
    }
    
    public static int setMatricula(int pos, Matricula matricula){
        System.out.println("En set Matricula");
        int n= 1;
        try {
            //El if es removido ya que el tamaño de registro nunca será excedido
            //if (matricula.getSize() + 4 > TAMREG) {
                
            //} else {
                crearArchivo();
                ArrayList<Asignatura> lista = matricula.getCursosMatriculados();
                for(int i =0 ; i < lista.size();i++){
                    System.out.println("Escribribiendo asignatura: "+lista.get(i).getNombre());
                    flujo.seek(pos * TAMREG);
                    flujo.writeUTF(matricula.genCode());
                    flujo.writeUTF(lista.get(i).getCodigo());
                    flujo.writeInt(DetalleMatriculas.countAsignatura(matricula.genCode(), lista.get(i).getCodigo()));
                    //System.out.println(matricula.getCalificaciones().get(i));
                    flujo.writeFloat(matricula.getCalificaciones().get(i));
                    pos++;
                    n++;
                //  }
                }
        } catch (IOException ex) {
            System.out.println("Excepción: " + ex.getMessage());
            n=0;
        } finally {
            try {
                flujo.close();
            } catch (IOException ex) {
                System.out.println("El flujo ya estaba cerrado: " + ex.getMessage());
            }
        }
        return n;
    }
    
    public static int countAsignatura(String codMatricula, String codAsignatura){
        int c = 0;
        try {
            crearArchivo();
            for(int i=0;i<numRegistros;i++){
                flujo.seek(i*TAMREG);
                String tempCodM = flujo.readUTF();
                if(tempCodM.substring(3,13).equals(codMatricula.substring(3,13))){
                    String tempCodA = flujo.readUTF();
                    if(tempCodA.equals(codAsignatura)){
                        c++;
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("ERROR: "+ex);
        } finally {
            try{
                flujo.close();
            }catch(IOException ex){
                System.out.println("El archivo ya se encuentra cerrado: "+ex);
            }
        }
        return c;
    }
    
    public static boolean condicionEstudiante(String codEstudiante){        
        try {
            crearArchivo();
            for(int i=numRegistros-1;i>=0;i--){
                flujo.seek(i*TAMREG);
                String tempCodM = flujo.readUTF();
                if(tempCodM.substring(3,13).equals(codEstudiante)){
                    String tempCodA = flujo.readUTF();
                    int nVeces = flujo.readInt();
                    if(nVeces == 4){
                        return false;
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("ERROR: "+ex);
        } finally {
            try{
                flujo.close();
            }catch(IOException ex){
                System.out.println("El archivo ya se encuentra cerrado: "+ex);
            }
        }
        
        return true;
    }
    
    public static ArrayList<Asignatura> getCursos(String codM){
        System.out.println("GetCursos");
        ArrayList<Asignatura> list = new ArrayList<Asignatura>();
        String codigoM;
        String codigoA;
        Asignatura asignatura = null;
        try {
            crearArchivo();
            for(int i = 0; i<numRegistros;i++){
                flujo.seek(i * TAMREG);
                codigoM = flujo.readUTF();
                if(codigoM.equals(codM)){
                    codigoA = flujo.readUTF();
                    list.add(DatoAsignatura.getAsignatura(DatoAsignatura.indexOf(codigoA)));
                }
            }
        } catch (IOException ex) {
            System.out.println("Problema de E/S: " + ex.getMessage());
        } finally {
            try {
                flujo.close();
            } catch (IOException ex) {
                System.out.println("El flujo ya estaba cerrado: "
                        + ex.getMessage());
            }
        }
        return list;
    }
    public static ArrayList<Float> getNotas(String codM){
        System.out.println("GetNotas");
        ArrayList<Float> list = new ArrayList<Float>();
        String codigoM;
        String codigoA;
        Float nota = 0.0f;
        try {
            
            for(int i = 0; i<numRegistros;i++){
                System.out.println("iteracion: "+i);
                try{
                    crearArchivo();
                    flujo.seek(i * TAMREG);
                    codigoM = flujo.readUTF();
                    System.out.println("CodigoM: "+codigoM);
                    if(codigoM.equals(codM)){
                        System.out.println(flujo.readUTF());
                        System.out.println(flujo.readInt());
                        nota = flujo.readFloat();
                        nota = (nota == null)?0.0f:nota;
                        System.out.println("nota:"+ nota);
                        
                    }
                }catch(IOException ex){
                    System.out.println("[DetalleMatriculas]Problema de E/S: " + ex.getMessage());
                    System.out.println("Nota = "+nota+ "|" + ex.getCause());
                }
                list.add(nota);
            }
        } finally {
            try {
                System.out.println("Cerrando flujo");
                flujo.close();
            } catch (IOException ex) {
                System.out.println("El flujo ya estaba cerrado: "
                        + ex.getMessage());
            }
        }
        
        for(Float f : list){
            System.out.println("f:"+f);
        }
        return list;
    }
}
