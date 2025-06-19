package usuario;

import Vinchucas.AplicacionWeb;

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
	public void recategorizarSiCorresponde(AplicacionWeb aplicacionWeb, Usuario usuario,
			int cantEnviosEsperados, int cantRevisionesEsperadas, int cantDiasConsiderados) {
		if (aplicacionWeb.usuarioCumpleReglasPromocion(usuario, cantEnviosEsperados, cantRevisionesEsperadas, cantDiasConsiderados)) {
			this.cambiarEstado(usuario);
		}
	}
}
