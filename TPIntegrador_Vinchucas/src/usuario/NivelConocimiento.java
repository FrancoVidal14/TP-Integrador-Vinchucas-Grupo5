package usuario;

import appWeb.Recategorizador;

public abstract class NivelConocimiento {
	
	public void recategorizarSiCorresponde(Recategorizador recategorizador, Usuario usuario, int cantEnviosEsperados, int cantRevisionesEsperadas, int cantDiasConsiderados) {
		if(this.debeRecategorizarse(recategorizador, usuario, cantEnviosEsperados, cantRevisionesEsperadas, cantDiasConsiderados)) {
			usuario.setNivelConocimiento(this.nuevoNivel());
		}
	}
	
	public abstract boolean esExperto();
	protected abstract boolean debeRecategorizarse(Recategorizador recategorizador, Usuario usuario, int cantEnviosEsperados, int cantRevisionesEsperadas, int cantDiasConsiderados);
	protected abstract NivelConocimiento nuevoNivel();
}
