package Vinchucas;

import java.util.List;

import muestra.Muestra;


public class ZonaDeCobertura {
    private String nombre;
    
    private Ubicacion epicentro;
    private double radioKm;
    private CalculadorDistancia calculadorDist;
    
public String getNombre() {
        return nombre;
    }
    
public Ubicacion getEpicentro(){
    return this.epicentro;
}

public double getRadio(){
    return this.radioKm;
}


public ZonaDeCobertura(String nombre, Ubicacion epicentro, double radioKm, CalculadorDistancia calculador){
    this.nombre = nombre;
    this.epicentro = epicentro;
    this.radioKm = radioKm;
    this.calculadorDist = calculador;
}

public boolean contiene(Ubicacion ubicacion){
    return calculadorDist.calcular(epicentro, ubicacion) <= radioKm;
}

public boolean seSolapaConLaZona(ZonaDeCobertura zona){
    double distancia = calculadorDist.calcular(this.epicentro, zona.getEpicentro());
    return distancia <= (this.radioKm + zona.getRadio());
}

// ↓↓↓↓ QUIZAS PARA SACAR DE ACA ↓↓↓↓ POR PRINCIPIO DE RESPONSABILIDAD UNICA

public List<Muestra> muestrasDentroDe(){
    	return appWeb.muestras().filter(m -> this.contiene(m.ubicacion)).toList();
}

public List<ZonaDeCobertura> zonasQueLaSolapan(){
		return appWeb.zonasDeCobertura().filter(z -> this.seSolapaConLaZona(z)).toList();
}
}
