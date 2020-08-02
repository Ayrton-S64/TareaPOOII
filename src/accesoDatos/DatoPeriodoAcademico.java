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
                flujo.writeInt(periodoAcademico.getYear());
                flujo.writeUTF(periodoAcademico.getSemestre());
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
        int index = -1;
        String temp;
        ArrayList<PeriodoAcademico> listPA = DatoPeriodoAcademico.getContenido();
        for(int i = 0;i<listPA.size();i++){
            if(listPA.get(i).genCode().equals(code)){
                index = i;
            }
        }
        return index;
    }
    
    public static PeriodoAcademico getPeriodoAcademico(int pos) {
        int year;
        String semestre;
        
        PeriodoAcademico periodoAcademico = null;
        try {
            crearArchivo();
            flujo.seek(pos * TAMREG);
            flujo.skipBytes(8);
            year = flujo.readInt();
            semestre = flujo.readUTF();
            periodoAcademico = new PeriodoAcademico(year,semestre);
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
