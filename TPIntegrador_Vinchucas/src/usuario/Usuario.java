package usuario;

public abstract class Usuario {
	private NivelConocimiento nivelConocimiento;
	
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