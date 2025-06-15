package Vinchucas;

import muestra.Muestra;

public class Organizacion implements NotificacionesPorMuestras{
	private Ubicacion ubicacion;
	private TipoDeOrganizacion tipo;
	private int cantEmpleados;
	private FuncionalidadExterna func;
	
	public Organizacion(Ubicacion ubicacion, TipoDeOrganizacion tipo, int cantEmpleados, FuncionalidadExterna fun) {
		this.setUbicacion(ubicacion);
		this.setTipo(tipo);
		this.setCantEmpleados(cantEmpleados);
		this.func = fun;
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
	
	public FuncionalidadExterna getFuncionalidad() {
		return func;
	}

	@Override
	public void recibirNotificacionDeRegistroDe(Muestra m) {
		//func.nuevoEvento(this, m.zonasDeCoberturaOcupadas(null), m);
		// me chilla porque la zona de cobertura de la muestra me trae una lista.
	}

	@Override
	public void recibirNotificacionDeValidacionDe(Muestra m) {
		//func.nuevoEvento(this, m.zonaDeCobertura, m;
		// lo mismo.
	}
}