package muestra;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import usuario.Opinion;
import usuario.Resultado;

public class EvaluacionMuestra{
	
	private EstadoEvaluacionMuestra estadoEvaluacion = new VotacionGeneral();
	private List<Opinion> opiniones = new ArrayList<>();
	
	public void procesarOpinion(Muestra muestra, Opinion opinion) throws Exception {
		this.estadoEvaluacion.procesarOpinion(this, muestra, opinion);
	};
	
	public List<Opinion> getOpiniones(){
		return this.opiniones;
	}
	
	public List<Opinion> getOpinionesExpertos(){
		return this.opiniones.stream().filter(o -> o.fueEmitidaPorExperto()).toList();
	}
	
	protected void agregarOpinion(Opinion opinion) {
		this.opiniones.add(opinion);
	}

	protected void setEstado(EstadoEvaluacionMuestra estado) {
		this.estadoEvaluacion = estado;
	}
	
	public LocalDateTime getFechaUltimaVotacion() {
		//TEST: si esta vacia lanza excepcion
		return (getOpiniones().getLast()).getFecha();
	}

	public Resultado getResultadoActual() {
	    Resultado resultadoMasFrecuente = null;
	    int maxApariciones = 0;
	    boolean empate = false;
	    
	    //itero sobre los resultados posibles para ver la cantidad de apariciones
	    for (Resultado resultadoCandidato : Resultado.values()) {
	        int aparicionesCandidato = 0;
	        for (Opinion op : this.opiniones) {
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
	
	public boolean esVerificada() {
		return this.estadoEvaluacion.esVerificado();
	}
}