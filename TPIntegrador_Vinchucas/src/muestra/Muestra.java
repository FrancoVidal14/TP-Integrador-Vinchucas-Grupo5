package muestra;

import java.time.LocalDateTime;
import java.util.List;

import Vinchucas.AplicacionWeb;
import Vinchucas.CalculadorDistancia;
import Vinchucas.CalculoDistancia;
import Vinchucas.ZonaDeCobertura;
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
	
	public Ubicacion getUbicacionOrigen(){
		return this.ubicacionOrigen;
	}
	
	public List<ZonaDeCobertura> zonasDeCoberturaOcupadas(AplicacionWeb appWeb){
    	return appWeb.getZonasDeCobertura().stream().filter(z -> z.contiene(this.getUbicacionOrigen())).toList();
}
	
}
