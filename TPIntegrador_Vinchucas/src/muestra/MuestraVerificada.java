package muestra;

import usuario.Opinion;

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
}