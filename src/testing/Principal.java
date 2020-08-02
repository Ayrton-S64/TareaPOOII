/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;
import entidades.*;
import accesoDatos.*;
import java.util.*;
/**
 *
 * @author CENTRINO
 */
public class Principal {
    static Scanner sc = new Scanner(System.in);;
    static ArrayList<Estudiante> estudiantes = DatoEstudiante.getContenido();
    static ArrayList<Asignatura> asignaturas = DatoAsignatura.getContenido();
    static ArrayList<Matricula> matriculas = DatoMatricula.getContenido();
    static PeriodoAcademico periodo = new PeriodoAcademico(2020,"I");
    static GregorianCalendar c = new GregorianCalendar();
    public static void registerEstudiante(){
        System.out.println("------[Registro Estudiante]------");
        System.out.print("Ingrese el codigo del estudiante: ");
        String codigo = sc.nextLine();
        System.out.print("Ingrese el dni del estudiante: ");
        String dni = sc.nextLine();
        System.out.print("Ingrese los apellidos del estudiante: ");
        String apellidos = sc.nextLine();
        System.out.print("Ingrese el nombre del estudiante: ");
        String nombres = sc.nextLine();
        System.out.print("Ingrese la direccion del estudiante: ");
        String direccion = sc.nextLine();
        Estudiante e = new Estudiante(codigo,dni,apellidos,nombres,direccion);
        System.out.println(e.toString());
        DatoEstudiante.escribirEstudiante(e);
        estudiantes.add(e);
    }
    
    public static void listEstudiantes(){
        System.out.println("------[Lista de Estudiantes]------");
        for(Estudiante e: estudiantes){
            System.out.println(e.toString());
        }
    }
    
    public static void registerAsignatura(){
        System.out.println("------[Registro Asignatura]------");
        System.out.print("Ingrese el codigo de la asignatura: ");
        String codigo = sc.nextLine();
        System.out.print("Ingrese el nombre de la asignatura: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese el ciclo de la asignatura: ");
        int ciclo = sc.nextInt();
        sc.nextLine();
        System.out.print("Ingrese el numero de creditos: ");
        int numeroCreditos = sc.nextInt();
        sc.nextLine();
        Asignatura a = new Asignatura(codigo,nombre,ciclo,numeroCreditos);
        DatoAsignatura.escribirAsignatura(a);
        asignaturas.add(a);
    }
    public static void listAsignaturas(){
        System.out.println("------[Lista de Asignaturas]------");
        for(Asignatura a: asignaturas){
            System.out.println(a.toString());
        }
    }
    
    public static boolean checkEstudiante(String codigo){
        Estudiante tempE = new Estudiante();
        tempE.setCodigo(codigo);
        for(Estudiante e: estudiantes){
            if(e.equals(tempE)){
                return true;
            }
        }
        return false;
    }
    
    public static Estudiante searchEstudiante(String codigo){
        Estudiante tempE = new Estudiante();
        tempE.setCodigo(codigo);
        for(Estudiante e: estudiantes){
            if(e.equals(tempE)){
                return e;
            }
        }
        return null;
    }
    
    public static boolean checkAsignatura(String codigo){
        Asignatura tempA = new Asignatura();
        tempA.setCodigo(codigo);
        for(Asignatura a: asignaturas){
            if(a.equals(tempA)){
                return true;
            }
        }
        return false;
    }
    
    public static Asignatura searchAsignatura(String codigo){
        Asignatura tempA = new Asignatura();
        tempA.setCodigo(codigo);
        for(Asignatura a: asignaturas){
            if(a.equals(tempA)){
                return a;
            }
        }
        return null;
    }
    
    public static void registerMatricula(){
        System.out.println("------[Registro Matricula]------");
//        private GregorianCalendar fecha;
//	private PeriodoAcademico periodo;
//	private Estudiante estudiante;
//	private ArrayList<Asignatura> cursosMatriculados;
//	private ArrayList<Float> calificaciones;
        Matricula m = new Matricula();
        m.setFecha(c);
        m.setPeriodo(periodo);
        boolean validation;
        Estudiante e = new Estudiante();
        do{
            System.out.print("Ingrese el codigo del estudiante: ");
            String codigo = sc.nextLine();
            validation = checkEstudiante(codigo);
            if(validation){
                e = searchEstudiante(codigo);
            }
            else{
                System.out.println("No se encontró al estudiante.\nIntente otra vez\n");
            }
        }while(!validation);
        m.setEstudiante(e);
        do{
            System.out.print("Ingrese el codigo de asignnatura: ");
            String codigo = sc.nextLine();
            boolean check = checkAsignatura(codigo);
            if(check){
                m.addAsignatura(searchAsignatura(codigo));
                m.addCalificacion(0.0f);
                System.out.print("Desea ingresar otra asignatura? (Y/N):");
                validation = sc.nextLine().toUpperCase().equals("Y");
            }
            else{
                System.out.println("No se encontró la asignatura.\nIntente otra vez\n");
            }
        }while(m.getCursosMatriculados().size()< 6 && validation);
        DatoMatricula.escribirMatricula(m);
        matriculas.add(m);
    }
    
    public static void registerNotas(){
        System.out.println("----------[Ingreso Notas]----------");
        for(Matricula m : matriculas){
            System.out.println("Notas para "+m.getEstudiante().getApellidos()+" "+m.getEstudiante().getNombre());
            for(int i = 0;i<m.getCursosMatriculados().size();i++){
                Asignatura a = m.getCursosMatriculados().get(i);
                System.out.print("["+a.getCodigo()+"] "+a.getNombre()+": ");
                float nota = sc.nextFloat();
                sc.nextLine();
                m.setCalificacion(i, nota);
            }
            DetalleMatriculas.escribirMatricula(m);
        }
    }
    
    public static void listMatriculas(){
        for(Matricula m: matriculas){
            System.out.println("--------------------------------------------------------");
            System.out.println(m.toString());
            System.out.println(m.reporteToString());
        }
    }
    
    public static void genFileEstudiantes(){
        for(Estudiante e: estudiantes){
            System.out.println(DatoEstudiante.escribirEstudiante(e));
        }
    }
    
    public static void main(String[] args) {
        boolean validation = true;
        estudiantes = DatoEstudiante.getContenido();
        do{
            System.out.println("\n________________________________\nIngrese la opcion que desea hacer:\n"+
                    "   1.Registrar Estudiante.\n"+
                    "   2.Listar Estudiantes.\n"+
                    "   3.Registrar un asignatura.\n"+
                    "   4.Listar Asignaturas.\n"+
                    "   5.Registrar matriculas.\n"+
                    "   6.Registrar notas.\n"+
                    "   7.Listar matriculas\n"+
                    "   8.Generar reporte de estudiantes\n"+
                    "   9.Salir.\n");
            System.out.print("Ingrese su respuesta-> ");
            int option = Integer.parseInt(sc.nextLine());
            switch(option){
                case 1:
                    registerEstudiante();
                    break;
                case 2:
                    listEstudiantes();
                    break;
                case 3:
                    registerAsignatura();
                    break;
                case 4:
                    listAsignaturas();
                    break;                
                case 5:
                    registerMatricula();
                    break;                
                case 6:
                    registerNotas();
                    break;
                case 7:
                    listMatriculas();
                    break;
                case 8:
                    genFileEstudiantes();
                    break;
                case 9:
                    validation = false;
                    break;
                default:
                    System.out.println("Por favor ingrese una opcion valida");
            }
            
        }while(validation);
    }
}
