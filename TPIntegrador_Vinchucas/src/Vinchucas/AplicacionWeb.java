package Vinchucas;

import java.util.ArrayList;
import java.util.List;

import FiltrosDeBusqueda.Criterio;
import FiltrosDeBusqueda.FiltroDeBusqueda;
import muestra.Muestra;
import usuario.Opinion;
import usuario.Usuario;

public class AplicacionWeb implements IDatosUsuario, RegistroDeValidaciones {
	private List<Muestra> muestras = new ArrayList<>();
	private List<Usuario> usuarios = new ArrayList<>();
	private List<ZonaDeCobertura> zonasDeCobertura;
	private FiltroDeBusqueda filtro;
	private Recategorizador recategorizador = new Recategorizador(this); 

	// Constructor
	public AplicacionWeb(FiltroDeBusqueda filtro) {
		this.zonasDeCobertura = new ArrayList<>();
		this.filtro = filtro;
	}

	// Getters
	public List<Muestra> getMuestras() {
		return muestras;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

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

	@Override
	public List<Opinion> getOpinionesDe(Usuario usuario) {
		return this.muestras.stream()
			.flatMap(m -> m.getOpinionesDe(usuario).stream())
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
		muestra.setReceptor(this);
		muestra.procesarOpinion(opinionUsuario);
		enviarMuestraAZonas(muestra);
	}

	@Override
	public void recibirMuestraValidada(Muestra m) {
		for (ZonaDeCobertura z : getZonasDeCobertura()) {
			if (z.contiene(m.getUbicacion())) {
				z.registrarValidacionDeMuestra(m);
			}
		}
	}

	// Funcionalidades
	public List<Muestra> muestrasAMenosDe(Muestra muestra, double km) {
		CalculadorDistancia calculador = new CalculoDistancia();
    	return muestras.stream()
			.filter(m -> calculador.calcular(muestra.getUbicacion(), m.getUbicacion()) < km)
			.toList();
	}

	public List<Muestra> filtrarMuestras(Criterio criterio) {
		 return filtro.filtrarMuestras(muestras, criterio);
	}

	public void recategorizar(int cantEnviosEsperados, int cantRevisionesEsperadas, int cantDiasConsiderados) {
		this.recategorizador.recategorizarUsuarios(cantEnviosEsperados, cantRevisionesEsperadas, cantDiasConsiderados);
	}

	public void enviarMuestraAZonas(Muestra muestra) {
		for (ZonaDeCobertura z : getZonasDeCobertura()) {
			if (z.contiene(muestra.getUbicacion())) {
				z.registrarMuestra(muestra);
			}
		}
	}
}