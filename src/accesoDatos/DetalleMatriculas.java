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
        String mensaje = "";
        try {
            crearArchivo();
            flujo.close();
            mensaje = setMatricula(numRegistros, matricula);
            if(mensaje.compareTo("ok")==0){
                numRegistros++;
            }
        } catch (IOException ex) {
            mensaje = "Excepcion : "+ex.getMessage();
        } finally {
            return mensaje;
        }
    }
    
    public static String setMatricula(int pos, Matricula matricula){
        String mensaje = "";
        try {
            if (matricula.getSize() + 4 > TAMREG) {
                mensaje = "Tamaño de registro insuficiente";
            } else {
                crearArchivo();
                flujo.seek(pos * TAMREG);
                flujo.writeUTF(matricula.genCode());
                ArrayList<Asignatura> lista = matricula.getCursosMatriculados();
                for(int i =0 ; i < lista.size();i++){
                    flujo.writeUTF(lista.get(i).getCodigo());
                    flujo.writeInt(DetalleMatriculas.countAsignatura(matricula.genCode(), lista.get(i).getCodigo()));
                    flujo.writeFloat(matricula.getCalificaciones().get(i));
                }
            }
        } catch (IOException ex) {
            mensaje = "Excepción: " + ex.getMessage();
        } finally {
            try {
                flujo.close();
            } catch (IOException ex) {
                mensaje = "El flujo ya estaba cerrado: " + ex.getMessage();
            }
        }
        return mensaje;
    }
    
    public static int countAsignatura(String codMatricula, String codAsignatura){
        int c = 0;
        try {
            crearArchivo();
            for(int i=0;i<numRegistros;i++){
                flujo.seek(i*TAMREG);
                String tempCodM = flujo.readUTF();
                if(tempCodM.equals(codMatricula)){
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
    
    public static ArrayList<Asignatura> getCursos(String codM){
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
        ArrayList<Float> list = new ArrayList<Float>();
        String codigoM;
        String codigoA;
        float nota = 0.0f;
        try {
            crearArchivo();
            for(int i = 0; i<numRegistros;i++){
                flujo.seek(i * TAMREG);
                codigoM = flujo.readUTF();
                if(codigoM.equals(codM)){
                    flujo.readUTF();
                    flujo.readInt();
                    nota = flujo.readFloat();
                    list.add(nota);
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
}
