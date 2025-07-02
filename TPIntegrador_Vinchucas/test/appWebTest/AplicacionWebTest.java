package appWebTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import appWeb.AplicacionWeb;
import appWeb.Recategorizador;
import filtroBusqueda.FiltroDeBusqueda;
import filtroBusqueda.Criterio;
import muestra.Muestra;
import muestra.IObserverMuestra;
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
	    private Ubicacion unq;
	    private Muestra muestraEnLaUnq, muestraEnLDA;
	    private ZonaDeCobertura zonaMock1, zonaMock2;
	    private IObserverMuestra reg;
		private ArrayList<Muestra> muestras;
		private ArrayList<ZonaDeCobertura> zonasDeCobertura;
		private ArrayList<Usuario> usuarios;
	    

    @BeforeEach
    public void setUp() throws Exception {

        dami = mock(Usuario.class);
        fran = mock(Usuario.class);
        joaco = mock(Usuario.class);
        opinionMock = mock(Opinion.class);
        filtro = mock(FiltroDeBusqueda.class);
        zonaMock1 = mock(ZonaDeCobertura.class);
        zonaMock2 = mock(ZonaDeCobertura.class);
        
        reg = mock(IObserverMuestra.class);
        
        unq = new Ubicacion(-34.7063, -58.2778);
        muestraEnLaUnq = new Muestra(LocalDateTime.of(2025, 6, 20, 12, 23), unq, dami, opinionMock,reg);
        muestraEnLDA = new Muestra(LocalDateTime.of(2023, 6, 19, 02, 13), unq, dami, opinionMock,reg);
        
        muestras = new ArrayList<>(List.of(muestraEnLaUnq, muestraEnLDA));
        zonasDeCobertura = new ArrayList<>(List.of(zonaMock1, zonaMock2));
        usuarios = new ArrayList<>(List.of(dami, fran, joaco));
        
        appVinchucas = new AplicacionWeb(muestras, zonasDeCobertura, usuarios, filtro);
    }
    
    @Test
    void testRegistrarUsuarioAndGetUsuarios() throws Exception {
        // Registramos usuarios y verificamos que se agreguen correctamente
    	Usuario usuarioMock = mock(Usuario.class);
        appVinchucas.registrarUsuario(usuarioMock);
        
        List<Usuario> nuevosUsuarios = appVinchucas.getUsuarios();
        assertEquals(4, nuevosUsuarios.size(), "La lista de usuarios debería tener 2 elementos");
        assertTrue(nuevosUsuarios.contains(dami));
        assertTrue(nuevosUsuarios.contains(fran));
        assertTrue(nuevosUsuarios.contains(usuarioMock));
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
        List<ZonaDeCobertura> zonasObtenidas = appVinchucas.getZonasDeCobertura();
        assertNotNull(zonasObtenidas, "La lista de zonas no debería ser nula");
        assertEquals(2, zonasObtenidas.size(), "Debe haber 1 zona de cobertura agregada");
        assertTrue(zonasObtenidas.contains(zonaMock1));
        assertTrue(zonasObtenidas.contains(zonaMock2));
    }
    

    
    @Test
    void testCorrectoDeMuestrasCercaDeLaUNQ() throws Exception {
    	List<Muestra> cercanas = appVinchucas.muestrasAMenosDe(muestraEnLaUnq, 12);
    	
    	//El resultado debe ser 1 ya que la muestraEnLaUnq como origen no se considera a ella misma dentro del rango
        assertEquals(1, cercanas.size());
 
    }
    
    @Test
    void testGetMuestrasEnviadasPorFiltraBien() throws Exception {
        Muestra m1 = new Muestra(LocalDateTime.now(), unq, fran, opinionMock,reg);
        Muestra m2 = new Muestra(LocalDateTime.now(), unq, dami, opinionMock,reg);

        appVinchucas.recibirMuestra(m1);
        appVinchucas.recibirMuestra(m2);

        List<Muestra> deFran = appVinchucas.getMuestrasEnviadasPor(fran);

        assertTrue(deFran.contains(m1));
        assertFalse(deFran.contains(m2));
    }
    
    @Test
    void testGetMuestrasEnviadasPor() throws Exception {
    	List<Muestra> deDami = appVinchucas.getMuestrasEnviadasPor(dami);

    	assertEquals(2, deDami.size());
    	assertTrue(deDami.contains(muestraEnLaUnq));
    	assertTrue(deDami.contains(muestraEnLDA));
    }
    
    @Test
    void testRecategorizar() {
        // recategorizamos con datos del enunciado
        appVinchucas.recategorizar(10, 20, 30);
        // Verificamos que se le haya pedido a los usuarios que se recategoricen con los mismos parámetros
        verify(dami).recategorizarSiCorresponde(any(Recategorizador.class), eq(10), eq(20), eq(30));
        verify(joaco).recategorizarSiCorresponde(any(Recategorizador.class), eq(10), eq(20), eq(30));
        verify(fran).recategorizarSiCorresponde(any(Recategorizador.class), eq(10), eq(20), eq(30));
    }
    
    @Test
    void testGetMuestras() throws Exception {
    	Muestra muestraMock = mock(Muestra.class);
    	appVinchucas.recibirMuestra(muestraMock);
    	assertEquals(3, appVinchucas.getMuestras().size());
    	assertTrue(appVinchucas.getMuestras().contains(muestraMock));
    }
    
    @Test
    void testAgregarZonaCobertura() {
    	ZonaDeCobertura zonaMock = mock(ZonaDeCobertura.class);
    	appVinchucas.añadirZonaDeCobertura(zonaMock);
    	assertEquals(3, appVinchucas.getZonasDeCobertura().size());
    	assertTrue(appVinchucas.getZonasDeCobertura().contains(zonaMock));
    }
    
    @Test
    void testFiltrarMuestras() {
    	Criterio criterioMock = mock(Criterio.class);
    	appVinchucas.filtrarMuestras(criterioMock);
    	verify(filtro, times(1)).filtrarMuestras(appVinchucas.getMuestras(), criterioMock);
    }
    
    @Test
    void testEnviarMuestraAZonas() {
    	ZonaDeCobertura zonaMock = mock(ZonaDeCobertura.class);
    	when(zonaMock.contiene(muestraEnLDA.getUbicacion())).thenReturn(true);
    	appVinchucas.añadirZonaDeCobertura(zonaMock);
    	appVinchucas.enviarMuestraAZonas(muestraEnLDA);
    	verify(zonaMock, times(1)).contiene(muestraEnLDA.getUbicacion());
    	verify(zonaMock, times(1)).registrarMuestra(muestraEnLDA);
    }
    
    @Test
    void testRecibirMuestraValidada() {
    	ZonaDeCobertura zonaMock = mock(ZonaDeCobertura.class);
    	when(zonaMock.contiene(muestraEnLDA.getUbicacion())).thenReturn(true);
    	appVinchucas.añadirZonaDeCobertura(zonaMock);
    	appVinchucas.recibirMuestraValidada(muestraEnLDA);
    	verify(zonaMock, times(1)).contiene(muestraEnLDA.getUbicacion());
    	verify(zonaMock, times(1)).registrarValidacionDeMuestra(muestraEnLDA);
    }
}