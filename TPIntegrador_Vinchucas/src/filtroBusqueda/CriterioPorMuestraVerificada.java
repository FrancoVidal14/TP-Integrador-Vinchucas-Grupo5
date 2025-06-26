package filtroBusqueda;

import muestra.Muestra;

public class CriterioPorMuestraVerificada implements Criterio{
	
	@Override
	public boolean cumpleMuestra(Muestra m) {
		return m.esMuestraVerificada();
	}	
}