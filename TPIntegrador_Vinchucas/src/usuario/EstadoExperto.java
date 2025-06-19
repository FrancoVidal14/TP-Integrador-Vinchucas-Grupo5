package usuario;

import Vinchucas.AplicacionWeb;

public class EstadoExperto implements EstadoUsuario {

	@Override
	public boolean esExperto() {
		return true;
	}

	@Override
	public void cambiarEstado(Usuario usuario) {
		usuario.setEstado(new EstadoBasico());
	}
	
	public void recategorizarSiCorresponde(AplicacionWeb aplicacionWeb, Usuario usuario,
			int cantEnviosEsperados, int cantRevisionesEsperadas, int cantDiasConsiderados) {
		if (!aplicacionWeb.usuarioCumpleReglasPromocion(usuario, cantEnviosEsperados, cantRevisionesEsperadas, cantDiasConsiderados)) {
			this.cambiarEstado(usuario);
		}
	}
}
