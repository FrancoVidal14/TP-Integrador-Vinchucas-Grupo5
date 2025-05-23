package usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Vinchucas.AplicacionWeb;
import Vinchucas.Ubicacion;
import muestra.Muestra;

public abstract class Usuario {
	
	private List<Opinion> opiniones;
	private NivelConocimiento nivelConocimiento;
	
	public Usuario() {
		this.opiniones = new ArrayList<>();
	}
	
	//suponemos que siempre que se envie una muestra, es en el momento exacto. Como un boton "enviar" por lo que la fecha es la que capta en ese momento
	public void enviarMuestra(Ubicacion ubicacionOrigen, Opinion opinionUsuario, AplicacionWeb sistema) {
		Muestra muestra = new Muestra(LocalDateTime.now(), ubicacionOrigen, this, opinionUsuario);
		sistema.recibirMuestra(muestra);
		opiniones.add(opinionUsuario);
	}
	
	public void opinar(Muestra muestra, Opinion opinionUsuario) {
		muestra.procesarOpinion(opinionUsuario);
	}
	
	public NivelConocimiento getNivelConocimiento() {
		return this.nivelConocimiento;
	}
	
	protected void setNivelConocimiento(NivelConocimiento nivel) {
		this.nivelConocimiento = nivel;
	}
	
	//metodo hook para recategorizar a los usuarios generales desde sistema?
//	protected void cambiarNivelConocimiento() {
//		
//	}
}
