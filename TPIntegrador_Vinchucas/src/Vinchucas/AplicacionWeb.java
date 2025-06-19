package Vinchucas;

import java.util.ArrayList;
import java.util.List;

import FiltrosDeBusqueda.Criterio;
import FiltrosDeBusqueda.FiltroDeBusqueda;
import muestra.Muestra;
import usuario.Opinion;
import usuario.Usuario;

public class AplicacionWeb{
	private List<Muestra> muestras;
	private List<Usuario> usuarios;
	private List<ZonaDeCobertura> zonasDeCobertura;
	private FiltroDeBusqueda filtro;
	
	public AplicacionWeb(FiltroDeBusqueda filtro) {
		this.muestras = new ArrayList<>();
		this.usuarios = new ArrayList<>();
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
	
	//proximamente una clase regategorizador
	public void recategorizar(int cantEnviosEsperados, int cantRevisionesEsperadas, int cantDiasConsiderados) {
		for(Usuario usuario : getUsuarios()) {
			usuario.recategorizarSiCorresponde(this, cantEnviosEsperados, cantRevisionesEsperadas, cantDiasConsiderados);
		}
	}
	
	public boolean usuarioCumpleReglasPromocion(Usuario usuario, int cantEnviosEsperados, int cantRevisionesEsperadas, int cantDiasConsiderados) {
		return this.usuarioEnvioMasDeMuestrasEn(usuario, cantEnviosEsperados, cantDiasConsiderados) && this.usuarioConcretoMasDeRevisionesEn(usuario, cantRevisionesEsperadas, cantDiasConsiderados);
	}
	
	private boolean usuarioConcretoMasDeRevisionesEn(Usuario usuario, int cantRevisionesEsperadas, int cantDiasConsiderados) {
		return this.revisionesCorrectasDeEn(usuario, cantDiasConsiderados).size() > cantRevisionesEsperadas;
	}

	private List<Muestra> revisionesCorrectasDeEn(Usuario usuario, int cantDiasConsiderados) {
		return this.muestras.stream().filter(m -> m.usuarioHizoRevisionExitosa(usuario, cantDiasConsiderados)).toList();
	}

	private boolean usuarioEnvioMasDeMuestrasEn(Usuario usuario, int cantEnviosEsperados ,int cantDiasConsiderados) {
		return this.muestrasEnviadasUsuarioEnUltimosDias(usuario, cantDiasConsiderados).size() > cantEnviosEsperados;
	}
	
	private List<Muestra> muestrasEnviadasUsuarioEnUltimosDias(Usuario usuario, int ultimosDias){
		return this.muestras.stream().filter(m ->  m.esUsuarioEnviador(usuario) && m.esEnviadaEnUltimos(ultimosDias)).toList();
	}
  
	//perdemos eficiencia pero evitamos que el usuario tenga una lista de opiniones (lo que resulta mas comodo para recategorizar) complicando la logica a la hora de verificar la opinion y agregarsela si es aceptada.
	public List<Opinion> getOpinionesDe(Usuario usuario) {
		return this.muestras.stream()
							.flatMap(m -> m.getOpinionesDe(usuario).stream())
							.toList();
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
	
	public List<Usuario> getUsuarios(){
		return this.usuarios;
	}
	
	public void enviarMuestraAZonas(Muestra muestra) {
		for(ZonaDeCobertura z : getZonasDeCobertura()) {
			if(z.contiene(muestra.getUbicacion())) {
				z.registrarMuestra(muestra);
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