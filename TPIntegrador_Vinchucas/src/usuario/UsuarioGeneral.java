package usuario;

import Vinchucas.Recategorizador;

public class UsuarioGeneral extends Usuario {

	public UsuarioGeneral() {
		this.estado = new EstadoBasico();
	}
	
	@Override
	public void recategorizarSiCorresponde(Recategorizador recategorizador, int cantEnviosEsperados, int cantRevisionesEsperadas, int cantDiasConsiderados)  {
		this.estado.recategorizarSiCorresponde(recategorizador, this, cantEnviosEsperados, cantRevisionesEsperadas, cantDiasConsiderados);
	}
	
	protected void setEstado(EstadoUsuario estado) {
		this.estado = estado;
	}
	
	public void decidirSiSoyExperto() {
		
	}
}
