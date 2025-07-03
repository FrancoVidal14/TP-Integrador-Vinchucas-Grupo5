package appWebTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import appWeb.AplicacionWeb;
import appWeb.Recategorizador;
import filtroBusqueda.FiltroDeBusqueda;
import filtroBusqueda.Criterio;
import muestra.Muestra;
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
	    

    @BeforeEach
    public void setUp() throws Exception {

        dami = mock(Usuario.class);
        fran = mock(Usuario.class);
        joaco = mock(Usuario.class);
        opinionMock = mock(Opinion.class);
        filtro = mock(FiltroDeBusqueda.class);
        zonaMock1 = mock(ZonaDeCobertura.class);
        zonaMock2 = mock(ZonaDeCobertura.class);
        
        unq = new Ubicacion(-34.7063, -58.2778);
        muestraEnLaUnq = new Muestra(LocalDateTime.of(2025, 6, 20, 12, 23), unq, dami, opinionMock);
        muestraEnLDA = new Muestra(LocalDateTime.of(2023, 6, 19, 02, 13), unq, dami, opinionMock);
        
        appVinchucas = new AplicacionWeb(filtro);
        appVinchucas.añadirZonaDeCobertura(zonaMock1);
        appVinchucas.añadirZonaDeCobertura(zonaMock2);
        appVinchucas.registrarUsuario(dami);
        appVinchucas.registrarUsuario(fran);
        appVinchucas.registrarUsuario(joaco);
        
    }
    
    @Test
    void testRegistrarUsuarioAndGetUsuarios() {
        Usuario cabra = mock(Usuario.class);
        appVinchucas.registrarUsuario(cabra);

        Set<Usuario> usuarios = appVinchucas.getUsuarios();

        assertEquals(4, usuarios.size());
        assertTrue(usuarios.contains(dami));
        assertTrue(usuarios.contains(fran));
        assertTrue(usuarios.contains(joaco));
        assertTrue(usuarios.contains(cabra));
    }

    
    @Test
    void testGetFiltro() {
        // Se verifica que el filtro inyectado en el constructor se retorne correctamente
        FiltroDeBusqueda filtroObtenido = appVinchucas.getFiltro();
        assertNotNull(filtroObtenido, "El filtro no puede ser nulo");
        assertEquals(filtro, filtroObtenido, "El filtro devuelto debe ser el mismo pasado en el constructor");
    }



    @Test
    void testGetZonasDeCobertura() {
        List<ZonaDeCobertura> zonasObtenidas = appVinchucas.getZonasDeCobertura();

        assertEquals(2, zonasObtenidas.size());
        assertTrue(zonasObtenidas.contains(zonaMock1));
        assertTrue(zonasObtenidas.contains(zonaMock2));
    }
    

    
    @Test
    void testCorrectoDeMuestrasCercaDeLaUNQ() {
        // Registramos ambas muestras en la app
        appVinchucas.recibirMuestra(muestraEnLaUnq);
        appVinchucas.recibirMuestra(muestraEnLDA);

        List<Muestra> cercanas = appVinchucas.muestrasAMenosDe(muestraEnLaUnq, 12);

        // Se excluye a sí misma, solo entra la otra muestra en misma ubicación
        assertEquals(1, cercanas.size());
        assertTrue(cercanas.contains(muestraEnLDA));
    }
    
    @Test
    void testIncorrectoDeMuestrasCercaDeLaUNQ() {
    	//creo una muestra fuera del alcance de la muestra en la unq
    	Muestra muestraMock = mock(Muestra.class);
    	Ubicacion ubicacionFueraAlcanceUNQ = new Ubicacion(-10.7063, -80.2778);
    	when(muestraMock.getUbicacion()).thenReturn(ubicacionFueraAlcanceUNQ);
    	
        // Registramos ambas muestras en la app
        appVinchucas.recibirMuestra(muestraEnLaUnq);
        appVinchucas.recibirMuestra(muestraMock);
        
        //guardo la lista de muestras cercanas a la muestra de la unq
        List<Muestra> cercanas = appVinchucas.muestrasAMenosDe(muestraEnLaUnq, 1);

        // No hay muestras cercanas 
        assertEquals(0, cercanas.size());
    }
    
    @Test
    void testGetMuestrasEnviadasPorFiltraBien() throws Exception {
        Muestra m1 = new Muestra(LocalDateTime.now(), unq, fran, opinionMock);
        Muestra m2 = new Muestra(LocalDateTime.now(), unq, dami, opinionMock);

        appVinchucas.recibirMuestra(m1);
        appVinchucas.recibirMuestra(m2);

        List<Muestra> deFran = appVinchucas.getMuestrasEnviadasPor(fran);

        assertTrue(deFran.contains(m1));
        assertFalse(deFran.contains(m2));
    }

    @Test
    void testGetMuestrasEnviadasPor() {
    	    appVinchucas.recibirMuestra(muestraEnLaUnq);
    	    appVinchucas.recibirMuestra(muestraEnLDA);

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
    void testGetMuestras() {
        appVinchucas.recibirMuestra(muestraEnLDA);

        List<Muestra> todas = appVinchucas.getMuestras();
        assertEquals(1, todas.size());
        assertTrue(todas.contains(muestraEnLDA));
    }
    
    @Test
    void testAñadirZonaCoberturaConMuestrasParaRegistrar() {
    	appVinchucas.recibirMuestra(muestraEnLaUnq);
        ZonaDeCobertura zonaMock = mock(ZonaDeCobertura.class);
        appVinchucas.añadirZonaDeCobertura(zonaMock);
        //Verificamos que la nueva zona fue añadida
        List<ZonaDeCobertura> zonas = appVinchucas.getZonasDeCobertura();
        assertEquals(3, zonas.size());
        assertTrue(zonas.contains(zonaMock));
        //Verificamos que recibe el mensaje de registrar a la unica muestra recibida en la appweb
        verify(zonaMock).registrarMuestraSiCorresponde(muestraEnLaUnq);

    }
    
    @Test
    void testAñadirZonaDeCoberturaSinMuestrasParaRegistrar() {
        ZonaDeCobertura zonaMock = mock(ZonaDeCobertura.class);
        appVinchucas.añadirZonaDeCobertura(zonaMock);
        //Verificamos que la nueva zona fue añadida
        List<ZonaDeCobertura> zonasActuales = appVinchucas.getZonasDeCobertura();
        assertEquals(3, zonasActuales.size());
        assertTrue(zonasActuales.contains(zonaMock));

        // Verificamos que nunca se le llamo porque no hay muestras
        verify(zonaMock, never()).registrarMuestraSiCorresponde(any(Muestra.class));
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

        // Configurar la zona para que sí contenga la muestra
        when(zonaMock.contiene(muestraEnLDA.getUbicacion())).thenReturn(true);

        // Añadir zona y enviar muestra
        appVinchucas.añadirZonaDeCobertura(zonaMock);
        appVinchucas.enviarRegistroDeMuestraAZonas(muestraEnLDA); // método correcto

        verify(zonaMock).registrarMuestraSiCorresponde(muestraEnLDA);
    }
    
    @Test
    void testRecibirMuestraEnAplicacion() {
        ZonaDeCobertura zonaMock = mock(ZonaDeCobertura.class);
        appVinchucas.añadirZonaDeCobertura(zonaMock);

        when(zonaMock.contiene(muestraEnLDA.getUbicacion())).thenReturn(true);

        appVinchucas.recibirMuestra(muestraEnLDA);

        verify(zonaMock).registrarMuestraSiCorresponde(muestraEnLDA);
    }
    
	@Test
	public void testActualizarUsuariosIncluyeOpinadores() {
		Muestra muestra = mock(Muestra.class);
	    Opinion opinion = mock(Opinion.class);
	    when(opinion.getUsuario()).thenReturn(fran);
	    when(muestra.getOpiniones()).thenReturn(List.of(opinion));
	    when(muestra.getUsuario()).thenReturn(dami);

	    appVinchucas.recibirMuestra(muestra);
	    Set<Usuario> usuarios = appVinchucas.getUsuarios();
	    
	    //usuario opinador de la muestra
	    assertTrue(usuarios.contains(fran));
	    //usuario enviador de la muestra, registrado cuando se recibe la muestra
	    assertTrue(usuarios.contains(dami));

	}
}