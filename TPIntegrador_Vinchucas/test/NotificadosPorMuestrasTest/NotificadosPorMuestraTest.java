package NotificadosPorMuestrasTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import appWeb.AplicacionWeb;
import filtroBusqueda.FiltroDeBusqueda;
import muestra.Muestra;
import organizacion.Organizacion;
import usuario.NivelExperto;
import usuario.Opinion;
import usuario.Resultado;
import usuario.Usuario;
import zonaCobertura.CalculadorDistancia;
import zonaCobertura.NotificadosPorMuestras;
import zonaCobertura.Ubicacion;
import zonaCobertura.ZonaDeCobertura;

class NotificadosPorMuestraTest {
	
	private LocalDateTime fecha1;
	private LocalDateTime fecha2;
	private LocalDateTime fecha5;
	
	private Ubicacion ubi1;
	private Ubicacion ubi2;
	private Ubicacion ubi3;
	
	private Muestra m1; 
	private Muestra m2;
	
	private AplicacionWeb app;
	
	private Usuario us1;
	private Usuario us2;
	private Usuario us3;
	
	private Opinion op2;
	private Opinion op3;
	private Opinion op4;
	
	private Resultado res2;
	private Resultado res4;
	
	private ZonaDeCobertura zonaBernal;
	private ZonaDeCobertura zonaQuilmes;
	private ZonaDeCobertura zonaBerazategui;
	
	private Organizacion org1;
	private Organizacion org2;
	private Organizacion org3;
	
	private CalculadorDistancia calculador1;
	private CalculadorDistancia calculador2;
	private CalculadorDistancia calculador3;
	
	private FiltroDeBusqueda filtro;
	
	private List<NotificadosPorMuestras> observadores;
	
	@BeforeEach
	void setUp() throws Exception {
		
		calculador1 = mock(CalculadorDistancia.class);
		calculador2 = mock(CalculadorDistancia.class);
		calculador3 = mock(CalculadorDistancia.class);
		
		us1 = new Usuario(new NivelExperto());
		us2 = new Usuario(new NivelExperto());
		us3 = new Usuario(new NivelExperto());
		
		res2 = Resultado.CHINCHE_FOLIADA;
		res4 = Resultado.NO_DEFINIDO;
		
		fecha1 = LocalDateTime.of(2020, 9, 18, 19, 25);
		fecha2 = LocalDateTime.of(2021, 10, 29, 20, 20);
		fecha5 = LocalDateTime.of(2024, 5, 10, 10, 36);
		
		op2 = new Opinion(fecha2, us2, res2);
		op3 = new Opinion(fecha5, us3, res4);
		op4 = new Opinion(fecha1, us1, res4);
		
		ubi1 = new Ubicacion(20, 60);
		ubi2 = new Ubicacion(45.76, 10.2);
		ubi3 = new Ubicacion(19.2, 5.47);
		
		app = new AplicacionWeb(filtro);
		
		m1 = new Muestra(fecha1, ubi1, us1, op2);
		m2 = new Muestra(fecha2, ubi1, us2, op3); 
		
		zonaBernal = new ZonaDeCobertura("Bernal", ubi1, 10000000000.3, calculador1);
		zonaQuilmes = new ZonaDeCobertura("Quilmes", ubi2, 310.2, calculador2);
		zonaBerazategui = new ZonaDeCobertura("Berazategui", ubi3, 325.6, calculador3);
		
		org1 = mock(Organizacion.class);
		org2 = mock(Organizacion.class);
		org3 = mock(Organizacion.class);
		
		observadores = new ArrayList<>();
		
		observadores.add(org1);
		observadores.add(org2);
		observadores.add(org3);
	}
	
	@Test
	void testZonasSeAniadenALaApp() {
		app.añadirZonaDeCobertura(zonaBernal);
		app.añadirZonaDeCobertura(zonaQuilmes);
		app.añadirZonaDeCobertura(zonaBerazategui);
		assertEquals(3, app.getZonasDeCobertura().size());
	}
	
	@Test
    void testOrganizacionSeRegistraEnBernal() {
        zonaBernal.getManejador().registrarOrganizacionEnZona(org1);
        assertEquals(1, zonaBernal.getManejador().getNotificados().size());
    }

    @Test
    void testOrganizacionSeDesregistraDeBernal() throws Exception {
    	zonaBernal.getManejador().registrarOrganizacionEnZona(org1);
    	zonaBernal.getManejador().sacarDelRegistroEnZonas(org1);
        app.recibirMuestra(m1);
        verify(org1, never()).recibirNotificacionDeRegistroDe(any(Muestra.class), any(ZonaDeCobertura.class));
    }


    @Test
    void testOrganizacionRegistradaRecibeRegistroDeMuestraDeLaZonaSuscripta() throws Exception {
    	app.añadirZonaDeCobertura(zonaBernal);
    	zonaBernal.getManejador().registrarOrganizacionEnZona(org1);
        app.recibirMuestra(m1);
        assertEquals(1, app.getZonasDeCobertura().size());
        assertEquals(1, app.getMuestras().size());
        assertTrue(zonaBernal.contiene(m1.getUbicacion()));
        assertEquals(1, zonaBernal.getManejador().getNotificados().size());
        verify(org1, times(1)).recibirNotificacionDeRegistroDe(m1, zonaBernal);
    }

    @Test
    void testOrganizacionRegistradaRecibeRegistroDeMuestraValidadaDeLaZonaSuscripta() throws Exception {
    	app.añadirZonaDeCobertura(zonaBernal);
    	zonaBernal.getManejador().registrarOrganizacionEnZona(org2);
    	app.recibirMuestra(m2);
    	m2.procesarOpinion(op2);
    	m2.procesarOpinion(op4);
    	assertTrue(m2.esMuestraVerificada());
        verify(org2, times(1)).recibirNotificacionDeValidacionDe(m2, zonaBernal);
    }

    @Test
    void testSoloLasOrganizacionesRegistradasRecibenLaNotificacionDeRegistroDeMuestra() throws Exception {
    	app.añadirZonaDeCobertura(zonaBernal);
    	zonaBernal.getManejador().registrarOrganizacionEnZona(org1);
        zonaQuilmes.getManejador().registrarOrganizacionEnZona(org2);
        app.recibirMuestra(m1);
        verify(org1, times(1)).recibirNotificacionDeRegistroDe(m1, zonaBernal);
        verify(org2, never()).recibirNotificacionDeRegistroDe(any(Muestra.class), any(ZonaDeCobertura.class));
    }

    @Test
    void testOrganizacionRecibeNotificacionesDeMultiplesZonasSiSeEncuentraRegistradaEnAmbas() throws Exception {
    	app.añadirZonaDeCobertura(zonaBernal);
    	app.añadirZonaDeCobertura(zonaQuilmes);
    	zonaBernal.getManejador().registrarOrganizacionEnZona(org1);
        zonaQuilmes.getManejador().registrarOrganizacionEnZona(org1);
        app.recibirMuestra(m1);
        app.recibirMuestra(m2);
        m2.procesarOpinion(op2);
    	m2.procesarOpinion(op4);
        verify(org1, times(1)).recibirNotificacionDeRegistroDe(m1, zonaBernal);
        verify(org1, times(1)).recibirNotificacionDeValidacionDe(m2, zonaQuilmes);
    }

    @Test
    void testNoHayNotifacionesPorqueNingunaOrganizacionEstaRegistrada() throws Exception {
        app.recibirMuestra(m1);
        verifyNoInteractions(org1, org2, org3);
    }
    
    @Test
    void testSetObservadoresEnManejador() {
    	zonaQuilmes.getManejador().setNotificados(observadores);
    	assertEquals(3, zonaQuilmes.getManejador().getNotificados().size());
    }
}