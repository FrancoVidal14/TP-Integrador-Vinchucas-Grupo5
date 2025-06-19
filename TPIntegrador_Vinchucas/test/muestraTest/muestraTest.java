package muestraTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Vinchucas.Ubicacion;
import muestra.Muestra;
import usuario.Opinion;
import usuario.Resultado;
import usuario.Usuario;
import usuario.UsuarioGeneral;
import usuario.UsuarioValidado;

class muestraTest {
	
	LocalDateTime fechaMuestra;
	Ubicacion unq;
	Usuario dami;
	Usuario joaco;
	Usuario fran;
	Opinion opinionInicial;
	Muestra muestra;

	@BeforeEach
	void setUp() throws Exception {
		fechaMuestra = LocalDateTime.now().minusDays(7);
		unq = new Ubicacion(-34.7063, -58.2778);
		dami = new UsuarioGeneral();
		joaco = new UsuarioValidado();
		fran = new UsuarioValidado();
		opinionInicial = new Opinion(fechaMuestra, dami, Resultado.CHINCHE_FOLIADA);
		muestra = new Muestra(fechaMuestra, unq, dami, opinionInicial);
	}

	@Test
	void getUsuarioTest() {
	    assertEquals(dami, muestra.getUsuario());
	}

	@Test
	void getFechaTest() {
	    assertEquals(fechaMuestra, muestra.getFecha());
	}
	
	@Test
	void getUbicacionTest() {
	    assertEquals(unq, muestra.getUbicacion());
	}
	
	@Test
	void esUsuarioEnviadorValidoTest() {
		assertTrue(muestra.esUsuarioEnviador(dami));
	}
	
	@Test
	void generadaEnUltimosValidoTest() {
		//nunca me toma exactamente el mismo dia porque el now es un quilombo pero deberia tomarlo con fechas reales
		int ultimosDias = 8;
		assertTrue(muestra.generadaEnUltimos(ultimosDias));
	}
	
	@Test
	void opinionInicialProcesadaTest() {
	    assertEquals(1, muestra.getOpiniones().size());
	    assertEquals(dami, muestra.getOpiniones().get(0).getUsuario());
	}
	
	@Test
	void resultadoActualValido() {
		assertEquals(Resultado.CHINCHE_FOLIADA, muestra.resultadoActual());
	}
	
	@Test
	void getOpinionesDeValido() {
		assertEquals(1, muestra.getOpinionesDe(dami).size());
	}
	
	@Test
	void buscarFechaUltimaVotacionValido() {
		assertEquals(fechaMuestra, muestra.buscarFechaUltimaVotacion());
	}
	
	@Test
	void usuarioHizoRevisionExitosaValido() {
		int ultimosDias = 8;
		assertTrue(muestra.usuarioHizoRevisionExitosa(dami, ultimosDias));
	}
}
