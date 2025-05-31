package VinchucasTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Vinchucas.AplicacionWeb;
import Vinchucas.CalculadorDistancia;
import Vinchucas.CalculoDistancia;
import Vinchucas.Ubicacion;
import muestra.Muestra;
import usuario.Usuario;
import usuario.Opinion;
import usuario.Resultado;

class AplicacionWebTest {

    // Variables de instancia accesibles desde todos los tests
    private AplicacionWeb appVinchucas;
    private Muestra muestraEnLaUnq;
    private Usuario dami, fran, joaco;
    private Opinion opinionMock;
    private Ubicacion unq, estadioIndependiente, estadioBoca;
    private CalculadorDistancia calculador;


    @BeforeEach
    public void setUp() {

        dami = mock(Usuario.class);
        fran = mock(Usuario.class);
        joaco = mock(Usuario.class);
        opinionMock = mock(Opinion.class);

        calculador = new CalculoDistancia();
        
        unq = new Ubicacion(-34.7063, -58.2778);
        muestraEnLaUnq = new Muestra(LocalDateTime.of(2025, 6, 19, 12, 23), unq, dami, opinionMock);

        estadioIndependiente = new Ubicacion(-34.6703, -58.3710);
        estadioBoca = new Ubicacion(-34.6356, -58.3643);

        appVinchucas = new AplicacionWeb();
        appVinchucas.recibirMuestra(estadioBoca, dami, Resultado.CHINCHE_FOLIADA);
        appVinchucas.recibirMuestra(estadioBoca, joaco, Resultado.IMAGEN_POCO_CLARA);
        appVinchucas.recibirMuestra(estadioIndependiente, fran, Resultado.PHTIA_CHINCE);
    }

    @Test
    void testCorrectoDeMuestrasCercaDeLaUNQ() {
    	double distancia = calculador.calcular(estadioIndependiente, unq); 
        System.out.println("Distancia entre el LDA y la UNQ es de: " + distancia + " km");
        
        assertEquals(1, appVinchucas.muestrasAMenosDe(muestraEnLaUnq, 12).size());
    }
    
}