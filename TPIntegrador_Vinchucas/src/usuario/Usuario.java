package usuario;

import Vinchucas.Recategorizador;

public abstract class Usuario {
	protected EstadoUsuario estado;
	
	public boolean esExperto() {
		return this.estado.esExperto();
	}
	
	//metodo hook para que sobreescriba Usuario General
	protected void setEstado(EstadoUsuario estado) {
		
	}
	
	//metodo hook para recategorizar a los usuarios generales desde sistema
	public void recategorizarSiCorresponde(Recategorizador recategorizador,  int cantEnviosEsperados, int cantRevisionesEsperadas, int cantDiasConsiderados) {
		
	}
}