package Vinchucas;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import muestra.Muestra;
import usuario.Opinion;
import usuario.Resultado;
import usuario.Usuario;

public class AplicacionWeb {
	private List<Muestra> muestras;
	private List<Usuario> usuarios;
	private List<ZonaDeCobertura> zonasDeCobertura;
	
	public AplicacionWeb() {
		this.muestras = new ArrayList<>();
	}
	
	//Decido que la aplicacion web reciba la informacion cruda y genere los objetos claves para mantener la integridad deseada
	public void recibirMuestra(Ubicacion ubicacion, Usuario usuario, Resultado resultado) {
		Opinion opinion = new Opinion(usuario, resultado);
		Muestra muestra = new Muestra(LocalDateTime.now(), ubicacion, usuario, opinion);
		this.muestras.add(muestra);
		this.usuarios.add(usuario);
	}
	
	
	public void recibirOpinion(Muestra muestra, Usuario usuario, Resultado resultado) throws Exception {
		Muestra muestraSistema = buscarMuestra(muestra);
		Opinion opinionUsuario = new Opinion(usuario, resultado);
		muestraSistema.procesarOpinion(opinionUsuario);
	}
	
	private Muestra buscarMuestra(Muestra muestra) throws Exception {
		Optional<Muestra> muestraBuscada = this.muestras.stream().filter(m -> m.equals(muestra)).findFirst();
		if (muestraBuscada.isEmpty()) throw new Exception ("No existe la muestra en el sistema");
		else return muestraBuscada.get();
	}
	
	public void recategorizar() {
		//recategoriza a los usuarios GENERALES segun:
		//"son personas que durante los últimos 30 días desde la fecha actual han realizado más de 10 envíos y más de 20 revisiones"
	}
  
  	//perdemos eficiencia pero evitamos que el usuario tenga una lista de opiniones (lo que resulta mas comodo para recategorizar) complicando la logica a la hora de verificar la opinion y agregarsela si es aceptada.
//	public List<Opinion> getOpinionesDe(Usuario usuario) {
//	    
//	}
	
	public List<Muestra> muestrasAMenosDe(Muestra muestra, double km){
		CalculadorDistancia calculador = new CalculoDistancia();
    	return muestras.stream().filter(m -> calculador.calcular(muestra.getUbicacion(), m.getUbicacion()) < km).toList();
	}

	public List<ZonaDeCobertura> getZonasDeCobertura() {
		return zonasDeCobertura;
	}
}
