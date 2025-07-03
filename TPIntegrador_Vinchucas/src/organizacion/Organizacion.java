package organizacion;

import muestra.Muestra;
import zonaCobertura.NotificadosPorMuestras;
import zonaCobertura.Ubicacion;
import zonaCobertura.ZonaDeCobertura;

public class Organizacion implements NotificadosPorMuestras {
	private Ubicacion ubicacion;
	private TipoDeOrganizacion tipo;
	private int cantEmpleados;
	private FuncionalidadExterna funcionalidadRegistro;
	private FuncionalidadExterna funcionalidadValidacion;

	// Constructor
	public Organizacion(Ubicacion ubicacion, TipoDeOrganizacion tipo, int cantEmpleados,
	                    FuncionalidadExterna fun1, FuncionalidadExterna fun2) {
		this.setUbicacion(ubicacion);
		this.setTipo(tipo);
		this.setCantEmpleados(cantEmpleados);
		this.setFuncionalidadRegistro(fun1);
		this.setFuncionalidadValidacion(fun2);
	}

	// Getters
	public Ubicacion getUbicacion() {
		return ubicacion;
	}

	public TipoDeOrganizacion getTipo() {
		return tipo;
	}

	public int getCantEmpleados() {
		return cantEmpleados;
	}

	public FuncionalidadExterna getFuncionalidadRegistro() {
		return funcionalidadRegistro;
	}

	public FuncionalidadExterna getFuncionalidadValidacion() {
		return funcionalidadValidacion;
	}

	// Setters
	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

	public void setTipo(TipoDeOrganizacion tipo) {
		this.tipo = tipo;
	}

	public void setCantEmpleados(int cantEmpleados) {
		this.cantEmpleados = cantEmpleados;
	}

	public void setFuncionalidadRegistro(FuncionalidadExterna funcionalidadRegistro) {
		this.funcionalidadRegistro = funcionalidadRegistro;
	}

	public void setFuncionalidadValidacion(FuncionalidadExterna funcionalidadValidacion) {
		this.funcionalidadValidacion = funcionalidadValidacion;
	}

	// Métodos de notificación
	@Override
	public void recibirNotificacionDeRegistroDe(Muestra m, ZonaDeCobertura zona) {
		getFuncionalidadRegistro().nuevoEvento(this, zona, m);
	}

	@Override
	public void recibirNotificacionDeValidacionDe(Muestra m, ZonaDeCobertura z) {
		getFuncionalidadValidacion().nuevoEvento(this, z, m);
	}
}