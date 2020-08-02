package entidades;

public class Asignatura {
	private String codigo;
	private String nombre;
	private int ciclo;
	private int numeroCreditos;
	
	public Asignatura() {
	}
	
	public Asignatura(String codigo, String nombre, int ciclo, int numeroCreditos) {
		this.codigo= codigo;
		this.nombre = nombre;
		this.ciclo = ciclo;
		this.numeroCreditos = numeroCreditos;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCiclo() {
		return ciclo;
	}

	public void setCiclo(int ciclo) {
		this.ciclo = ciclo;
	}

	public int getNumeroCreditos() {
		return numeroCreditos;
	}

	public void setNumeroCreditos(int numeroCreditos) {
		this.numeroCreditos = numeroCreditos;
	}
        
        public String toString(){
            return "["+this.getCodigo()+"]"+" "+this.getNombre()+" |ciclo n°"+this.getCiclo()+" "+ this.getNumeroCreditos()+" creditos";
        }
        
        public int getSize(){
            return this.getCodigo().length() * 2 + this.getNombre().length()*2 +  4 + 4;
        }
        
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Asignatura other = (Asignatura) obj;
		if (codigo == null) {
			if (other.getCodigo() != null)
				return false;
		} else if (!codigo.equals(other.getCodigo()))
			return false;
		return true;
	}
	
	
}
