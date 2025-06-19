package muestra;

import usuario.Opinion;

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
			//notifica al sistema que se valido la muestra
			
		}
	}

	@Override
	protected void cambiarEstado(EvaluacionMuestra evMuestra) {
		evMuestra.setEstado(new MuestraVerificada());
	}
}