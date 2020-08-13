package entidades;

public class PeriodoAcademico {
	private int year ;
	private String semestre ;
        private String[] validos= {"I","II","Nivelacion"};
	public PeriodoAcademico(){
	} 
	public PeriodoAcademico (int year , String semestre){
	    this.year=year;
	    this.semestre = semestre;
	}
	public void setYear(int year){
	   this.year = year ;
	}
	public int getYear (){
	    return year;
	}
	public void setSemestre(String semestre){
	    this.semestre = semestre ;
	}
	public String getSemestre(){
	    return semestre ; 
	}
        
        public String toString(){
            return year+"-"+semestre;
        }
        public PeriodoAcademico nextPeriodo(){
            PeriodoAcademico p = new PeriodoAcademico();
            int tempYear = Integer.parseInt(this.toString().split("-")[0]);
            String tempSemestre = this.toString().split("-")[1];
            if(tempSemestre.equals("Nivelacion")){
                tempYear++;
                tempSemestre = validos[0];
            }else{
                if(tempSemestre.equals("I")){
                    tempSemestre = validos[1];
                }
                else{tempSemestre = validos[2];}
            }
            return new PeriodoAcademico(tempYear, tempSemestre);
        }
        public int getSize(){
            return 4 + 6 +this.getSemestre().length()*2;
        }
        
        public String genCode(){
            String s = semestre.equals("I")?"1":semestre.equals("II")?"2":"3";
            return Integer.toString(year).substring(2) + s;
        }
        
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + year;
		result = prime * result + ((semestre == null) ? 0 : semestre.hashCode());
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
		PeriodoAcademico other = (PeriodoAcademico) obj;
		if (year != other.getYear())
			return false;
		if (semestre == null) {
			if (other.getSemestre() != null)
				return false;
		} else if (!semestre.equals(other.getSemestre()))
			return false;
		return true;
	}
	
}
