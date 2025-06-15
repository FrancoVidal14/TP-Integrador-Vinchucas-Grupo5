package Vinchucas;

import java.util.List;

import muestra.Muestra;

import java.util.ArrayList;

public class ManejadorDeEventos {
	
	private List<NotificacionesPorMuestras> observadores = new ArrayList<>();
	
	
	public void registrarOrganizacionEnZonas(NotificacionesPorMuestras o, List<ZonaDeCobertura> zonas) {
		
	}
	
	public void sacarDelRegistroEnZonas(NotificacionesPorMuestras o, List<ZonaDeCobertura> zonas) {
		
	}

	public List<NotificacionesPorMuestras> getObservadores() {
		return observadores;
	}

	public void setObservadores(List<NotificacionesPorMuestras> observadores) {
		this.observadores = observadores;
	}
	
	public void recibirInformacionDeMuestraRegistrada(Muestra muestra) {
		for(NotificacionesPorMuestras o : observadores) {
			o.recibirNotificacionDeRegistroDe(muestra);
		}
	}
	
	public void recibirInformacionDeMuestraValidada(Muestra muestra) {
		for(NotificacionesPorMuestras o : observadores) {
			o.recibirNotificacionDeValidacionDe(muestra);
		}	
	}
}
