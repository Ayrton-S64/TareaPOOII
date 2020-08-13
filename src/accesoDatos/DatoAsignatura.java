/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesoDatos;

import entidades.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author CENTRINO
 */
public class DatoAsignatura {
    private static RandomAccessFile flujo;
    private static final int TAMREG = 120;
    private static int numRegistros;
    private static String nArchivo = "Asignaturas.txt";
    //Codigo 4*2 +2        =10 bytes
    //Nombre  50*2 +2      =102 bytes
    //Ciclo  4             =4 bytes
    //NumeroCreditos 4     =4 bytes
    public static String crearArchivo() {
        try {
            flujo = new RandomAccessFile(nArchivo, "rw");
            numRegistros = (int) Math.ceil((double) flujo.length() / (double) TAMREG);
        } catch (IOException ex) {
            return "Problema al crear el flujo: " + ex.getMessage();
        }
        return null;
    }

    public static String escribirAsignatura(Asignatura asignatura) {
        String mensaje = "";
        try {
            crearArchivo();
            flujo.close();
            mensaje = setAsignatura(numRegistros, asignatura);
            if (mensaje.compareTo("ok") == 0) {
                numRegistros++;
            }
        } catch (IOException ex) {
            mensaje = "Excepción: " + ex.getMessage();
        } finally {
            return mensaje;
        }
    }

    public static String setAsignatura(int posicion, Asignatura asignatura) {
        String mensaje = "";
        try {
            if (asignatura.getSize() + 4 > TAMREG) {
                mensaje = "Tamaño de registro excedido";
            } else {
                crearArchivo();
                flujo.seek(posicion * TAMREG);
                flujo.writeUTF(asignatura.getCodigo());
                flujo.writeUTF(asignatura.getNombre());
                flujo.writeInt(asignatura.getCiclo());
                flujo.writeInt(asignatura.getNumeroCreditos());
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
    
    public static int indexOf(String cod){
        int index = -1;
        ArrayList<Asignatura> listA = DatoAsignatura.getContenido();
        Asignatura tempA = new Asignatura();
        tempA.setCodigo(cod);
        for(int i = 0; i<listA.size();i++){
            Asignatura a=listA.get(i);
            if(a.equals(tempA)){
                index = i;
            }
        }
        return index;
    }
    
    public static Asignatura getAsignatura(int pos) {
        String codigo;
        String nombre;
        int ciclo;
        int numeroCreditos;
        
        Asignatura asignatura = null;
        try {
            crearArchivo();
            flujo.seek(pos * TAMREG);
            codigo = flujo.readUTF();
            nombre = flujo.readUTF();
            ciclo = flujo.readInt();
            numeroCreditos = flujo.readInt();
            asignatura = new Asignatura(codigo,nombre,ciclo,numeroCreditos);
        } catch (IOException ex) {
            System.out.println("Problema de E/S en DatoAsignatura: " + ex.getMessage());
            System.out.println(pos*TAMREG);
        } finally {
            try {
                flujo.close();
            } catch (IOException ex) {
                System.out.println("El flujo ya estaba cerrado: "
                        + ex.getMessage());
            }
        }
        return asignatura;
    }

    public static ArrayList<Asignatura> getContenido() {
        ArrayList<Asignatura> lista = new ArrayList<Asignatura>();
        try {
            crearArchivo();
            for (int i = 0; i < numRegistros; i++) {
                lista.add(getAsignatura(i));
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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
