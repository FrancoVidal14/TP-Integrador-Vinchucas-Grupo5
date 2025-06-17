package FiltrosDeBusqueda;

import muestra.EstadoEvaluacionMuestra;
import muestra.Muestra;

public class CriterioNivelDeVerificacion implements Criterio{
	
	private EstadoEvaluacionMuestra estado;
	
	public CriterioNivelDeVerificacion(EstadoEvaluacionMuestra estado) {
		this.estado = estado;
	}
	
	@Override
	public boolean cumpleMuestra(Muestra m) {
		return m.getEvaluacion().getEstado().equals(estado);
	}	
}