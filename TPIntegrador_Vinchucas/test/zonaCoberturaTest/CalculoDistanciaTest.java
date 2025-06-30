package zonaCoberturaTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import zonaCobertura.CalculadorDistancia;
import zonaCobertura.CalculoDistancia;
import zonaCobertura.Ubicacion;

import static org.junit.jupiter.api.Assertions.*;


public class CalculoDistanciaTest {
	private CalculadorDistancia calculador = new CalculoDistancia();

    private Ubicacion estadioIndependiente;
    private Ubicacion estadioBoca;

    @BeforeEach
    public void setup() {
        estadioIndependiente = new Ubicacion(-34.6703, -58.3710);  // Libertadores de América
        estadioBoca = new Ubicacion(-34.6356, -58.3643);          // La Bombonera

        calculador = new CalculoDistancia();
    }

    @Test
    public void testDistanciaDeIndependienteABoca() {
        double distancia = calculador.calcular(estadioIndependiente, estadioBoca);
        assertTrue(distancia > 3.8 && distancia < 4.2);
    }

    @Test
    public void testDistanciaEntreMismasUbicacionesEsCero() {
        double distancia = calculador.calcular(estadioIndependiente, estadioIndependiente);
        assertTrue(distancia == 0.00);
    }


}