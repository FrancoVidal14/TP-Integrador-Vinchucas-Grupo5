package FiltrosDeBusqueda;

import muestra.Muestra;

public class CriterioPorMuestraVerificada implements Criterio{
	
	@Override
	public boolean cumpleMuestra(Muestra m) {
		return m.esMuestraVerificada();
	}	
}