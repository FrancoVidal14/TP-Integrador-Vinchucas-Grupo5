package FiltrosDeBusqueda;

import muestra.Muestra;

public class CriterioPorMuestraEnVotacion implements Criterio{

	@Override
	public boolean cumpleMuestra(Muestra m) {
		return !m.esMuestraVerificada();
	}

}
