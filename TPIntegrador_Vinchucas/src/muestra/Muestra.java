package muestra;

import java.time.LocalDateTime;

import Vinchucas.Ubicacion;
import usuario.Opinion;
import usuario.Usuario;

public class Muestra {
	private LocalDateTime fechaCreacion;
	private Ubicacion ubicacionOrigen;
	private Usuario usuarioEnviador;
	private EvaluacionMuestra muestraEvaluada;
	
	public Muestra(LocalDateTime fechaCreacion, Ubicacion ubicacionOrigen, Usuario usuarioEnviador, Opinion opinionUsuarioEnviador) {
		this.fechaCreacion = fechaCreacion;
		this.ubicacionOrigen = ubicacionOrigen;
		this.usuarioEnviador = usuarioEnviador;
		this.muestraEvaluada = new EvaluacionMuestra();
		this.muestraEvaluada.evaluarOpinion(opinionUsuarioEnviador);
	}
}
