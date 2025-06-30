package muestra;

import java.util.List;

import usuario.Opinion;
import usuario.Resultado;

public class MuestraVerificada extends EstadoEvaluacionMuestra {

	@Override
	public void procesarOpinion(EvaluacionMuestra evMuestra, Muestra muestra, Opinion opinion) throws Exception {
		throw new Exception("No se puede opinar en la muestra porque esta verificada");
	}

	@Override
	protected void cambiarEstado(EvaluacionMuestra evMuestra) {
		// TODO Auto-generated method stub
	}

	@Override
	protected boolean esVerificado() {
		return true;
	}

	@Override
	protected Resultado resultadoActual(List<Opinion> opiniones) {
		return opiniones.getLast().getResultado();
		// se asume que la ultima opinion del experto que se guardo en el
		// estado VotacionExperto es el resultado correcto debido al proceso
		// que se lleva a cabo para pasar a estado MuestraVerificada y que
		// en este estado ya no se puede recibir opiniones
	}
}