package muestra;

import java.util.List;

import usuario.NivelConocimiento;
import usuario.Opinion;
import usuario.Usuario;

public abstract class EstadoEvaluacionMuestra {
	
	protected abstract void cambiarEstado(EvaluacionMuestra evMuestra);
	
	public void procesarOpinion(EvaluacionMuestra evMuestra, Muestra muestra, Opinion opinion) throws Exception {
		this.cumpleReglasBase(muestra, opinion, evMuestra.getOpiniones());
	};
	
	private void cumpleReglasBase(Muestra muestra, Opinion opinion, List<Opinion> opinionesMuestra) throws Exception {
		if (this.usuarioEnviadorOpinaOtraVez(muestra, opinion)) throw new Exception("El usuario que envio la muestra no puede volver a opinar sobre ella");
		if (this.usuarioOpinaOtraVez(opinion, opinionesMuestra)) throw new Exception("El usuario no puede volver a opinar sobre esta muestra");
	}
	
	private boolean usuarioEnviadorOpinaOtraVez(Muestra muestra, Opinion opinion){
		return muestra.getUsuario().getDni() ==  opinion.getUsuario().getDni();
	}
	
	private boolean usuarioOpinaOtraVez (Opinion opinion, List<Opinion> opinionesMuestra){
		List<Usuario> usuariosOpinaron = opinionesMuestra.stream().map(opi -> opi.getUsuario()).toList();
		return usuariosOpinaron.stream().anyMatch(user -> user.getDni() == opinion.getUsuario().getDni());
	}
	
	protected boolean opinoExperto(Opinion opinion) {
		return opinion.getUsuario().getNivelConocimiento().equals(NivelConocimiento.EXPERTO);
	}
}
