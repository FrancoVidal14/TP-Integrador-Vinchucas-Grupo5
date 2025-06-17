package Vinchucas;

import muestra.Muestra;

public interface NotificacionesPorMuestras {

	public void recibirNotificacionDeRegistroDe(Muestra m, ZonaDeCobertura zona);
	
	public void recibirNotificacionDeValidacionDe(Muestra m, ZonaDeCobertura zona);
	
}