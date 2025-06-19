package FiltrosDeBusqueda;

import muestra.Muestra;
import java.time.LocalDateTime;

public class CriterioFechaUltimaVotacion implements Criterio{

	private LocalDateTime fechaUltimavotacion;
	
	public CriterioFechaUltimaVotacion(LocalDateTime fecha) {
		this.fechaUltimavotacion = fecha;
	}
	
	@Override
	public boolean cumpleMuestra(Muestra m) {
		return m.buscarFechaUltimaVotacion().equals(fechaUltimavotacion);
	}
}