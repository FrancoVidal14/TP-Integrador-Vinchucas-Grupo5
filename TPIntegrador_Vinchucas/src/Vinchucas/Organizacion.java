package Vinchucas;

import muestra.Muestra;

public class Organizacion implements NotificacionesPorMuestras{
	private Ubicacion ubicacion;
	private TipoDeOrganizacion tipo;
	private int cantEmpleados;
	private FuncionalidadExterna funcionalidadRegistro;
	private FuncionalidadExterna funcionalidadValidacion;
	
	public Organizacion(Ubicacion ubicacion, TipoDeOrganizacion tipo, int cantEmpleados, FuncionalidadExterna fun1, FuncionalidadExterna fun2) {
		this.setUbicacion(ubicacion);
		this.setTipo(tipo);
		this.setCantEmpleados(cantEmpleados);
		this.setFuncionalidadRegistro(fun1);
		this.setFuncionalidadValidacion(fun2);
	}

	public Ubicacion getUbicacion() {
		return ubicacion;
	}

    public void setUbicacion(Ubicacion ubicacion) {
        if (ubicacion == null) {
            throw new IllegalArgumentException("La ubicación no puede ser nula");
        }
        this.ubicacion = ubicacion;
    }

	public int getCantEmpleados() {
		return cantEmpleados;
	}

	public void setCantEmpleados(int cantEmpleados) {
	    if (cantEmpleados < 0) {
	        throw new IllegalArgumentException("La cantidad de empleados no puede ser negativa");
	    }
	    this.cantEmpleados = cantEmpleados;
	}
	
	public TipoDeOrganizacion getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeOrganizacion tipo) {
		this.tipo = tipo;
	}

	@Override
	public void recibirNotificacionDeRegistroDe(Muestra m, ZonaDeCobertura zona) {
		getFuncionalidadRegistro().nuevoEvento(this, zona, m);
	}

	@Override
	public void recibirNotificacionDeValidacionDe(Muestra m, ZonaDeCobertura z) {
		getFuncionalidadValidacion().nuevoEvento(this, z, m);
	}

	public FuncionalidadExterna getFuncionalidadRegistro() {
		return funcionalidadRegistro;
	}

	public void setFuncionalidadRegistro(FuncionalidadExterna funcionalidadRegistro) {
		this.funcionalidadRegistro = funcionalidadRegistro;
	}

	public FuncionalidadExterna getFuncionalidadValidacion() {
		return funcionalidadValidacion;
	}

	public void setFuncionalidadValidacion(FuncionalidadExterna funcionalidadValidacion) {
		this.funcionalidadValidacion = funcionalidadValidacion;
	}
}