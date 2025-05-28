package VinchucasTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Vinchucas.CalculadorDistancia;
import Vinchucas.CalculoDistancia;
import Vinchucas.Ubicacion;
import Vinchucas.ZonaDeCobertura;

import static org.junit.jupiter.api.Assertions.*;

public class ZonaCoberturaTest {

    private Ubicacion estadioIndependiente;
    private Ubicacion estadioBoca;
    private Ubicacion estadioLiverpool;
    private Ubicacion estadioManchester;
    private Ubicacion estadioMilan;

    private CalculadorDistancia calculador;

    @BeforeEach
    public void setup() {
        estadioIndependiente = new Ubicacion(-34.6708, -58.3622); // Libertadores de América
        estadioBoca = new Ubicacion(-34.6304, -58.4375);          // La Bombonera
        estadioLiverpool = new Ubicacion(53.4308, -2.9609);     // Anfield
        estadioManchester = new Ubicacion(53.4635, -2.2923);    // Old Trafford
        estadioMilan = new Ubicacion(45.4785, 9.1222);        // San Siro

        calculador = new CalculoDistancia();
    }

    @Test 
    public void testZonasSeSolapanIndependienteYBocaEn5KMCadaUna(){
        ZonaDeCobertura zonaIndep = new ZonaDeCobertura("Libertadores de America",estadioIndependiente,5.00, calculador);
        ZonaDeCobertura zonaBoca = new ZonaDeCobertura("La bombonera",estadioBoca, 5.00 , calculador);
        double distancia = calculador.calcular(estadioIndependiente, estadioBoca);

        System.out.println("Distancia calculada entre estadios: " + distancia + " km");
        
        System.out.println("Independiente: " + estadioIndependiente.getLatitud() + ", " + estadioIndependiente.getLongitud());
        System.out.println("Boca: " + estadioBoca.getLatitud() + ", " + estadioBoca.getLongitud());
        
        assertTrue(zonaIndep.seSolapaConLaZona(zonaBoca));

    }

    @Test
    public void testBomboneraDentroDeZonaIndependienteDe8Km(){
        ZonaDeCobertura zonaIndep = new ZonaDeCobertura("Libertadores de America",estadioIndependiente,9, calculador);
        assertTrue(zonaIndep.contiene(estadioBoca));
    }

    @Test
    public void testEstadioLiverpoolNoEstaDentroDeZonaManchester1KM(){
        ZonaDeCobertura zonaManchester = new ZonaDeCobertura("Old Trafford", estadioManchester, 1, calculador);
        assertFalse(zonaManchester.contiene(estadioLiverpool));
    }

}