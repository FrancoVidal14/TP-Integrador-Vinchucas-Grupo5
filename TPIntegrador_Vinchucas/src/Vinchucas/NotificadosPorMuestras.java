package Vinchucas;

import muestra.Muestra;

public interface NotificadosPorMuestras {

	public void recibirNotificacionDeRegistroDe(Muestra m, ZonaDeCobertura zona);
	
	public void recibirNotificacionDeValidacionDe(Muestra m, ZonaDeCobertura zona);
	
}