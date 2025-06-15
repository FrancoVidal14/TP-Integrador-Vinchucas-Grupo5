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
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de organización no puede ser nulo");
        }
        this.tipo = tipo;
}
}