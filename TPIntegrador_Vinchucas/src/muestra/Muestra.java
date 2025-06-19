package muestra;

import java.time.LocalDateTime;
import java.util.List;

import Vinchucas.AplicacionWeb;
import Vinchucas.ZonaDeCobertura;
import Vinchucas.Ubicacion;
import usuario.Opinion;
import usuario.Resultado;
import usuario.Usuario;

public class Muestra{
	private LocalDateTime fechaCreacion;
	private Ubicacion ubicacionOrigen;
	private Usuario usuarioEnviador;
	private EvaluacionMuestra evaluacionMuestra = new EvaluacionMuestra();
	
	public Muestra(LocalDateTime fechaCreacion, Ubicacion ubicacionOrigen, Usuario usuarioEnviador, Opinion opinionUsuarioEnviador) throws Exception {
		this.fechaCreacion = fechaCreacion;
		this.ubicacionOrigen = ubicacionOrigen;
		this.usuarioEnviador = usuarioEnviador;
		this.evaluacionMuestra.procesarOpinion(this, opinionUsuarioEnviador);
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
	
	public boolean esUsuarioEnviador(Usuario usuario) {
		return this.usuarioEnviador.equals(usuario);
	}
	
	public boolean generadaEnUltimos(int ultimosDias) {
		LocalDateTime fechaComienzoValidez = LocalDateTime.now().minusDays(ultimosDias);
		return this.fechaCreacion.isAfter(fechaComienzoValidez);
	}
	
	//Evaluacion de muestra
	public void procesarOpinion(Opinion opinion) throws Exception {
		this.evaluacionMuestra.procesarOpinion(this, opinion);
	}
	
	public Resultado resultadoActual() {
		return this.evaluacionMuestra.getResultadoActual();
	}
	
	public List<Opinion> getOpiniones(){
		return this.evaluacionMuestra.getOpiniones();
	}
	
	public List<Opinion> getOpinionesDe(Usuario usuario){
		return this.getOpiniones().stream().filter(opinion -> opinion.esUsuarioOpinador(usuario)).toList();
	}
	
	public LocalDateTime buscarFechaUltimaVotacion() {
		return evaluacionMuestra.getFechaUltimaVotacion();
	}

	public boolean usuarioHizoRevisionExitosa(Usuario usuario, int cantDiasConsiderados) {
		return this.getOpinionesDe(usuario).stream().anyMatch(o -> o.esUsuarioOpinador(usuario) && o.generadaEnUltimos(cantDiasConsiderados));
	}
	
	//Zonas de cobertura de la muestra
	//falta test
	public List<ZonaDeCobertura> zonasDeCoberturaOcupadas(AplicacionWeb appWeb){
    	return appWeb.getZonasDeCobertura().stream().filter(z -> z.contiene(this.getUbicacion())).toList();
	}
	
	public EvaluacionMuestra getEvaluacion() {
		return this.evaluacionMuestra;
	}
	
	public EstadoEvaluacionMuestra getEstadoActual() {
		return getEvaluacion().getEstado();
	}
}