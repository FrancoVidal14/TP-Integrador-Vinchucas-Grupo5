package usuario;

public abstract class Usuario {
	protected EstadoUsuario estado;
	
	public boolean esExperto() {
		return this.estado.esExperto();
	}
	
	//metodo hook para recategorizar a los usuarios generales desde sistema?
	protected void cambiarNivelConocimiento() {
		
	}
	
	//metodo hook para que sobreescriba Usuario General
	protected void setEstado(EstadoUsuario estado) {
		
	}
}