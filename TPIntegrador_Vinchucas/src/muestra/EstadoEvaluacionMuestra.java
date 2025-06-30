package muestra;

import java.util.List;

import usuario.Opinion;
import usuario.Resultado;

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
	
	//desgloso el formato normal para calcular el resultadoActual en cada fase de votacion, 
	//usando las opiniones que necesita segun la fase que le corresponde y 
	//cambiando el comportamiento para cuando la muestra se encuentre verificada
	protected Resultado resultadoActual(List<Opinion> opiniones) {
	    Resultado resultadoMasFrecuente = null;
	    int maxApariciones = 0;
	    boolean empate = false;
	    
	    //itero sobre los resultados posibles para ver la cantidad de apariciones
	    for (Resultado resultadoCandidato : Resultado.values()) {
	        int aparicionesCandidato = 0;
	        for (Opinion op : opiniones) {
	            if (op.esMismoResultado(resultadoCandidato)) {
	            	aparicionesCandidato++;
	            }
	        }

	        if (aparicionesCandidato > maxApariciones) {
	        	maxApariciones = aparicionesCandidato;
	            resultadoMasFrecuente = resultadoCandidato;
	            empate = false;
	        } else if (aparicionesCandidato == maxApariciones) {
	            empate = true;
	        }
	    }
	    
	    //siempre hay al menos 1 opinion que es la del usuario enviador y por ende no hace falta corroborar si aparicionesCandidato es > 0
	    return empate ? Resultado.NO_DEFINIDO : resultadoMasFrecuente;
	}
}