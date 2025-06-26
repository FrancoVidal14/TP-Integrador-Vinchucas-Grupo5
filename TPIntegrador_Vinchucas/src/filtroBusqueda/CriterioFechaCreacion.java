package filtroBusqueda;

import java.time.LocalDateTime;

import muestra.Muestra;

public class CriterioFechaCreacion implements Criterio{
	
	private LocalDateTime fechaCreacion;
	
	public CriterioFechaCreacion(LocalDateTime fecha) {
		this.fechaCreacion = fecha;
	}
	
	@Override
	public boolean cumpleMuestra(Muestra m) {
		return m.getFecha().equals(fechaCreacion);
	}	
}