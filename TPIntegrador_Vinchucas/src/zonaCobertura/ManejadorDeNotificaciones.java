package zonaCobertura;

import java.util.List;

import muestra.Muestra;

import java.util.ArrayList;

public class ManejadorDeNotificaciones {
	
	private List<NotificadosPorMuestras> notificados = new ArrayList<>();
	
	public void registrarOrganizacionEnZonas(NotificadosPorMuestras n) {
		getNotificados().add(n);
	}
	
	public void sacarDelRegistroEnZonas(NotificadosPorMuestras n) {
		getNotificados().remove(n);
	}

	public List<NotificadosPorMuestras> getNotificados() {
		return this.notificados;
	}

	public void setNotificados(List<NotificadosPorMuestras> observadores) {
		this.notificados = observadores;
	}
	
	public void recibirInformacionDeMuestraRegistrada(Muestra muestra, ZonaDeCobertura zona) {
		for(NotificadosPorMuestras n : this.notificados) {
			n.recibirNotificacionDeRegistroDe(muestra, zona);
		}
	}
	
	public void recibirInformacionDeMuestraValidada(Muestra muestra, ZonaDeCobertura zona) {
		for(NotificadosPorMuestras n : this.notificados) {
			n.recibirNotificacionDeValidacionDe(muestra, zona);
		}	
	}
}