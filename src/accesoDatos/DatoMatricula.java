/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesoDatos;
import entidades.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author CENTRINO
 */
public class DatoMatricula {
    private static RandomAccessFile flujo;
    private static final int  TAMREG = 70;
    private static int numRegistros;
    private static String nArchivo = "Matriculas.txt";
    // codigo = 13*2 +2         =28 bytes
    // dia = 4                  =4  bytes
    // mes = 4                  =4  bytes
    // year = 4                 =4  bytes
    // CodigoPA = 3*2 +2        =8  bytes
    // CodigoE = 10*2 +2        =22 bytes
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
            if (matricula.getSize() + 6 > TAMREG) {
                mensaje = "Tamaño de registro insuficiente";
            } else {
                crearArchivo();
                flujo.seek(pos * TAMREG);
                flujo.writeUTF(matricula.genCode());
                flujo.writeInt(matricula.getDia());
                flujo.writeInt(matricula.getMes());
                flujo.writeInt(matricula.getYear());
                flujo.writeUTF(matricula.getPeriodo().genCode());
                flujo.writeUTF(matricula.getEstudiante().getCodigo());
                mensaje = "ok";
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
    public static Matricula getMatricula(int pos){
        System.out.println("]]]]]]]]]CorriendoGetMatricula");
        GregorianCalendar f = new GregorianCalendar();
        String codM = "",codPA = "",codE = "";
        int dia= 0, mes = 0, year = 0;
        Estudiante estudiante = null;
        PeriodoAcademico pa = null;
        ArrayList<Asignatura> asignaturas = new ArrayList<>();
        ArrayList<Float> calificaciones = new ArrayList<>();
        
        //estudiante = DatoEstudiante.get(DatoEstudiante.indexOf())
        
        try{
            crearArchivo();
            flujo.seek(pos*TAMREG);
            codM = flujo.readUTF();
            dia = flujo.readInt();
            mes = flujo.readInt();
            year = flujo.readInt();
            codPA = flujo.readUTF();
            codE = flujo.readUTF();
        } catch (IOException ex) {
            System.out.println("Error... : " + ex.getMessage());
        } finally {
            try{
                flujo.close();
            }catch(IOException ex){
                System.out.println("El archivo ya estaba cerrado: "+ex.getMessage());
            }
        }
        //Se termino de leer el archivo
        f.set(year, mes-1, dia);
        System.out.println("check en getMatricula-DatoMatricula");
        System.out.println("Matricula codPA:"+codPA);
        int indexPa = DatoPeriodoAcademico.indexOF(codPA);
        System.out.println("index codPA: "+indexPa);
        pa = DatoPeriodoAcademico.getPeriodoAcademico(indexPa);
        System.out.println(pa.genCode());
        System.out.println("_____________________");
        estudiante = DatoEstudiante.getEstudiante(DatoEstudiante.indexOf(codE));
        asignaturas = DetalleMatriculas.getCursos(codM);
        calificaciones = DetalleMatriculas.getNotas(codM);
        System.out.println("getMatricula: "+codM);
        System.out.println("asignaturas.size():"+asignaturas.size());
        System.out.println("calificaciones.size():"+calificaciones.size());
        /*for(int i=0;i<calificaciones.size();i+){
            System.out.println(asignaturas.get(i).getNombre()+"->"+calificaciones.get(i));
        }*/
        return new Matricula(f,pa,estudiante,asignaturas,calificaciones);
    }
    
    public static ArrayList<Matricula> getContenido(){
        ArrayList<Matricula> lista = new ArrayList<Matricula>();
        try {
            crearArchivo();
            for (int i = 0; i < numRegistros; i++) {
                System.out.println("]]]]]]]]ObteniendoMatricula en posicion ->"+i);
                lista.add(getMatricula(i));
            }
        } finally {
            try {
                flujo.close();
            } catch (IOException ex) {
                System.out.println("El flujo ya estaba cerrado: " + ex.getMessage());
            }
        }
        return lista;
    }

    public static int getNumRegistros() {
        return numRegistros;
    }
}
