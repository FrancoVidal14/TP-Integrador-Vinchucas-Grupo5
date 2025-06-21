package Vinchucas;

import java.util.ArrayList;
import java.util.List;

import FiltrosDeBusqueda.Criterio;
import FiltrosDeBusqueda.FiltroDeBusqueda;
import muestra.Muestra;
import usuario.Opinion;
import usuario.Usuario;

public class AplicacionWeb implements IDatosUsuario, RegistroDeValidaciones{
	private List<Muestra> muestras = new ArrayList<>();
	private List<Usuario> usuarios = new ArrayList<>();
	private List<ZonaDeCobertura> zonasDeCobertura;
	private FiltroDeBusqueda filtro;
	private Recategorizador recategorizador = new Recategorizador(this);
	
	public AplicacionWeb(FiltroDeBusqueda filtro) {
		this.zonasDeCobertura = new ArrayList<>();
		this.filtro = filtro;
	}	
	
	//Cambiamos idea en base a lo charlado con el profe Diego Cano y entendemos que la integridad ya estaria garantizada.
	public void recibirMuestra(Muestra muestra, Usuario usuario, Opinion opinionUsuario) throws Exception {
		this.muestras.add(muestra);
		this.usuarios.add(usuario);
		this.recibirOpinion(muestra, opinionUsuario); 
		enviarMuestraAZonas(muestra);
	}
	
	public void recibirOpinion(Muestra muestra, Opinion opinion) throws Exception {
		muestra.procesarOpinion(opinion);
	}
	
	public void recategorizar(int cantEnviosEsperados, int cantRevisionesEsperadas, int cantDiasConsiderados) {
		this.recategorizador.recategorizarUsuarios(cantEnviosEsperados, cantRevisionesEsperadas, cantDiasConsiderados);
	}
	
	//IDatosUsuario
	@Override
	public List<Usuario> getUsuarios(){
		return this.usuarios;
	}
  
	//perdemos eficiencia pero evitamos que el usuario tenga una lista de opiniones (lo que resulta mas comodo para recategorizar) 
	//complicando la logica a la hora de verificar la opinion y agregarsela si es aceptada desde muestra.
	@Override
	public List<Opinion> getOpinionesDe(Usuario usuario) {
		return this.muestras.stream()
							.flatMap(m -> m.getOpinionesDe(usuario).stream())
							.toList();
	}
	
	@Override
	public List<Muestra> getMuestrasEnviadasPor(Usuario usuario) {
		return this.muestras.stream().filter(m ->  m.esUsuarioEnviador(usuario)).toList();
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
	
	public void enviarMuestraAZonas(Muestra muestra) {
		for(ZonaDeCobertura z : getZonasDeCobertura()) {
			if(z.contiene(muestra.getUbicacion())) {
				z.registrarMuestra(muestra);
			}
		}
	}
	
	@Override
	public void recibirMuestraValidada(Muestra m) {
		for(ZonaDeCobertura z : getZonasDeCobertura()) {
			if(z.contiene(m.getUbicacion())) {
				z.registrarValidacionDeMuestra(m);
			}
		}
	}
	
	public FiltroDeBusqueda getFiltro() {
		return this.filtro;
	}
	
	public List<Muestra> filtrarMuestras(Criterio criterio) {
		 return filtro.filtrarMuestras(muestras, criterio);
	}
}