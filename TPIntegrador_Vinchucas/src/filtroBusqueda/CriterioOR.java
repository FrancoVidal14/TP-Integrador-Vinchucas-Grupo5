package filtroBusqueda;

import muestra.Muestra;

public class CriterioOR extends CriterioBinario{

	public CriterioOR(Criterio c1, Criterio c2) {
		this.c1 = c1;
		this.c2 = c2;
	}

	@Override
	public boolean cumpleMuestra(Muestra m) {
		return c1.cumpleMuestra(m) || c2.cumpleMuestra(m);
	}
}