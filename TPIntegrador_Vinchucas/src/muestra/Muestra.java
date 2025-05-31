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
	private EvaluacionMuestra evaluacionMuestra;
	
	
	public Muestra(LocalDateTime fechaCreacion, Ubicacion ubicacionOrigen, Usuario usuarioEnviador, Opinion opinionUsuarioEnviador) {
		this.fechaCreacion = fechaCreacion;
		this.ubicacionOrigen = ubicacionOrigen;
		this.usuarioEnviador = usuarioEnviador;
		this.evaluacionMuestra = new EvaluacionMuestra();
		try {
			this.evaluacionMuestra.procesarOpinion(this, opinionUsuarioEnviador);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Usuario getUsuario() {
		return usuarioEnviador;
	}

	public LocalDateTime getFecha() {
		return fechaCreacion;
	}
	
	public void procesarOpinion(Opinion opinion) throws Exception {
		this.evaluacionMuestra.procesarOpinion(this, opinion);
	}
	
	public Ubicacion getUbicacion() {
		return this.ubicacionOrigen;
	}
	

	public List<ZonaDeCobertura> zonasDeCoberturaOcupadas(AplicacionWeb appWeb){
    	return appWeb.getZonasDeCobertura().stream().filter(z -> z.contiene(this.getUbicacion())).toList();
  }
}
