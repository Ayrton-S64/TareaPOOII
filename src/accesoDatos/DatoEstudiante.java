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
public class DatoEstudiante {
    private static RandomAccessFile flujo;
    private static final int TAMREG = 204;
    private static int numRegistros;
    private static String nArchivo = "Estudiantes.txt";
    //Codigo 10*2 +2        =22 bytes
    //DNI   8*2 +2          =18 bytes
    //Nombre  15*2 +2       =32 bytes
    //Apellidos  30*2 +2    =62 bytes
    //Direccion  34*2 +2    =70 bytes
    public static String crearArchivo() {
        try {
            flujo = new RandomAccessFile(nArchivo, "rw");
            numRegistros = (int) Math.ceil((double) flujo.length() / (double) TAMREG);
        } catch (IOException ex) {
            return "Problema al crear el flujo: " + ex.getMessage();
        }
        return null;
    }

    public static String escribirEstudiante(Estudiante estudiante) {
        String mensaje = "";
        try {
            crearArchivo();
            flujo.close();
            mensaje = setEstudiante(numRegistros, estudiante);
            if (mensaje.compareTo("ok") == 0) {
                numRegistros++;
            }
        } catch (IOException ex) {
            mensaje = "Excepción: " + ex.getMessage();
        } finally {
            return mensaje;
        }
    }

    
    public static String setEstudiante(int posicion, Estudiante estudiante) {
        String mensaje = "";
        try {
            if (estudiante.getSize() + 10 > TAMREG) {
                mensaje = "Tamaño de registro insuficiente";
            } else {
                crearArchivo();
                flujo.seek(posicion * TAMREG);
                flujo.writeUTF(estudiante.getCodigo());
                flujo.writeUTF(estudiante.getDni());
                flujo.writeUTF(estudiante.getApellidos());
                flujo.writeUTF(estudiante.getNombre());
                flujo.writeUTF(estudiante.getDireccion());
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
        System.out.println("DatoEstudiante.indexOF : "+cod);
        int index = -1;
        ArrayList<Estudiante> listE = DatoEstudiante.getContenido();
        Estudiante tempE = new Estudiante();
        tempE.setCodigo(cod);
        for(int i = 0; i<listE.size();i++){
            Estudiante e=listE.get(i);
            if(e.equals(tempE)){
                index = i;
            }
        }
        return index;
    }
    
    public static Estudiante getEstudiante(int pos) {
        String codigo;
        String dni;
        String apellidos;
        String nombre;
        String direccion;
        
        Estudiante estudiante = null;
        try {
            crearArchivo();
            flujo.seek(pos * TAMREG);
            codigo = flujo.readUTF();
            dni = flujo.readUTF();
            apellidos = flujo.readUTF();
            nombre = flujo.readUTF();
            direccion = flujo.readUTF();
            estudiante = new Estudiante(codigo,dni,apellidos,nombre,direccion);
        } catch (IOException ex) {
            System.out.println("Problema de E/S en DatoEstudiante: " + ex.getMessage());
            System.out.println(pos*TAMREG);
        } finally {
            try {
                flujo.close();
            } catch (IOException ex) {
                System.out.println("El flujo ya estaba cerrado: "
                        + ex.getMessage());
            }
        }
        return estudiante;
    }

    public static ArrayList<Estudiante> getContenido() {
        ArrayList<Estudiante> lista = new ArrayList<Estudiante>();
        try {
            crearArchivo();
            for (int i = 0; i < numRegistros; i++) {
                lista.add(getEstudiante(i));
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
