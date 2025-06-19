package FiltrosDeBusqueda;

import muestra.Muestra;
import usuario.Resultado;

public class CriterioTipoDeInsecto implements Criterio {
	
	private Resultado resultado;
	
	public CriterioTipoDeInsecto(Resultado res) {
		this.resultado = res;
	}
	
	@Override
	public boolean cumpleMuestra(Muestra m) {
		return m.resultadoActual().equals(this.resultado);
	}
}