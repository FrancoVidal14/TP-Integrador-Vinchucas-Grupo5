package VinchucasTest;

import org.junit.jupiter.api.Test;
import Vinchucas.FuncionalidadExterna;
import Vinchucas.Organizacion;
import Vinchucas.TipoDeOrganizacion;
import Vinchucas.Ubicacion;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;

class OrganizacionTest {

    private Ubicacion ubicacion;
    private TipoDeOrganizacion tipo;
    private Organizacion organizacion;
    private FuncionalidadExterna func1;
    private FuncionalidadExterna func2;
    
    @BeforeEach
    void setUp() {
        ubicacion = new Ubicacion(-34.6037, -58.3816);
        tipo = TipoDeOrganizacion.Salud;
        func1 = mock(FuncionalidadExterna.class);
        func2 = mock(FuncionalidadExterna.class);
        organizacion = new Organizacion(ubicacion, tipo, 50, func1, func2);
    }

    @Test
    void constructorInicializaCorrectamente() {
        assertEquals(ubicacion, organizacion.getUbicacion());
        assertEquals(tipo, organizacion.getTipo());
        assertEquals(50, organizacion.getCantEmpleados());
    }

    @Test
    void getYSetUbicacion() {
        Ubicacion nuevaUbicacion = new Ubicacion(-31.4201, -64.1888);
        organizacion.setUbicacion(nuevaUbicacion);
        assertEquals(nuevaUbicacion, organizacion.getUbicacion());
    }

    @Test
    void setUbicacionConNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            organizacion.setUbicacion(null);
        });
    }

    @Test
    void getYSetCantEmpleados() {
        organizacion.setCantEmpleados(100);
        assertEquals(100, organizacion.getCantEmpleados());
    }

    @Test
    void setCantEmpleadosNegativoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            organizacion.setCantEmpleados(-5);
        });
    }

    @Test
    void getYSetTipo() {
        organizacion.setTipo(TipoDeOrganizacion.Educativa);
        assertEquals(TipoDeOrganizacion.Educativa, organizacion.getTipo());
    }

}