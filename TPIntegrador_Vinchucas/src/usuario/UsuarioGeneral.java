package usuario;

import Vinchucas.AplicacionWeb;

public class UsuarioGeneral extends Usuario {

	public UsuarioGeneral() {
		this.estado = new EstadoBasico();
	}
	
	@Override
	public void recategorizarSiCorresponde(AplicacionWeb aplicacionWeb, int cantEnviosEsperados, int cantRevisionesEsperadas, int cantDiasConsiderados)  {
		this.estado.recategorizarSiCorresponde(aplicacionWeb, this, cantEnviosEsperados, cantRevisionesEsperadas, cantDiasConsiderados);
	}
	
	protected void setEstado(EstadoUsuario estado) {
		this.estado = estado;
	}
	
	public void decidirSiSoyExperto() {
		
	}
}
