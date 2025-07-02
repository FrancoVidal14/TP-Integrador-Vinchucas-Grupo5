package muestra;

import java.util.ArrayList;
import java.util.List;

public class ManejadorDeMuestras {
	
	private List<IObserverMuestra> registrados;
	
	public ManejadorDeMuestras() {
		this.registrados = new ArrayList<>();
	}
	
	public List<IObserverMuestra> getObservadores() {
		return this.registrados;
	}
	
	public void setObservadores(List<IObserverMuestra> rs) {
		this.registrados = rs;
	}
	
	public void suscribirObservador(IObserverMuestra r) {
		getObservadores().add(r);
	}
	
	public void desuscribirObservador(IObserverMuestra r) {
		getObservadores().remove(r);
	}
	
	public void notificarValidacion(Muestra m) {
		for(IObserverMuestra r : registrados) {
			r.recibirMuestraValidada(m);
		}
	}
}
