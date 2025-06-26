package usuario;

import appWeb.Recategorizador;

public class NivelBasico extends NivelConocimiento {

	@Override
	public boolean esExperto() {
		return false;
	}

	@Override
	protected boolean debeRecategorizarse(Recategorizador recategorizador, Usuario usuario, int cantEnviosEsperados,
			int cantRevisionesEsperadas, int cantDiasConsiderados) {
		return recategorizador.cumpleCondiciones(usuario, cantEnviosEsperados, cantRevisionesEsperadas, cantDiasConsiderados);
	}

	@Override
	protected NivelConocimiento nuevoNivel() {
		return new NivelExperto();
	}
}
