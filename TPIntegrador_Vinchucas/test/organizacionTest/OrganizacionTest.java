package organizacionTest;

import org.junit.jupiter.api.Test;

import organizacion.FuncionalidadExterna;
import organizacion.Organizacion;
import organizacion.TipoDeOrganizacion;
import zonaCobertura.Ubicacion;

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
        tipo = TipoDeOrganizacion.SALUD;
        func1 = mock(FuncionalidadExterna.class);
        func2 = mock(FuncionalidadExterna.class);
        organizacion = new Organizacion(ubicacion, tipo, 50, func1, func2);
    }
    
    @Test
    void testGetFuncionalidadRegistro() {
        assertEquals(func1, organizacion.getFuncionalidadRegistro(),
            "Debería devolver la funcionalidad de registro proporcionada al constructor");
    }

    @Test
    void testGetFuncionalidadValidacion() {
        assertEquals(func2, organizacion.getFuncionalidadValidacion(),
            "Debería devolver la funcionalidad de validación proporcionada al constructor");
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
        organizacion.setTipo(TipoDeOrganizacion.EDUCATIVA);
        assertEquals(TipoDeOrganizacion.EDUCATIVA, organizacion.getTipo());
    }

}