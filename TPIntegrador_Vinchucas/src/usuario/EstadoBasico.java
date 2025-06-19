package usuario;

import Vinchucas.Recategorizador;

public class EstadoBasico implements EstadoUsuario {

	@Override
	public boolean esExperto() {
		return false;
	}

	@Override
	public void cambiarEstado(Usuario usuario) {
		usuario.setEstado(new EstadoExperto());
	}

	@Override
	public void recategorizarSiCorresponde(Recategorizador recategorizador, Usuario usuario,
			int cantEnviosEsperados, int cantRevisionesEsperadas, int cantDiasConsiderados) {
		if (recategorizador.cumpleCondiciones(usuario, cantEnviosEsperados, cantRevisionesEsperadas, cantDiasConsiderados)) {
			this.cambiarEstado(usuario);
		}
	}
}
