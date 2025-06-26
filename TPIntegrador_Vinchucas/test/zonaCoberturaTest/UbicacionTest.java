package zonaCoberturaTest;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import zonaCobertura.Ubicacion;

public class UbicacionTest {

    private Ubicacion estadioIndependiente;
    private Ubicacion estadioBoca;
    private Ubicacion estadioRiver;
    private Ubicacion estadioLiverpool;
    private Ubicacion estadioManchester;

    @BeforeEach
    public void setup() {
        estadioIndependiente = new Ubicacion(-34.6703, -58.3710); // Libertadores de América
        estadioBoca = new Ubicacion(-34.6356, -58.3643);        // La Bombonera
        estadioRiver = new Ubicacion(-34.5456, -58.4497);      // Monumental
        estadioLiverpool = new Ubicacion(53.4308, -2.9609);    // Anfield
        estadioManchester = new Ubicacion(53.4635, -2.2923);   // Old Trafford
    }

    @Test
    public void testUbicacionesAMenosDeDevuelveListaVacia() {
        // Configuración
        List<Ubicacion> ubicacionesLejanas = List.of(estadioLiverpool, estadioManchester);
        
        // Ejecución
        List<Ubicacion> resultado = estadioIndependiente.ubicacionesAMenosDe(ubicacionesLejanas, 10.0);
        
        // Verificación
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void testUbicacionesAMenosDeEncuentraUbicacionesCercanas() {
        // Configuración
        List<Ubicacion> ubicaciones = List.of(estadioBoca, estadioRiver, estadioLiverpool);
        double distanciaMaxima = 10.0; // km
        
        // Ejecución
        List<Ubicacion> resultado = estadioIndependiente.ubicacionesAMenosDe(ubicaciones, distanciaMaxima);
        
        // Verificación
        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(estadioBoca));
        assertFalse(resultado.contains(estadioRiver));
    }

    @Test
    public void testUbicacionesAMenosDeConDistanciaJusta() {
        // Distancia Independiente-Boca: ~3.9km aprox
        List<Ubicacion> ubicaciones = List.of(estadioBoca);
        double distanciaMaxima = 3.8; // km (justo por debajo de la distancia real)
        
        // Ejecución
        List<Ubicacion> resultado = estadioIndependiente.ubicacionesAMenosDe(ubicaciones, distanciaMaxima);
        
        
        // Verificación
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void testUbicacionesAMenosDeConListaVacia() {
        // Configuración
        List<Ubicacion> ubicacionesVacias = List.of();
        
        // Ejecución
        List<Ubicacion> resultado = estadioIndependiente.ubicacionesAMenosDe(ubicacionesVacias, 10.0);
        
        // Verificación
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void testUbicacionesAMenosDeConDistanciaMinima() {
        // Configuración
        List<Ubicacion> ubicaciones = List.of(estadioIndependiente, estadioBoca);
        
        // Ejecución
        List<Ubicacion> resultado = estadioIndependiente.ubicacionesAMenosDe(ubicaciones, 0.00001); //Como la logica esta hecha con < no puedo poner 0
        
        // Verificación
        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(estadioIndependiente));
    }
}