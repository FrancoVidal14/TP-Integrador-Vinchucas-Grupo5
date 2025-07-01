package zonaCobertura;

import java.util.List;

import muestra.Muestra;
import muestra.IObserverMuestra;


public class ZonaDeCobertura implements IObserverMuestra{
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

    public ZonaDeCobertura(String nombre, Ubicacion epicentro, double radioKm, CalculadorDistancia calculador) {
    	this.nombre = nombre;
    	this.epicentro = epicentro;
    	this.radioKm = radioKm;
    	this.calculadorDist = calculador;
    	this.manejador = new ManejadorDeNotificaciones();
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
    
    public void registrarMuestraSiCorresponde(Muestra m) {
    	if(this.contiene(m.getUbicacion())) {
    		manejador.recibirInformacionDeMuestraRegistrada(m, this);
    		m.agregarObservador(this);
    	}
    }
    
	@Override
	public void recibirMuestraValidada(Muestra m) {
		manejador.recibirInformacionDeMuestraValidada(m, this);
	}
}