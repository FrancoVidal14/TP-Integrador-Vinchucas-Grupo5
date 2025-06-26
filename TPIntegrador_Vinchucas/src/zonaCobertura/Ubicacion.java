package zonaCobertura;

import java.util.List;

public class Ubicacion {
	private double latitud;
	private double longitud;
	
	public Ubicacion (double y, double x) {
		this.latitud = y;
		this.longitud = x;
	}

	public double getLongitud() {
		return longitud;
	}

	public double getLatitud() {
		return latitud;
	}
	
	public List<Ubicacion> ubicacionesAMenosDe(List<Ubicacion> ubicaciones, double km){
		CalculadorDistancia calculador = new CalculoDistancia();
    	return ubicaciones.stream().filter(u -> calculador.calcular(this,u) < km).toList();
	}
	
}
