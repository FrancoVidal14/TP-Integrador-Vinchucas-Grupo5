package muestra;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import usuario.Opinion;

public class EvaluacionMuestra {
	
	private EstadoEvaluacionMuestra estadoEvaluacion;
	private List<Opinion> opiniones;
	
	public EvaluacionMuestra() {
		this.opiniones = new ArrayList<>();
		this.estadoEvaluacion = new VotacionGeneral();
	}
	
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
		if(getOpiniones().isEmpty()) {
			throw new IllegalArgumentException("No hay opiniones sobre esta muestra");
		}
		return getOpiniones().getLast().getFecha();
	}
}
