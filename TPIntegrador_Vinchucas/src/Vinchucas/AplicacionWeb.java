package Vinchucas;

import java.util.ArrayList;
import java.util.List;

import usuario.Usuario;
import muestra.Muestra;

public class AplicacionWeb {

	private List<ZonaDeCobertura> zonasDeCobertura = new ArrayList<>();
	private List<Usuario> usuarios = new ArrayList<>();
	private List<Muestra> muestras = new ArrayList<>();
	
	public void añadirZonaDeCobertura(ZonaDeCobertura zona) {
		zonasDeCobertura.add(zona);
	}
	public void registrarUsuario(Usuario usuario) {
		usuarios.add(usuario);
	}
	public void recibirMuestra(Muestra muestra) {
		muestras.add(muestra);
	}
	
	public List<ZonaDeCobertura> getZonasDeCobertura() {
		return zonasDeCobertura;
	}
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public List<Muestra> getMuestras() {
		return muestras;
	}
	
	public void categorizarUsuarios() {
	;	
	}
	
	public List<Muestra> filtrarMuestras(Criterio criterio){
		;
	}
	
	public List<Muestra> muestrasAMenosDe(Muestra muestra, double km){
		CalculadorDistancia calculador = new CalculoDistancia();
    	return muestras.stream().filter(m -> calculador.calcular(muestra.getUbicacionOrigen(), m.getUbicacionOrigen()) < km).toList();
	}
}
