package entidades;

public class Estudiante {
	private String codigo;
	private String dni;
	private String apellidos;
	private String nombres;
	private String direccion;

	public Estudiante() {
	}

	public Estudiante(String codigo, String dni, String apellidos, String nombre, String direccion) {
		this.codigo = codigo;
		this.dni = dni;
		this.apellidos = apellidos;
		this.nombres = nombre;
		this.direccion = direccion;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getDni() {
		return dni;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setNombre(String nombre) {
		this.nombres = nombre;
	}

	public String getNombre() {
		return nombres;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDireccion() {
		return direccion;
	}
        
        public String toString(){
            String m = "["+this.codigo+"] "+this.dni+" "+this.apellidos+" "+this.nombres;
            return m;
        }
        
        public int getSize(){
            return this.getCodigo().length() * 2 + this.getDni().length() * 2 +this.getApellidos().length() * 2 +
                    this.getNombre().length() * 2 + this.getDireccion().length() * 2;
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
		Estudiante other = (Estudiante) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.getCodigo()))
			return false;
		return true;
	}
	
	
}
