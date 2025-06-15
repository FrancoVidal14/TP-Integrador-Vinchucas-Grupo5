package Vinchucas;

import java.util.List;
import java.util.ArrayList;

import muestra.Muestra;


public class ZonaDeCobertura {
    private String nombre;
    private Ubicacion epicentro;
    private double radioKm;
    private CalculadorDistancia calculadorDist;
    private ManejadorDeEventos manejador;
    private List<Muestra> muestras;
    
    public String getNombre() {
        return nombre;
    }
    
    public Ubicacion getEpicentro(){
    	return this.epicentro;
    }

    public double getRadio(){
    	return this.radioKm;
    }

    public ZonaDeCobertura(String nombre, Ubicacion epicentro, double radioKm, CalculadorDistancia calculador, ManejadorDeEventos manejador) {
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

// ↓↓↓↓ QUIZAS PARA SACAR DE ACA ↓↓↓↓ POR PRINCIPIO DE RESPONSABILIDAD UNICA

    public List<ZonaDeCobertura> zonasQueLaSolapan(AplicacionWeb appWeb){
		return appWeb.getZonasDeCobertura().stream().filter(z -> this.seSolapaConLaZona(z)).toList();
    }
    
    public void registrarMuestra(Muestra m) {
    	manejador.recibirInformacionDeMuestraRegistrada(m);
    }
    
    public void registrarValidacionDeMuestra(Muestra m) {
    	manejador.recibirInformacionDeMuestraValidada(m);
    }
}