package usuario;

import Vinchucas.Recategorizador;

public class EstadoExperto implements EstadoUsuario {

	@Override
	public boolean esExperto() {
		return true;
	}

	@Override
	public void cambiarEstado(Usuario usuario) {
		usuario.setEstado(new EstadoBasico());
	}
	
	public void recategorizarSiCorresponde(Recategorizador recategorizador, Usuario usuario,
			int cantEnviosEsperados, int cantRevisionesEsperadas, int cantDiasConsiderados) {
		if (!recategorizador.cumpleCondiciones(usuario, cantEnviosEsperados, cantRevisionesEsperadas, cantDiasConsiderados)) {
			this.cambiarEstado(usuario);
		}
	}
}
