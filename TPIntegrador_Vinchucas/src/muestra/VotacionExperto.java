package muestra;

import usuario.Opinion;

public class VotacionExperto extends EstadoEvaluacionMuestra {

	@Override
	public void procesarOpinion(EvaluacionMuestra evMuestra, Muestra muestra, Opinion opinion) throws Exception {
		super.procesarOpinion(evMuestra, muestra, opinion);
		if (!super.opinoExperto(opinion)) throw new Exception("Solo pueden votar expertos en la evaluacion actual de la muestra");
		evMuestra.agregarOpinion(opinion);
		this.verificarCambioEstado(evMuestra, opinion);
	}

	// decido verificar mediante la ultima opinion si la muestra puede ser verificada
	private void verificarCambioEstado(EvaluacionMuestra evMuestra, Opinion opinion) {
		long cantidad = evMuestra.getOpiniones().stream().filter(o -> this.opinoExperto(o))
				.filter(o -> o.getResultado().equals(opinion.getResultado())).count();

		if (cantidad >= 2) {
			this.cambiarEstado(evMuestra);
		}
	}

	@Override
	protected void cambiarEstado(EvaluacionMuestra evMuestra) {
		evMuestra.setEstado(new MuestraVerificada());
	}

}
