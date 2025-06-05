package FiltrosDeBusqueda;

import java.util.List;
import java.util.stream.Collectors;

import muestra.Muestra;

public class FiltroDeBusqueda {
	
	public List<Muestra> filtrarMuestras(List<Muestra> muestras, Criterio c) {
		return muestras.stream().filter(m -> c.cumpleMuestra(m)).collect(Collectors.toList());
	}
}