package organizacion;

import muestra.Muestra;
import zonaCobertura.ZonaDeCobertura;

public interface FuncionalidadExterna {
	
	public void nuevoEvento(Organizacion o, ZonaDeCobertura z, Muestra m);
	
}