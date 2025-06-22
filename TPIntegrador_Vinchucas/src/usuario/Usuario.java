package usuario;

import Vinchucas.Recategorizador;

public class Usuario {
	private NivelConocimiento nivelConocimiento;
	
	//constructor base, donde todos los usuarios iniciarian con nivel basico
	public Usuario() {
		this.nivelConocimiento = new NivelBasico();
	}
	
	//constructor para instanciar usuarios validados o usuarios con otro nivel en un futuro
	public Usuario(NivelConocimiento nivelInicial) {
		this.nivelConocimiento = nivelInicial;
	}
	
	public boolean esExperto() {
		return this.nivelConocimiento.esExperto();
	}
	
	//metodo para validar a un usuario externamente y sea siempre experto
	public void validarExternamente() {
        this.nivelConocimiento = new NivelExpertoValidado();
    }
	
	//metodo para modificar el nivel de conocimiento en base a la recategorizacion
    protected void setNivelConocimiento(NivelConocimiento nuevoNivel) {
        this.nivelConocimiento = nuevoNivel;
    }

    public void recategorizarSiCorresponde(Recategorizador recategorizador, int cantEnviosEsperados, int cantRevisionesEsperadas, int cantDiasConsiderados) {
        this.nivelConocimiento.recategorizarSiCorresponde(recategorizador, this, cantEnviosEsperados, cantRevisionesEsperadas, cantDiasConsiderados);
    }
}