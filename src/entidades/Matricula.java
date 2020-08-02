package entidades;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class Matricula {
	private GregorianCalendar fecha;
	private PeriodoAcademico periodo;
	private Estudiante estudiante;
	private ArrayList<Asignatura> cursosMatriculados;
	private ArrayList<Float> calificaciones;
	
	public Matricula() {
	}
	
	public Matricula(GregorianCalendar fecha, PeriodoAcademico periodo, 
					Estudiante estudiante,ArrayList<Asignatura> cursosMatriculados,
					ArrayList<Float> calificaciones) {
		this.fecha = fecha;
		this.periodo = periodo;
		this.estudiante = estudiante;
		this.cursosMatriculados = cursosMatriculados;
		this.calificaciones = calificaciones;
	}
	public Matricula(int dia, int mes, int year, PeriodoAcademico periodo, 
			Estudiante estudiante,ArrayList<Asignatura> cursosMatriculados,
			ArrayList<Float> calificaciones) {
		
		GregorianCalendar f = new GregorianCalendar();
		f.set(year, mes-1, dia);
		this.fecha = f;
		this.periodo = periodo;
		this.estudiante = estudiante;
		this.cursosMatriculados = cursosMatriculados;
		this.calificaciones = calificaciones;
	}

	public GregorianCalendar getFecha() {
		return fecha;
	}
        
        public int getDia(){
            return this.getFecha().get(Calendar.DAY_OF_MONTH);
        }
        public int getMes(){
            return this.getFecha().get(Calendar.MONTH+1);
        }
        public int getYear(){
            return this.getFecha().get(Calendar.YEAR);
        }

	public void setFecha(GregorianCalendar fecha) {
		this.fecha = fecha;
	}

	public PeriodoAcademico getPeriodo() {
		return periodo;
	}

	public void setPeriodo(PeriodoAcademico periodo) {
		this.periodo = periodo;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public ArrayList<Asignatura> getCursosMatriculados() {
		return cursosMatriculados;
	}

	public void setCursosMatriculados(ArrayList<Asignatura> cursosMatriculados) {
		this.cursosMatriculados = cursosMatriculados;
	}
	
	public void addAsignatura(Asignatura a) {
		if(cursosMatriculados == null) {
			cursosMatriculados = new ArrayList<Asignatura>();
		}
		cursosMatriculados.add(a);
	}

	public ArrayList<Float> getCalificaciones() {
		return calificaciones;
	}

	public void setCalificaciones(ArrayList<Float> calificaciones) {
		this.calificaciones = calificaciones;
	}
        
	public void setCalificacion(int index, float f){
            calificaciones.set(index, f);
        }
        
	public void addCalificacion(Float f) {
		if(calificaciones == null) {
			calificaciones = new ArrayList<Float>();
		}
		calificaciones.add(f);
	}
        
        public boolean checkAsignatura(Asignatura a){
            for(Asignatura asignatura: cursosMatriculados){
                if(asignatura.equals(a)){
                    return true;
                }
            }
            return false;
        }
        
        public String toString(){
            return fecha.get(Calendar.DAY_OF_MONTH)+"/"+(fecha.get(Calendar.MONTH)+1)+"/"+fecha.get(Calendar.YEAR)+
                    ""+periodo.toString()+" "+ estudiante.getCodigo();
        }
        public String reporteToString(){
            String m = periodo.toString()+"["+estudiante.getCodigo()+"]: \n";
            for(int i = 0; i<cursosMatriculados.size();i++){
                Asignatura c = cursosMatriculados.get(i);
                m+="    ["+c.getCodigo()+"] "+c.getNombre() +"  |"+calificaciones.get(i);
            }
            return m;
        }
        
        public String genCode(){
            return this.getPeriodo().genCode()+this.getEstudiante().getCodigo();
        }
        
        public int getSize(){
            return this.genCode().length()*2 + 4 + 4 + 4 + this.periodo.genCode().length()*2 + this.getEstudiante().getCodigo().length()*2;
        }
        
        public int getCalificacionLength(Asignatura a){
            return this.genCode().length()*2 + a.getCodigo().length()*2 + 4 + 4;
        }
        
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.periodo);
        hash = 23 * hash + Objects.hashCode(this.estudiante);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Matricula other = (Matricula) obj;
        if (!Objects.equals(this.periodo, other.periodo)) {
            return false;
        }
        if (!Objects.equals(this.estudiante, other.estudiante)) {
            return false;
        }
        return true;
    }
}
