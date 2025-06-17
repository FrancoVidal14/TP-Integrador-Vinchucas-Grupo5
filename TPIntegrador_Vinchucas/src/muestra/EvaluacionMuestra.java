package muestra;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import usuario.Opinion;

public class EvaluacionMuestra {
	
	private EstadoEvaluacionMuestra estadoEvaluacion = new VotacionGeneral();
	private List<Opinion> opiniones = new ArrayList<>();
	
	public void procesarOpinion(Muestra muestra, Opinion opinion) throws Exception {
		this.estadoEvaluacion.procesarOpinion(this, muestra, opinion);
	};
	
	public List<Opinion> getOpiniones(){
		return this.opiniones;
	}
	
	protected void agregarOpinion(Opinion opinion) {
		this.opiniones.add(opinion);
	}

	protected void setEstado(EstadoEvaluacionMuestra estado) {
		this.estadoEvaluacion = estado;
	}
	
	public LocalDateTime getFechaUltimaVotacion() {
		//TEST: si esta vacia lanza excepcion
		return getOpiniones().getLast().getFecha();
	}

	public void enviarRegistro(Muestra muestra) {
		this.estadoEvaluacion.enviarRegistroSiEsVerificada(muestra);
	}
}