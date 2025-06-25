package usuarioTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import usuario.Opinion;
import usuario.Resultado;
import usuario.Usuario;

class OpinionTest {
	
	Usuario joaco;
	Usuario fran;
	Resultado resultadoJoaco;
	Resultado resultadoFran;
	LocalDateTime fechaOpinionJoaco;
	LocalDateTime fechaOpinionFran;
	Opinion opinionJoaco;
	Opinion opinionFran;

	@BeforeEach
	void setUp() throws Exception {
		joaco = mock(Usuario.class); //va a ser usuario basico
		fran = mock(Usuario.class); //va a ser usuario experto
		when(joaco.esExperto()).thenReturn(false);
		when(fran.esExperto()).thenReturn(true);
		resultadoJoaco = Resultado.CHINCHE_FOLIADA;
		resultadoFran = Resultado.IMAGEN_POCO_CLARA;
		fechaOpinionJoaco = LocalDateTime.now().minusDays(2);
		fechaOpinionFran = LocalDateTime.now().minusDays(5);
		opinionJoaco = new Opinion(fechaOpinionJoaco, joaco,resultadoJoaco);
		opinionFran = new Opinion(fechaOpinionFran, fran, resultadoFran);
	}

	@Test
	void testGetUsuario() {
		assertEquals(joaco, opinionJoaco.getUsuario());
		assertEquals(fran, opinionFran.getUsuario());
	}

	@Test
	void testGetResultado() {
		assertEquals(resultadoJoaco, opinionJoaco.getResultado());
		assertEquals(resultadoFran, opinionFran.getResultado());
	}

	@Test
	void testGetFecha() {
		assertEquals(fechaOpinionJoaco, opinionJoaco.getFecha());
		assertEquals(fechaOpinionFran, opinionFran.getFecha());
	}

	@Test
	void testFueEmitidaPorExperto() {
		assertTrue(opinionFran.fueEmitidaPorExperto());
		assertFalse(opinionJoaco.fueEmitidaPorExperto()); //es basico
	}

	@Test
	void testEsUsuarioOpinador() {
		assertTrue(opinionFran.esUsuarioOpinador(fran));
		assertTrue(opinionJoaco.esUsuarioOpinador(joaco));
	}

	@Test
	void testEsMismoResultado() {
		assertTrue(opinionJoaco.esMismoResultado(resultadoJoaco));
	}

	@Test
	void testGeneradaEnUltimos() {
		assertTrue(opinionJoaco.generadaEnUltimos(3));
		assertTrue(opinionFran.generadaEnUltimos(7));
	}

}
