package usuarioTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import appWeb.Recategorizador;
import usuario.NivelExpertoValidado;
import usuario.NivelConocimiento;
import usuario.NivelExperto;
import usuario.Usuario;

class UsuarioTest {
	
	Usuario joaco;
	Usuario fran;
	Usuario dami;
	Recategorizador recategorizador;
	
	@BeforeEach
	void setUp() throws Exception {
		NivelConocimiento nivelValidado = new NivelExpertoValidado();
		NivelConocimiento nivelExperto = new NivelExperto();
		joaco = new Usuario();
		fran = new Usuario(nivelValidado);
		dami = new Usuario(nivelExperto);
		recategorizador = mock(Recategorizador.class);
	}

	@Test
	void testEsExperto() {
		assertTrue(fran.esExperto()); //es siempre experto
		assertTrue(dami.esExperto()); //es experto
	}
	
	@Test
	void testEsBasico() {
		assertFalse(joaco.esExperto()); //es basico
	}

	@Test
	void testValidarExternamente() {
		assertFalse(joaco.esExperto());
		joaco.validarExternamente();
		assertTrue(joaco.esExperto()); //pasa a ser experto siempre
		
		assertTrue(fran.esExperto()); //ya es experto validado
		fran.validarExternamente();
		assertTrue(fran.esExperto()); //sigue siendolo
	}

	@Test
	void testRecategorizarBasicoAExperto() {
		assertFalse(joaco.esExperto());
		when(recategorizador.cumpleCondiciones(joaco, 0, 0, 0)).thenReturn(true); //dejo 0 porque mockeo
		joaco.recategorizarSiCorresponde(recategorizador, 0, 0, 0); 
		assertTrue(joaco.esExperto()); //pasa a ser experto
	}
	
	@Test
	void testRecategorizarExpertoValidado() {
		fran.recategorizarSiCorresponde(recategorizador, 0, 0, 0);
		assertTrue(fran.esExperto()); //no cambia porque es validado externo
	}
	
	@Test
	void testRecategorizarExpertoABasico() {
		assertTrue(dami.esExperto());
		when(recategorizador.cumpleCondiciones(dami, 0, 0, 0)).thenReturn(false); //dejo 0 porque mockeo
		dami.recategorizarSiCorresponde(recategorizador, 0, 0, 0); 
		assertFalse(dami.esExperto()); //pasa a ser basico
	}
	
	@Test
	void testUsuarioSeMantieneExperto() {
		assertTrue(dami.esExperto());
		when(recategorizador.cumpleCondiciones(dami, 0, 0, 0)).thenReturn(true); //dejo 0 porque mockeo
		dami.recategorizarSiCorresponde(recategorizador, 0, 0, 0); 
		assertTrue(dami.esExperto()); //sigue siendo experto ya que no incumplio las condiciones
	}
}
