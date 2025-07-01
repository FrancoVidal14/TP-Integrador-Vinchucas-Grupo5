package appWeb;

import java.util.ArrayList;
import java.util.List;

import filtroBusqueda.Criterio;
import filtroBusqueda.FiltroDeBusqueda;
import muestra.ManejadorDeMuestras;
import muestra.Muestra;
import muestra.IObserverMuestra;
import usuario.Usuario;
import zonaCobertura.CalculadorDistancia;
import zonaCobertura.CalculoDistancia;
import zonaCobertura.IDatosZonaCobertura;
import zonaCobertura.ZonaDeCobertura;

public class AplicacionWeb implements IDatosUsuario, IDatosZonaCobertura {
	private List<Muestra> muestras;
	private List<ZonaDeCobertura> zonasDeCobertura;
	private List<Usuario> usuarios;
	private FiltroDeBusqueda filtro;
	private Recategorizador recategorizador = new Recategorizador(this); 

	// Constructor
	public AplicacionWeb(List<Muestra> muestrasRegistradas, List<ZonaDeCobertura> zonasDeCoberturaRegistradas, List<Usuario> usuariosRegistrados, FiltroDeBusqueda filtro) {
		//informacion del sistema que debe existir para poder mantener la cohesion del enunciado segun
		//lo dicho en app movil
		this.muestras = muestrasRegistradas;
		this.zonasDeCobertura = zonasDeCoberturaRegistradas;
		this.usuarios = usuariosRegistrados;
		this.filtro = filtro;
	}

	// Getters
	public List<Muestra> getMuestras() {
		return muestras;
	}
	
	@Override
	//necesario para la recategorizacion
	public List<Usuario> getUsuarios() {
		return this.usuarios;
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