package muestra;

import Vinchucas.RegistroDeValidaciones;
import usuario.Opinion;

public class MuestraVerificada extends EstadoEvaluacionMuestra {
	
	private RegistroDeValidaciones registro;
	
	@Override
	public void procesarOpinion(EvaluacionMuestra evMuestra, Muestra muestra, Opinion opinion) throws Exception {
		throw new Exception("No se puede opinar en la muestra porque esta verificada");
	}	

	@Override
	protected void cambiarEstado(EvaluacionMuestra evMuestra) {
		// TODO Auto-generated method stub
	}

	@Override
	public void enviarRegistroSiEsVerificada(Muestra muestra) {
		registro.recibirMuestraValidada(muestra);
	}

}