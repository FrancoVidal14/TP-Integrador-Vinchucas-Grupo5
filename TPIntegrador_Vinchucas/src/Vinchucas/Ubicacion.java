package Vinchucas;

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
	
	
}
