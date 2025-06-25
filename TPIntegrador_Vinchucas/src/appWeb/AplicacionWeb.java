package appWeb;

import java.util.ArrayList;
import java.util.List;

import filtroBusqueda.Criterio;
import filtroBusqueda.FiltroDeBusqueda;
import muestra.Muestra;
import muestra.RegistroDeValidaciones;
import usuario.Opinion;
import usuario.Usuario;
import zonaCobertura.CalculadorDistancia;
import zonaCobertura.CalculoDistancia;
import zonaCobertura.IDatosZonaCobertura;
import zonaCobertura.ZonaDeCobertura;

public class AplicacionWeb implements IDatosUsuario, IDatosZonaCobertura, RegistroDeValidaciones {
	private List<Muestra> muestras = new ArrayList<>();
	private List<Usuario> usuarios = new ArrayList<>();
	private List<ZonaDeCobertura> zonasDeCobertura= new ArrayList<>();
	private FiltroDeBusqueda filtro;
	private Recategorizador recategorizador = new Recategorizador(this); 

	// Constructor
	public AplicacionWeb(FiltroDeBusqueda filtro) {
		this.filtro = filtro;
	}

	// Getters
	public List<Muestra> getMuestras() {
		return muestras;
	}

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
	public void registrarUsuario(Usuario u) {
		this.usuarios.add(u);
	}

	public void añadirZonaDeCobertura(ZonaDeCobertura zona) {
		this.zonasDeCobertura.add(zona);
	}

	public void recibirMuestra(Muestra muestra, Usuario usuario, Opinion opinionUsuario) throws Exception {
		this.muestras.add(muestra);
		this.usuarios.add(usuario);
		muestra.procesarOpinion(opinionUsuario);
		enviarMuestraAZonas(muestra);
	}

	// Funcionalidades
	public List<Muestra> muestrasAMenosDe(Muestra muestra, double km) {
		CalculadorDistancia calculador = new CalculoDistancia();
		return muestras.stream()
			.filter(m -> !m.equals(muestra)) // evitar compararse a sí misma
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