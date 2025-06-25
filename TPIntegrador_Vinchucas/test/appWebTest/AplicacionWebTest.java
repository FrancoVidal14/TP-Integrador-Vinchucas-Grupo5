package appWebTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import appWeb.AplicacionWeb;
import appWeb.Recategorizador;
import filtroBusqueda.FiltroDeBusqueda;
import muestra.Muestra;
import muestra.RegistroDeValidaciones;
import usuario.Usuario;
import zonaCobertura.Ubicacion;
import zonaCobertura.ZonaDeCobertura;
import usuario.Opinion;

class AplicacionWebTest {

    // Variables de instancia accesibles desde todos los tests
	    private AplicacionWeb appVinchucas;
	    private Usuario dami, fran, joaco;
	    private Opinion opinionMock;
	    private FiltroDeBusqueda filtro;
	    private Ubicacion unq, estadioIndependiente, estadioBoca;
	    private Muestra muestraEnLaUnq, muestraEnLDA;
	    private RegistroDeValidaciones reg;
	    

    @BeforeEach
    public void setUp() throws Exception {

        dami = mock(Usuario.class);
        fran = mock(Usuario.class);
        joaco = mock(Usuario.class);
        opinionMock = mock(Opinion.class);
        filtro = mock(FiltroDeBusqueda.class);
        
        reg = mock(RegistroDeValidaciones.class);
        
        unq = new Ubicacion(-34.7063, -58.2778);
        muestraEnLaUnq = new Muestra(LocalDateTime.of(2025, 6, 20, 12, 23), unq, dami, opinionMock,reg);
        muestraEnLDA = new Muestra(LocalDateTime.of(2023, 6, 19, 02, 13), unq, dami, opinionMock,reg);

        estadioIndependiente = new Ubicacion(-34.6703, -58.3710);
        estadioBoca = new Ubicacion(-34.6356, -58.3643);

        appVinchucas = new AplicacionWeb(filtro);
    }
    
    @Test
    void testRegistrarUsuarioAndGetUsuarios() throws Exception {
        // Verificamos que inicialmente la lista de usuarios esté vacía
        List<Usuario> usuariosInicial = appVinchucas.getUsuarios();
        assertNotNull(usuariosInicial, "La lista de usuarios no debería ser nula");
        assertEquals(0, usuariosInicial.size(), "Inicialmente la lista de usuarios debería estar vacía");
        
        // Registramos usuarios y verificamos que se agreguen correctamente
        appVinchucas.registrarUsuario(dami);
        appVinchucas.registrarUsuario(fran);
        
        List<Usuario> usuariosDespues = appVinchucas.getUsuarios();
        assertEquals(2, usuariosDespues.size(), "La lista de usuarios debería tener 2 elementos");
        assertTrue(usuariosDespues.contains(dami));
        assertTrue(usuariosDespues.contains(fran));
    }

    
    @Test
    void testGetFiltro() {
        // Se verifica que el filtro inyectado en el constructor se retorne correctamente
        FiltroDeBusqueda filtroObtenido = appVinchucas.getFiltro();
        assertNotNull(filtroObtenido, "El filtro no puede ser nulo");
        assertEquals(filtro, filtroObtenido, "El filtro devuelto debe ser el mismo pasado en el constructor");
    }



    @Test
    void testGetZonasDeCobertura() throws Exception {
        // Se crea una zona de cobertura simulada (mock) y se agrega a la lista obtenida del getter
        List<ZonaDeCobertura> zonas = appVinchucas.getZonasDeCobertura();
        ZonaDeCobertura zonaMock = mock(ZonaDeCobertura.class);
        zonas.add(zonaMock);
        
        List<ZonaDeCobertura> zonasObtenidas = appVinchucas.getZonasDeCobertura();
        assertNotNull(zonasObtenidas, "La lista de zonas no debería ser nula");
        assertEquals(1, zonasObtenidas.size(), "Debe haber 1 zona de cobertura agregada");
        assertTrue(zonasObtenidas.contains(zonaMock));
    }
    

    
    @Test
    void testCorrectoDeMuestrasCercaDeLaUNQ() throws Exception {
    	appVinchucas.recibirMuestra(muestraEnLaUnq, dami, opinionMock);
    	appVinchucas.recibirMuestra(muestraEnLDA, fran, opinionMock);
    	
    	List<Muestra> cercanas = appVinchucas.muestrasAMenosDe(muestraEnLaUnq, 12);
    	
    	//El resultado debe ser 1 ya que la muestraEnLaUnq como origen no se considera a ella misma dentro del rango
        assertEquals(1, cercanas.size());
 
    }
    
    @Test
    void testGetMuestrasEnviadasPorFiltraBien() throws Exception {
        Muestra m1 = new Muestra(LocalDateTime.now(), unq, fran, opinionMock,reg);
        Muestra m2 = new Muestra(LocalDateTime.now(), unq, dami, opinionMock,reg);

        appVinchucas.recibirMuestra(m1, fran, opinionMock);
        appVinchucas.recibirMuestra(m2, dami, opinionMock);

        List<Muestra> deFran = appVinchucas.getMuestrasEnviadasPor(fran);

        assertTrue(deFran.contains(m1));
        assertFalse(deFran.contains(m2));
    }
    
    @Test
    void testGetMuestrasEnviadasPor() throws Exception {
    	Muestra m1 = new Muestra(LocalDateTime.now(), unq, dami, opinionMock,reg);
    	Muestra m2 = new Muestra(LocalDateTime.now(), estadioBoca, joaco, opinionMock,reg);

    	appVinchucas.recibirMuestra(m1, dami, opinionMock);
    	appVinchucas.recibirMuestra(m2, joaco, opinionMock);

    	List<Muestra> deDami = appVinchucas.getMuestrasEnviadasPor(dami);

    	assertEquals(1, deDami.size());
    	assertTrue(deDami.contains(m1));
    	assertFalse(deDami.contains(m2));
    }
    
    @Test
    void testRecategorizar() {
        appVinchucas.registrarUsuario(dami);
        // recategorizamos con datos del enunciado
        appVinchucas.recategorizar(10, 20, 30);
        // Verificamos que se le haya pedido al usuario que se recategorice con los mismos parámetros
        verify(dami).recategorizarSiCorresponde(any(Recategorizador.class), eq(10), eq(20), eq(30));
    }
}