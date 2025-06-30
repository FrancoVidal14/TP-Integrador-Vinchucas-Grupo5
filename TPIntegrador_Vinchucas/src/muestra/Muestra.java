package muestra;

import java.time.LocalDateTime;
import java.util.List;

import usuario.Opinion;
import usuario.Resultado;
import usuario.Usuario;
import zonaCobertura.Ubicacion;

public class Muestra {
	private LocalDateTime fechaCreacion;
	private Ubicacion ubicacionOrigen;
	private Usuario usuarioEnviador;
	private EvaluacionMuestra evaluacionMuestra = new EvaluacionMuestra();
	private RegistroDeValidaciones receptor;
	
	public Muestra(LocalDateTime fechaCreacion, Ubicacion ubicacionOrigen, Usuario usuarioEnviador, Opinion opinionUsuarioEnviador, RegistroDeValidaciones reg) throws Exception {
		this.fechaCreacion = fechaCreacion;
		this.ubicacionOrigen = ubicacionOrigen;
		this.usuarioEnviador = usuarioEnviador;
		this.evaluacionMuestra.procesarOpinion(this, opinionUsuarioEnviador);
		this.receptor = reg;
	}
	
	//getters y equals
	public LocalDateTime getFecha() {
		return fechaCreacion;
	}
	
	public Ubicacion getUbicacion() {
		return this.ubicacionOrigen;
	}
	
	public Usuario getUsuario() {
		return usuarioEnviador;
	}
	
	public List<Opinion> getOpiniones(){
		return this.evaluacionMuestra.getOpiniones();
	}
	
	public List<Opinion> getOpinionesDe(Usuario usuario){
		return this.getOpiniones().stream().filter(opinion -> opinion.esUsuarioOpinador(usuario)).toList();
	}
	
	public RegistroDeValidaciones getRegistro() {
		return this.receptor;
	}
	
	public boolean esUsuarioEnviador(Usuario usuario) {
		return this.usuarioEnviador.equals(usuario);
	}
	
	public boolean esMismaMuestra(Muestra muestra) {
		return muestra.equals(this);
	}
	
	public boolean generadaEnUltimos(int ultimosDias) {
		LocalDateTime fechaComienzoValidez = LocalDateTime.now().minusDays(ultimosDias);
		return this.fechaCreacion.isAfter(fechaComienzoValidez);
	}
	
	public void setReceptor(RegistroDeValidaciones receptor) {
		this.receptor = receptor;
	}
	
	//Evaluacion de muestra
	public void procesarOpinion(Opinion opinion) throws Exception {
		this.evaluacionMuestra.procesarOpinion(this, opinion);
	}
	
	public Resultado resultadoActual() {
		return this.evaluacionMuestra.getResultadoActual();
	}
	
	public LocalDateTime buscarFechaUltimaVotacion() {
		return evaluacionMuestra.getFechaUltimaVotacion();
	}

	public boolean usuarioHizoRevisionExitosa(Usuario usuario, int cantDiasConsiderados) {
		return this.getOpinionesDe(usuario).stream().anyMatch(o -> o.esUsuarioOpinador(usuario) && o.generadaEnUltimos(cantDiasConsiderados));
	}
	
	public boolean esMuestraVerificada() {
		return this.evaluacionMuestra.esVerificada();
	}
	
	protected void enviarMuestraVerificada() {
		this.receptor.recibirMuestraValidada(this);
	}
}