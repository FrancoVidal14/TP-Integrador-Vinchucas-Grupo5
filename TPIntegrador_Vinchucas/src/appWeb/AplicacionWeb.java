package appWeb;

import java.util.ArrayList;
import java.util.List;

import filtroBusqueda.Criterio;
import filtroBusqueda.FiltroDeBusqueda;
import muestra.Muestra;
import muestra.RegistroDeValidaciones;
import usuario.Usuario;
import zonaCobertura.CalculadorDistancia;
import zonaCobertura.CalculoDistancia;
import zonaCobertura.IDatosZonaCobertura;
import zonaCobertura.ZonaDeCobertura;

public class AplicacionWeb implements IDatosUsuario, IDatosZonaCobertura, RegistroDeValidaciones {
	private List<Muestra> muestras;
	private List<ZonaDeCobertura> zonasDeCobertura;
	private List<Usuario> usuarios;
	private FiltroDeBusqueda filtro;
	private Recategorizador recategorizador = new Recategorizador(this); 

	// Constructor
	public AplicacionWeb(ArrayList<Muestra> muestrasRegistradas, ArrayList<ZonaDeCobertura> zonasDeCoberturaRegistradas, ArrayList<Usuario> usuariosRegistrados, FiltroDeBusqueda filtro) {
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
		enviarMuestraAZonas(muestra); //cambiar por observer
	}
	
	public void registrarUsuario(Usuario u) {
		this.usuarios.add(u);
	}
	
	public void añadirZonaDeCobertura(ZonaDeCobertura zona) {
		this.zonasDeCobertura.add(zona);
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
	public void enviarMuestraAZonas(Muestra muestra) {
		for (ZonaDeCobertura z : getZonasDeCobertura()) {
			if (z.contiene(muestra.getUbicacion())) {
				z.registrarMuestra(muestra);
			}
		}
	}
	
	@Override
	public void recibirMuestraValidada(Muestra m) {
		for (ZonaDeCobertura z : getZonasDeCobertura()) {
			if (z.contiene(m.getUbicacion())) {
				z.registrarValidacionDeMuestra(m);
			}
		}
	}
}