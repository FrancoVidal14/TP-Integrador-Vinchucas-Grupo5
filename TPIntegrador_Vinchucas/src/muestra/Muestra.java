package muestra;

import java.time.LocalDateTime;
import java.util.List;

import Vinchucas.AplicacionWeb;
import Vinchucas.ZonaDeCobertura;
import Vinchucas.Ubicacion;
import usuario.Opinion;
import usuario.Usuario;

public class Muestra {
	private LocalDateTime fechaCreacion;
	private Ubicacion ubicacionOrigen;
	private Usuario usuarioEnviador;
	private EvaluacionMuestra evaluacionMuestra = new EvaluacionMuestra();;
	
	
	public Muestra(LocalDateTime fechaCreacion, Ubicacion ubicacionOrigen, Usuario usuarioEnviador, Opinion opinionUsuarioEnviador) throws Exception {
		this.fechaCreacion = fechaCreacion;
		this.ubicacionOrigen = ubicacionOrigen;
		this.usuarioEnviador = usuarioEnviador;
		this.evaluacionMuestra.procesarOpinion(this, opinionUsuarioEnviador);
	}

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
	
	public void procesarOpinion(Opinion opinion) throws Exception {
		this.evaluacionMuestra.procesarOpinion(this, opinion);
	}
	
	public List<ZonaDeCobertura> zonasDeCoberturaOcupadas(AplicacionWeb appWeb){
    	return appWeb.getZonasDeCobertura().stream().filter(z -> z.contiene(this.getUbicacion())).toList();
	}
	
	public LocalDateTime buscarFechaUltimaVotacion() {
		return evaluacionMuestra.getFechaUltimaVotacion();
	}
}