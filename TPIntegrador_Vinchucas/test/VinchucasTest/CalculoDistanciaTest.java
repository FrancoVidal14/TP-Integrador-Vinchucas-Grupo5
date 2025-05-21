package VinchucasTest;

import Vinchucas.Ubicacion;

import Vinchucas.CalculoDistancia;
import Vinchucas.CalculadorDistancia;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CalculoDistanciaTest {

	private CalculadorDistancia calculador = new CalculoDistancia();


    @Test
    public void testDistanciaEntreMismasUbicacionesEsCero() {
        Ubicacion ubicacion = new Ubicacion(40.7128, -74.0060); // Nueva York
        double distancia = calculador.calcular(ubicacion, ubicacion);
        assertEquals(0.0, distancia, 0.0001);
    }

    @Test
    public void testDistanciaNuevaYorkaLosAngeles() {
        Ubicacion ny = new Ubicacion(40.7128, -74.0060);
        Ubicacion la = new Ubicacion(34.0522, -118.2437);

        double distancia = calculador.calcular(ny, la);

        // La distancia real aprox. entre NY y LA es ~3936 km
        assertEquals(3936, distancia, 10); // margen de 10 km
    }
}
