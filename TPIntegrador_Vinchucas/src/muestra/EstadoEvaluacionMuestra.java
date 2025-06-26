package muestra;

import java.util.List;

import usuario.Opinion;

public abstract class EstadoEvaluacionMuestra {
	
	protected abstract void cambiarEstado(EvaluacionMuestra evMuestra);
	
	public void procesarOpinion(EvaluacionMuestra evMuestra, Muestra muestra, Opinion opinion) throws Exception {
		this.cumpleReglasBase(muestra, opinion, evMuestra.getOpiniones());
	};
	
	private void cumpleReglasBase(Muestra muestra, Opinion opinion, List<Opinion> opinionesMuestra) throws Exception {;
		if (this.usuarioOpinaOtraVez(opinion, opinionesMuestra) && this.esUsuarioEnviador(muestra, opinion)) throw new Exception("El usuario que envio la muestra no puede volver a opinar sobre ella");
		if (this.usuarioOpinaOtraVez(opinion, opinionesMuestra)) throw new Exception("El usuario no puede volver a opinar sobre esta muestra");
	}
	
	private boolean usuarioOpinaOtraVez (Opinion opinion, List<Opinion> opinionesMuestra){
		return opinionesMuestra.stream().anyMatch(opi -> opi.esUsuarioOpinador(opinion.getUsuario()));
	}
	
	private boolean esUsuarioEnviador(Muestra muestra, Opinion opinion) {
		return muestra.esUsuarioEnviador(opinion.getUsuario());
	}
	
	protected abstract boolean esVerificado();
}