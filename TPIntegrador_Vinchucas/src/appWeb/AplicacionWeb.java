package appWeb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import filtroBusqueda.Criterio;
import filtroBusqueda.FiltroDeBusqueda;
import muestra.Muestra;
import usuario.Usuario;
import zonaCobertura.CalculadorDistancia;
import zonaCobertura.CalculoDistancia;
import zonaCobertura.IDatosZonaCobertura;
import zonaCobertura.ZonaDeCobertura;

public class AplicacionWeb implements IDatosUsuario, IDatosZonaCobertura {
	private List<Muestra> muestras = new ArrayList<>();
	private List<ZonaDeCobertura> zonasDeCobertura = new ArrayList<>();
	private Set<Usuario> usuarios = new HashSet<>();
	private FiltroDeBusqueda filtro;
	private Recategorizador recategorizador = new Recategorizador(this); 

	// Constructor
	public AplicacionWeb( FiltroDeBusqueda filtro) {
		this.filtro = filtro;
	}

	// Getters
	public List<Muestra> getMuestras() {
		return muestras;
	}
	
	@Override
	//necesario para la recategorizacion
	public Set<Usuario> getUsuarios() {
		this.actualizarUsuarios();
		return this.usuarios;
	}
	
	//funcion que se encarga de traer los usuarios opinadores y agregarlos 
	//al set de usuarios, garantizando que no haya repetidos
	//ya que no tenemos forma de garantizarlos en mediante app web
	//y es necesario para recategorizarlos
	private void actualizarUsuarios() {
		this.usuarios.addAll(this.getUsuariosOpinadores());
	}
	
	private Set<Usuario> getUsuariosOpinadores(){
		Set<Usuario> usuariosOpinadores = new HashSet<>();
		for (Muestra muestra : this.muestras) {
			usuariosOpinadores.addAll(muestra.getOpiniones().stream().map(o -> o.getUsuario()).toList());
		}
		return usuariosOpinadores;
	}
	
	@Override
	public List<ZonaDeCobertura> getZonasDeCobertura() {
		return zonasDeCobertura;
	}

	public FiltroDeBusqueda getFiltro() {
		return this.filtro;
	}

	@Override
	public List<Muestra> getMuestrasEnviadasPor(Usuario usuario) {
		return this.muestras.stream()
			.filter(m -> m.esUsuarioEnviador(usuario))
			.toList();
	}

	// Registro y recepción
	public void recibirMuestra(Muestra muestra) {
		this.muestras.add(muestra);
		this.registrarUsuario(muestra.getUsuario());
		enviarRegistroDeMuestraAZonas(muestra);
	}
	
	public void registrarUsuario(Usuario u) {
		this.usuarios.add(u);
	}
	
	public void añadirZonaDeCobertura(ZonaDeCobertura zona) {
		this.zonasDeCobertura.add(zona);
		registrarMuestrasDeLaAplicacionALaZonaEntrante(zona);
	}
	
	public void registrarMuestrasDeLaAplicacionALaZonaEntrante(ZonaDeCobertura z) {
		for(Muestra m : this.muestras) {
			z.registrarMuestraSiCorresponde(m);
		}
	}

	// Funcionalidades
	public List<Muestra> muestrasAMenosDe(Muestra muestra, double km) {
		CalculadorDistancia calculador = new CalculoDistancia();
		return muestras.stream()
			.filter(m -> !m.esMismaMuestra(muestra)) // evitar compararse a sí misma
			.filter(m -> calculador.calcular(muestra.getUbicacion(), m.getUbicacion()) < km)
			.toList();
	}


	public List<Muestra> filtrarMuestras(Criterio criterio) {
		 return filtro.filtrarMuestras(muestras, criterio);
	}

	public void recategorizar(int cantEnviosEsperados, int cantRevisionesEsperadas, int cantDiasConsiderados) {
		this.recategorizador.recategorizarUsuarios(cantEnviosEsperados, cantRevisionesEsperadas, cantDiasConsiderados);
	}
	
	//notificar a zonas sobre muestras para observer
	public void enviarRegistroDeMuestraAZonas(Muestra muestra) {
		for(ZonaDeCobertura z : this.zonasDeCobertura) {
			z.registrarMuestraSiCorresponde(muestra);
		}
	}
}