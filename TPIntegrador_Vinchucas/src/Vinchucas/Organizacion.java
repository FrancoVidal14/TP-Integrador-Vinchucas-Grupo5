package Vinchucas;

public class Organizacion {
	private Ubicacion ubicacion;
	private TipoDeOrganizacion tipo;
	private int cantEmpleados;
	
	public Organizacion(Ubicacion ubicacion, TipoDeOrganizacion tipo, int cantEmpleados) {
		this.setUbicacion(ubicacion);
		this.setTipo(tipo);
		this.setCantEmpleados(cantEmpleados);
	}

	public Ubicacion getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

	public int getCantEmpleados() {
		return cantEmpleados;
	}

	public void setCantEmpleados(int cantEmpleados) {
		this.cantEmpleados = cantEmpleados;
	}

	public TipoDeOrganizacion getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeOrganizacion tipo) {
		this.tipo = tipo;
	}


}
