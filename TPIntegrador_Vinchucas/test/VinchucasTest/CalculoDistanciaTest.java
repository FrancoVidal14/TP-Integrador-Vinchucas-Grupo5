package VinchucasTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Vinchucas.CalculadorDistancia;
import Vinchucas.CalculoDistancia;
import Vinchucas.Ubicacion;

import static org.junit.jupiter.api.Assertions.*;


public class CalculoDistanciaTest {
	private CalculadorDistancia calculador = new CalculoDistancia();

    private Ubicacion estadioIndependiente;
    private Ubicacion estadioBoca;
    private Ubicacion estadioLiverpool;
    private Ubicacion estadioManchester;
    private Ubicacion estadioMilan;

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
    public void testDistanciaDeIndependienteABoca() {
        double distancia = calculador.calcular(estadioIndependiente, estadioBoca);
        assertTrue(distancia > 8 && distancia < 8.3 );
    }

    @Test
    public void testDistanciaEntreMismasUbicacionesEsCero() {
        double distancia = calculador.calcular(estadioIndependiente, estadioIndependiente);
        assertTrue(distancia == 0.00);
    }


}