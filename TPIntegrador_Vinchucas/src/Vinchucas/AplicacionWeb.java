package Vinchucas;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import muestra.Muestra;
import usuario.Opinion;
import usuario.Resultado;
import usuario.Usuario;

public class AplicacionWeb {
	private List<Muestra> muestras;
	private List<Usuario> usuarios;
	private List<ZonaDeCobertura> zonasDeCobertura;
	private Map<Integer, List<Opinion>> opinionesUsuarios;
	
	public AplicacionWeb() {
		this.muestras = new ArrayList<>();
		this.usuarios = new ArrayList<>();
		this.zonasDeCobertura = new ArrayList<>();
		this.opinionesUsuarios = new HashMap<>();
	}	
	
	//Decido que la aplicacion web reciba la informacion cruda y genere los objetos claves para mantener la integridad deseada
	public void recibirMuestra(Ubicacion ubicacion, Usuario usuario, Resultado resultado) {
		Opinion opinion = new Opinion(usuario, resultado);
		Muestra muestra = new Muestra(LocalDateTime.now(), ubicacion, usuario, opinion);
		this.muestras.add(muestra);
		this.usuarios.add(usuario);
	}
	
	
	public void recibirOpinion(Muestra muestra, Usuario usuario, Resultado resultado) throws Exception {
	    try {
	        Muestra muestraSistema = buscarMuestra(muestra);
	        Opinion opinionUsuario = new Opinion(usuario, resultado);
	        
	        procesarOpinion(muestraSistema, opinionUsuario);
	        registrarOpinion(usuario, opinionUsuario);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private void registrarOpinion(Usuario usuario, Opinion opinionUsuario) {
		List<Opinion> opinionesUsuario = this.opinionesUsuarios.get(usuario.getDni());

		if (opinionesUsuario == null) {
		    opinionesUsuario = new ArrayList<>();
		    this.opinionesUsuarios.put(usuario.getDni(), opinionesUsuario);
		}

		opinionesUsuario.add(opinionUsuario);
	}

	private Muestra buscarMuestra(Muestra muestra) throws Exception {
		Optional<Muestra> muestraBuscada = this.muestras.stream().filter(m -> m.equals(muestra)).findFirst();
		if (muestraBuscada.isEmpty()) throw new Exception ("No existe la muestra en el sistema");
		else return muestraBuscada.get();
	}
	
	private void procesarOpinion(Muestra muestraSistema, Opinion opinion) throws Exception {
		muestraSistema.procesarOpinion(opinion);
	}
	
	public void recategorizar() {
		//recategoriza a los usuarios GENERALES segun:
		//"son personas que durante los últimos 30 días desde la fecha actual han realizado más de 10 envíos y más de 20 revisiones"
	}
  
	//perdemos eficiencia pero evitamos que el usuario tenga una lista de opiniones (lo que resulta mas comodo para recategorizar) complicando la logica a la hora de verificar la opinion y agregarsela si es aceptada.
	public List<Opinion> getOpinionesDe(Usuario usuario) {
		//validar usuario en el sistema??
		List<Opinion> opiniones = this.opinionesUsuarios.get(usuario.getDni());
	    if (opiniones == null) {
	    	//throw new Exception ("El usuario todavia no dio su opinion");
	    	return opiniones = new ArrayList<>();
	    }
	    return opiniones;
	}
	
	public List<Muestra> muestrasAMenosDe(Muestra muestra, double km){
		CalculadorDistancia calculador = new CalculoDistancia();
    	return muestras.stream().filter(m -> calculador.calcular(muestra.getUbicacion(), m.getUbicacion()) < km).toList();
	}

	public List<ZonaDeCobertura> getZonasDeCobertura() {
		return zonasDeCobertura;
	}
	
	public List<Muestra> getMuestras() {
		return muestras;
	}
	
	public void enviarMuestrasAZona() {
		for(ZonaDeCobertura z : getZonasDeCobertura()) {
			for(Muestra m : getMuestras()) {
				// deberia enviarle las muestras de cada zona a la zona correspondiente para que las organizaciones observen.
				// El problema esta en cuando una muestra ocupa dos zonas, como envio a las dos?
				// if m.getzona == z -> z.agregarMuestra(m)
			}
		}
	}
	
	public void enviarValidacionesDeMuestras() {
		// deberia enviarle la informacion de que se valido una muestra en la zona, al manejador de la zona misma para enviarle esa info a la organizaciones
	}
}
