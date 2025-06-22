package usuario;

import appWeb.Recategorizador;

public class NivelExpertoValidado extends NivelConocimiento {

	@Override
	public boolean esExperto() {
		return true;
	}

	@Override
	protected boolean debeRecategorizarse(Recategorizador recategorizador, Usuario usuario, int cantEnviosEsperados,
			int cantRevisionesEsperadas, int cantDiasConsiderados) {
		return false;
	}

	@Override
	protected NivelConocimiento nuevoNivel() {
		return this; //no cambia nunca en caso de usarse en un futuro y mantener el polimorfismo
	}

}
