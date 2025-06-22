package VinchucasTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import appWeb.AplicacionWeb;
import zonaCobertura.CalculadorDistancia;
import zonaCobertura.CalculoDistancia;
import zonaCobertura.Ubicacion;
import zonaCobertura.ZonaDeCobertura;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

public class ZonaCoberturaTest {

    private Ubicacion estadioIndependiente;
    private Ubicacion estadioBoca;
    private Ubicacion estadioLiverpool;
    private Ubicacion estadioManchester;
    private Ubicacion estadioMilan;

    private CalculadorDistancia calculador;
    private AplicacionWeb appWebMock;
    
    @BeforeEach
    public void setup() {
        estadioIndependiente = new Ubicacion(-34.6703, -58.3710);    // Libertadores de América
        estadioBoca = new Ubicacion(-34.6356, -58.3643);            // La Bombonera
        estadioLiverpool = new Ubicacion(53.4308, -2.9609);        // Anfield
        estadioManchester = new Ubicacion(53.4635, -2.2923);      // Old Trafford
        estadioMilan = new Ubicacion(45.4785, 9.1222);           // San Siro

        calculador = new CalculoDistancia();
        appWebMock = mock(AplicacionWeb.class);
    }

    @Test 
    public void testZonasSeSolapanIndependienteYBocaEn5KMCadaUna(){
        // Configuración
        ZonaDeCobertura zonaIndep = new ZonaDeCobertura("Libertadores de America",estadioIndependiente,5.00, calculador);
        ZonaDeCobertura zonaBoca = new ZonaDeCobertura("La bombonera",estadioBoca, 5.00 , calculador);
        
        // Ejecución
        double distancia = calculador.calcular(estadioIndependiente, estadioBoca);

        // Comprobación de distancias
        System.out.println("Distancia calculada entre estadios: " + distancia + " km");
        System.out.println("Independiente: " + estadioIndependiente.getLatitud() + ", " + estadioIndependiente.getLongitud());
        System.out.println("Boca: " + estadioBoca.getLatitud() + ", " + estadioBoca.getLongitud());
        
        // Verificación
        assertTrue(zonaIndep.seSolapaConLaZona(zonaBoca));

    }

    @Test
    public void testBomboneraDentroDeZonaIndependienteDe8Km(){
        // Configuración
    	ZonaDeCobertura zonaIndep = new ZonaDeCobertura("Libertadores de America",estadioIndependiente,9, calculador);
        
        //Verificación
        assertTrue(zonaIndep.contiene(estadioBoca));
    }

    @Test
    public void testEstadioLiverpoolNoEstaDentroDeZonaManchester1KM(){
        // Configuración
        ZonaDeCobertura zonaManchester = new ZonaDeCobertura("Old Trafford", estadioManchester, 1, calculador);
        
        //Verificación
        assertFalse(zonaManchester.contiene(estadioLiverpool));
    }

    @Test
    public void testGetNombreDevuelveNombreCorrecto() {
        // Configuración
        String nombreEsperado = "San Siro";
        
        // Ejecución
        ZonaDeCobertura zona = new ZonaDeCobertura(nombreEsperado, estadioMilan, 5.0, calculador);
        
        // Verificación
        assertEquals(nombreEsperado, zona.getNombre());
    }
    
    @Test
    public void testZonasQueLaSolapanCuandoHaySolapamiento() {
        // Configuración
        ZonaDeCobertura zonaIndep = new ZonaDeCobertura("Libertadores de America", estadioIndependiente, 5.0, calculador);
        ZonaDeCobertura zonaBoca = new ZonaDeCobertura("La Bombonera", estadioBoca, 5.0, calculador);
        
        // Configurar el mock para devolver la lista con la zona de Boca
        when(appWebMock.getZonasDeCobertura()).thenReturn(List.of(zonaBoca));
        
        // Ejecución
        List<ZonaDeCobertura> zonasSolapadas = zonaIndep.zonasQueLaSolapan(appWebMock);
        
        // Verificación
        assertEquals(1, zonasSolapadas.size());
        assertEquals("La Bombonera", zonasSolapadas.get(0).getNombre());
    }
    
    @Test
    public void testZonasQueLaSolapanCuandoNoHaySolapamiento() {
        // Configuración
        ZonaDeCobertura zonaIndep = new ZonaDeCobertura("Libertadores de America", estadioIndependiente, 5.0, calculador);
        ZonaDeCobertura zonaMilan = new ZonaDeCobertura("San Siro", estadioMilan, 5.0, calculador);
        
        // Configurar el mock para devolver la lista con la zona de Milán
        when(appWebMock.getZonasDeCobertura()).thenReturn(List.of(zonaMilan));
        
        // Ejecución
        List<ZonaDeCobertura> zonasSolapadas = zonaIndep.zonasQueLaSolapan(appWebMock);
        
        // Verificación
        assertTrue(zonasSolapadas.isEmpty());
    }
    
    @Test
    public void testZonasQueLaSolapanConMultiplesZonas() {
        // Configuración
        ZonaDeCobertura zonaIndep = new ZonaDeCobertura("Libertadores de America", estadioIndependiente, 10.0, calculador);
        ZonaDeCobertura zonaBoca = new ZonaDeCobertura("La Bombonera", estadioBoca, 5.0, calculador);
        ZonaDeCobertura zonaManchester = new ZonaDeCobertura("Old Trafford", estadioManchester, 5.0, calculador);
        
        // Configurar el mock para devolver múltiples zonas
        when(appWebMock.getZonasDeCobertura()).thenReturn(List.of(zonaBoca, zonaManchester));
        
        // Ejecución
        List<ZonaDeCobertura> zonasSolapadas = zonaIndep.zonasQueLaSolapan(appWebMock);
        
        // Verificación
        assertEquals(1, zonasSolapadas.size());
        assertEquals("La Bombonera", zonasSolapadas.get(0).getNombre());
    }
    
}