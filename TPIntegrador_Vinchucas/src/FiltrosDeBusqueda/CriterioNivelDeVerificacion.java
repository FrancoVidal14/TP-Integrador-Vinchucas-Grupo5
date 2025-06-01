package FiltrosDeBusqueda;

import muestra.Muestra;

public class CriterioNivelDeVerificacion implements Criterio{
	
	private TipoVerificacion tipo;
	
	public CriterioNivelDeVerificacion(TipoVerificacion tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public boolean cumpleMuestra(Muestra m) {
		return m.getNivelVerificacion().equals(tipo);
	}	
}