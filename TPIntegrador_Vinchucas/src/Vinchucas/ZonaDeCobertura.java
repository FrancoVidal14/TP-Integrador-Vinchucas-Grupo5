package Vinchucas;

import java.util.List;

import muestra.Muestra;


public class ZonaDeCobertura {
    private String nombre;
    private Ubicacion epicentro;
    private double radioKm;
    private CalculadorDistancia calculadorDist;
    private ManejadorDeNotificaciones manejador;
    
    public String getNombre() {
        return nombre;
    }
    
    public Ubicacion getEpicentro(){
    	return this.epicentro;
    }

    public double getRadio(){
    	return this.radioKm;
    }

    public ZonaDeCobertura(String nombre, Ubicacion epicentro, double radioKm, CalculadorDistancia calculador, ManejadorDeNotificaciones manejador) {
    	this.nombre = nombre;
    	this.epicentro = epicentro;
    	this.radioKm = radioKm;
    	this.calculadorDist = calculador;
    	this.manejador = manejador;
    }

    public boolean contiene(Ubicacion ubicacion){
    	return calculadorDist.calcular(epicentro, ubicacion) <= radioKm;
    }

    public boolean seSolapaConLaZona(ZonaDeCobertura zona){
    	double distancia = calculadorDist.calcular(this.epicentro, zona.getEpicentro());
    	return distancia <= (this.radioKm + zona.getRadio());
    }

    public List<ZonaDeCobertura> zonasQueLaSolapan(IDatosZonaCobertura datosZona){
		return datosZona.getZonasDeCobertura().stream().filter(z -> this.seSolapaConLaZona(z)).toList();
    }
    
    public void registrarMuestra(Muestra m) {
    	manejador.recibirInformacionDeMuestraRegistrada(m, this);
    }
    
    public void registrarValidacionDeMuestra(Muestra m) {
    	manejador.recibirInformacionDeMuestraValidada(m, this);
    }
}