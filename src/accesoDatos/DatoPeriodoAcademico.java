/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesoDatos;

import entidades.PeriodoAcademico;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 *
 * @author CENTRINO
 */
public class DatoPeriodoAcademico {
    private static RandomAccessFile flujo;
    private static final int TAMREG = 26;
    private static int numRegistros;
    private static String nArchivo = "PeriodosAcademicos.txt";
    //codigo 3*2 +2        =8   bytes
    //Year  4              =4   bytes
    //Semestre 10*2 + 2    =22  bytes
    public static String crearArchivo() {
        try {
            flujo = new RandomAccessFile(nArchivo, "rw");
            numRegistros = (int) Math.ceil((double) flujo.length() / (double) TAMREG);
        } catch (IOException ex) {
            return "Problema al crear el flujo: " + ex.getMessage();
        }
        return null;
    }

    public static String escribirPeriodoAcademico(PeriodoAcademico periodoAcademico) {
        String mensaje = "";
        try {
            crearArchivo();
            flujo.close();
            mensaje = setPeriodoAcademico(numRegistros, periodoAcademico);
            if (mensaje.compareTo("ok") == 0) {
                numRegistros++;
            }
        } catch (IOException ex) {
            mensaje = "Excepción: " + ex.getMessage();
        } finally {
            return mensaje;
        }
    }

    public static String setPeriodoAcademico(int posicion, PeriodoAcademico periodoAcademico) {
        String mensaje = "";
        try {
            if (periodoAcademico.getSize() + 4 > TAMREG) {
                mensaje = "Tamaño de registro insuficiente";
            } else {
                crearArchivo();
                flujo.seek(posicion * TAMREG);
                flujo.writeUTF(periodoAcademico.genCode());
                System.out.println(periodoAcademico.genCode());
                flujo.writeInt(periodoAcademico.getYear());
                System.out.println(periodoAcademico.getYear());
                flujo.writeUTF(periodoAcademico.getSemestre());
                System.out.println(periodoAcademico.getSemestre());
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
    
    public static int indexOF(String code){
        System.out.println("____EjecutandoIndexOF_________");
        int index = -1;
        String temp;
        ArrayList<PeriodoAcademico> listPA = DatoPeriodoAcademico.getContenido();
        for(int i = 0;i<listPA.size();i++){
            System.out.println(code+"=="+listPA.get(i).genCode()+"?");
            System.out.println(listPA.get(i).getSemestre().getClass());
            if(listPA.get(i).genCode().equals(code)){
                index = i;
            }
        }
        System.out.println("retornado: "+index);
        System.out.println("_____TerminandoIndexOf_____");
        return index;
    }
    
    public static PeriodoAcademico getPeriodoAcademico(int pos) {
        int year;
        String semestre;
        System.out.println(pos);
        PeriodoAcademico periodoAcademico = null;
        try {
            crearArchivo();
            System.out.println("archivoCreado");
            flujo.seek(pos * TAMREG);
            System.out.println("seek realizaco");
            flujo.readUTF();
            System.out.println("codigo saltado");
            year = flujo.readInt();
            System.out.println("año leido: "+ year);
            semestre = flujo.readUTF();
            System.out.println("semestre leido: "+semestre);
            periodoAcademico = new PeriodoAcademico(year,semestre);
            System.out.println(periodoAcademico.genCode()+"]]]");
        } catch (IOException ex) {
            System.out.println("Problema de E/S en DatoPA: " + ex.getMessage());
            System.out.println(pos*TAMREG);
        } finally {
            try {
                flujo.close();
            } catch (IOException ex) {
                System.out.println("El flujo ya estaba cerrado: "
                        + ex.getMessage());
            }
        }
        return periodoAcademico;
    }

    public static ArrayList<PeriodoAcademico> getContenido() {
        ArrayList<PeriodoAcademico> lista = new ArrayList<PeriodoAcademico>();
        try {
            crearArchivo();
            for (int i = 0; i < numRegistros; i++) {
                lista.add(getPeriodoAcademico(i));
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
