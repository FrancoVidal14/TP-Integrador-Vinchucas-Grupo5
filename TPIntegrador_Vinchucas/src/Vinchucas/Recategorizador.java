package Vinchucas;

import usuario.Usuario;

public class Recategorizador {

	private IDatosUsuario datos;
	//interfaz que implementa appweb para tener los datos para el recategorizador sin exponer a la app web

	public Recategorizador(IDatosUsuario datos) {
		this.datos = datos;
	}

	public void recategorizarUsuarios(int cantEnviosEsperados, int cantRevisionesEsperadas, int cantDiasConsiderados) {
		for (Usuario usuario : datos.getUsuarios()) {
			//recategoriza al usuario segun el estado y si cumple las condiciones
			usuario.recategorizarSiCorresponde(this, cantEnviosEsperados, cantRevisionesEsperadas,
					cantDiasConsiderados);
		}
	}

    public boolean cumpleCondiciones(Usuario usuario, int cantDiasConsiderados, int cantEnviosEsperados, int cantRevisionesEsperadas) {
        return this.cantidadEnvios(usuario, cantDiasConsiderados) > cantEnviosEsperados && this.cantidadRevisionesExitosas(usuario,cantDiasConsiderados) > cantRevisionesEsperadas;
    }
    
    private long cantidadEnvios(Usuario usuario, int cantDiasConsiderados){
    	return datos.getMuestrasEnviadasPor(usuario).stream()
        .filter(m -> m.generadaEnUltimos(cantDiasConsiderados))
        .count();
    }
    
    /* por si las revisiones son todas las opiniones
    private long cantidadRevisiones(Usuario usuario, int cantDiasConsiderados){
    	return datos.getOpinionesDe(usuario).stream()
        .filter(op -> op.generadaEnUltimos(cantDiasConsiderados))
        .count();
    }
    */
    
    private int cantidadRevisionesExitosas(Usuario usuario, int dias) {
        return datos.getMuestrasEnviadasPor(usuario).stream()
            .filter(m -> m.usuarioHizoRevisionExitosa(usuario, dias))
            .toList().size();
    }
}
