package zonaCoberturaTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import muestra.Muestra;
import zonaCobertura.ManejadorDeNotificaciones;
import zonaCobertura.NotificadosPorMuestras;
import zonaCobertura.ZonaDeCobertura;

class ManejadorDeNotificacionesTest {
	
	ManejadorDeNotificaciones notificador;
	NotificadosPorMuestras notificado1;
	NotificadosPorMuestras notificado2;
	
	@BeforeEach
	void setUp() throws Exception {
		notificador = new ManejadorDeNotificaciones();
		notificado1 = mock(NotificadosPorMuestras.class);
		notificado2 = mock(NotificadosPorMuestras.class);
		notificador.registrarOrganizacionEnZona(notificado2);
	}

	@Test
	void testRegistrarOrganizacionEnZona() {
		notificador.registrarOrganizacionEnZona(notificado1);
		assertEquals(2, notificador.getNotificados().size());
		assertEquals(notificado1, notificador.getNotificados().get(1));
	}

	@Test
	void testSacarDelRegistroEnZonas() {
		notificador.sacarDelRegistroEnZonas(notificado2);
		assertEquals(0, notificador.getNotificados().size());
	}

	@Test
	void testGetNotificados() {
		assertEquals(1, notificador.getNotificados().size());
		assertEquals(notificado2, notificador.getNotificados().get(0));
	}

	@Test
	void testSetNotificados() {
		assertEquals(1, notificador.getNotificados().size());
		List<NotificadosPorMuestras> notificados = List.of(notificado1, notificado2);
		notificador.setNotificados(notificados);
		assertEquals(2, notificador.getNotificados().size());
	}

	@Test
	void testRecibirInformacionDeMuestraRegistrada() {
    	Muestra muestraMock = mock(Muestra.class);
    	ZonaDeCobertura zonaMock = mock(ZonaDeCobertura.class);
		notificador.recibirInformacionDeMuestraRegistrada(muestraMock, zonaMock);
		verify(notificado2, times(1)).recibirNotificacionDeRegistroDe(muestraMock, zonaMock);
	}

	@Test
	void testRecibirInformacionDeMuestraValidada() {
    	Muestra muestraMock = mock(Muestra.class);
    	ZonaDeCobertura zonaMock = mock(ZonaDeCobertura.class);
		notificador.recibirInformacionDeMuestraValidada(muestraMock, zonaMock);
		verify(notificado2, times(1)).recibirNotificacionDeValidacionDe(muestraMock, zonaMock);
	}

}
