package usuario;

import Vinchucas.Recategorizador;

public class NivelExperto extends NivelConocimiento {

	@Override
	public boolean esExperto() {
		return true;
	}

	@Override
	protected boolean debeRecategorizarse(Recategorizador recategorizador, Usuario usuario, int cantEnviosEsperados,
			int cantRevisionesEsperadas, int cantDiasConsiderados) {
		return !recategorizador.cumpleCondiciones(usuario, cantDiasConsiderados, cantEnviosEsperados, cantRevisionesEsperadas);
	}

	@Override
	protected NivelConocimiento nuevoNivel() {
		return new NivelBasico();
	}
}
