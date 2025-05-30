package muestra;

import java.util.List;

import usuario.NivelConocimiento;
import usuario.Opinion;

public class VotacionGeneral extends EstadoEvaluacionMuestra {

	@Override
	public void procesarOpinion(EvaluacionMuestra evMuestra, Muestra muestra, Opinion opinion) throws Exception {
		super.procesarOpinion(evMuestra, muestra, opinion);
		evMuestra.agregarOpinion(opinion);
		if (super.opinoExperto(opinion)) this.cambiarEstado(evMuestra);
	}
	
	@Override
	protected void cambiarEstado(EvaluacionMuestra evMuestra) {
		evMuestra.setEstado(new VotacionExperto());
	}

}
