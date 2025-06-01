package usuario;

public abstract class Usuario {
	private int dni;
	private NivelConocimiento nivelConocimiento;
	
	public int getDni() {
		return this.dni;
	}
	
	public NivelConocimiento getNivelConocimiento() {
		return this.nivelConocimiento;
	}
	
	protected void setNivelConocimiento(NivelConocimiento nivel) {
		this.nivelConocimiento = nivel;
	}
	
	//metodo hook para recategorizar a los usuarios generales desde sistema?
//	protected void cambiarNivelConocimiento() {
//		
//	}
}