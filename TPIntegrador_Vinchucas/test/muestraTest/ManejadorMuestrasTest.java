package muestraTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import muestra.IObserverMuestra;
import muestra.ManejadorDeMuestras;
import muestra.Muestra;

class ManejadorMuestrasTest {
	
	IObserverMuestra observador;
	ManejadorDeMuestras manejador;

	@BeforeEach
	void setUp() throws Exception {
		observador = mock(IObserverMuestra.class);
		manejador = new ManejadorDeMuestras();
	}

	@Test
	void testGetObservadores() {
		manejador.suscribirObservador(observador);
		assertTrue(manejador.getObservadores().contains(observador));
	}

	@Test
	void testSetObservadores() {
		List<IObserverMuestra> observadores = List.of(observador);
		manejador.setObservadores(observadores);
		assertEquals(observadores, manejador.getObservadores());
	}

	@Test
	void testSuscribirObservador() {
		manejador.suscribirObservador(observador);
		assertTrue(manejador.getObservadores().contains(observador));
	}

	@Test
	void testDesuscribirObservador() {
		manejador.suscribirObservador(observador);
		assertTrue(manejador.getObservadores().contains(observador));
		manejador.desuscribirObservador(observador);
		assertFalse(manejador.getObservadores().contains(observador));
	}

	@Test
	void testNotificarValidacion() {
		Muestra muestraMock = mock(Muestra.class);
		manejador.suscribirObservador(observador);
		assertTrue(manejador.getObservadores().contains(observador));
		manejador.notificarValidacion(muestraMock);
		verify(observador).recibirMuestraValidada(muestraMock);
	}

}
