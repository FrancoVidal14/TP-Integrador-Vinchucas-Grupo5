package muestra;

import java.util.List;

import usuario.Opinion;
import usuario.Resultado;

public class VotacionExperto extends EstadoEvaluacionMuestra {

	@Override
	public void procesarOpinion(EvaluacionMuestra evMuestra, Muestra muestra, Opinion opinion) throws Exception {
		super.procesarOpinion(evMuestra, muestra, opinion);
		if (!opinion.fueEmitidaPorExperto()) throw new Exception("Solo pueden votar expertos en la evaluacion actual de la muestra");
		evMuestra.agregarOpinion(opinion);
		this.verificarCambioEstado(muestra, evMuestra, opinion);
	}

	// decido verificar mediante la ultima opinion si la muestra puede ser verificada
	private void verificarCambioEstado(Muestra muestra, EvaluacionMuestra evMuestra, Opinion opinion) {
		long cantOpinionesIguales = evMuestra.getOpinionesExpertos().stream()
				.filter(o -> o.esMismoResultado(opinion.getResultado()))
				.count();

		if (cantOpinionesIguales >= 2) {
			this.cambiarEstado(evMuestra);
			muestra.enviarMuestraValidada();
		}
	}

	@Override
	protected void cambiarEstado(EvaluacionMuestra evMuestra) {
		evMuestra.setEstado(new MuestraVerificada());
	}

	@Override
	protected boolean esVerificado() {
		return false;
	}
	
	//envio la lista filtrada para solo calcular con la votacion de los expertos
	@Override
	protected Resultado resultadoActual(List<Opinion> opiniones) {
	    List<Opinion> expertas = opiniones.stream()
	        .filter(Opinion::fueEmitidaPorExperto)
	        .toList();
	    return super.resultadoActual(expertas);
	}
}