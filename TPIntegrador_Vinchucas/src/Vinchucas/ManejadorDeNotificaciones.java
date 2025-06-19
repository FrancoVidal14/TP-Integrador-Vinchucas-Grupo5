package Vinchucas;

import java.util.List;

import muestra.Muestra;

import java.util.ArrayList;

public class ManejadorDeNotificaciones {
	
	private List<NotificacionesPorMuestras> observadores = new ArrayList<>();
	
	public void registrarOrganizacionEnZonas(NotificacionesPorMuestras o) {
		getObservadores().add(o);
	}
	
	public void sacarDelRegistroEnZonas(NotificacionesPorMuestras o) {
		getObservadores().remove(o);
	}

	public List<NotificacionesPorMuestras> getObservadores() {
		return observadores;
	}

	public void setObservadores(List<NotificacionesPorMuestras> observadores) {
		this.observadores = observadores;
	}
	
	public void recibirInformacionDeMuestraRegistrada(Muestra muestra, ZonaDeCobertura zona) {
		for(NotificacionesPorMuestras o : observadores) {
			o.recibirNotificacionDeRegistroDe(muestra, zona);
		}
	}
	
	public void recibirInformacionDeMuestraValidada(Muestra muestra, ZonaDeCobertura zona) {
		for(NotificacionesPorMuestras o : observadores) {
			o.recibirNotificacionDeValidacionDe(muestra, zona);
		}	
	}
}