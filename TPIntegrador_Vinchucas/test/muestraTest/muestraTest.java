package muestraTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import muestra.Muestra;
import muestra.RegistroDeValidaciones;
import usuario.Opinion;
import usuario.Resultado;
import usuario.Usuario;

import zonaCobertura.Ubicacion;

class muestraTest {
	
	LocalDateTime fecha;
	Ubicacion unq;
	Usuario dami;
	Usuario joaco;
	Usuario fran;
	Usuario alan;
	Resultado resultadoOpDami;
	Opinion opinionDami;
	Muestra muestra;
	Resultado resultadoOpJoaco;
	Resultado resultadoOpFran;
	Opinion opinionJoaco;
	Opinion opinionFran;
	private RegistroDeValidaciones reg;

	@BeforeEach
	void setUp() throws Exception {
		fecha = LocalDateTime.now().minusDays(7); //hace 1 semana
		unq = new Ubicacion(-34.7063, -58.2778);
        dami = mock(Usuario.class);
        fran = mock(Usuario.class);
        joaco = mock(Usuario.class);
        alan = mock(Usuario.class);
        when(dami.esExperto()).thenReturn(false);
        when(fran.esExperto()).thenReturn(true);
        when(joaco.esExperto()).thenReturn(true);
        when(alan.esExperto()).thenReturn(false); //opina cuando se verifique la muestra
        resultadoOpDami =  Resultado.CHINCHE_FOLIADA;
        resultadoOpJoaco =  Resultado.VINCHUCA_INFESTANS;
        resultadoOpFran =  Resultado.VINCHUCA_INFESTANS;
		opinionDami = new Opinion(fecha, dami, resultadoOpDami);
		opinionJoaco = new Opinion(fecha, joaco, resultadoOpJoaco);
		opinionFran = new Opinion(fecha, fran, resultadoOpFran);
		reg = mock(RegistroDeValidaciones.class);
		muestra = new Muestra(fecha, unq, dami, opinionDami, reg);
	}

	@Test
	void getUsuarioTest() {
	    assertEquals(dami, muestra.getUsuario());
	}

	@Test
	void getFechaTest() {
	    assertEquals(fecha, muestra.getFecha());
	}
	
	@Test
	void getUbicacionTest() {
	    assertEquals(unq, muestra.getUbicacion());
	}
	
	@Test
	void getReceptorTest() {
	    assertEquals(reg, muestra.getRegistro());
	}
	
	@Test
	void setReceptorTest() {
		RegistroDeValidaciones regTest = mock(RegistroDeValidaciones.class);
		muestra.setReceptor(regTest);
	    assertEquals(regTest, muestra.getRegistro());
	}
	
	@Test
	void esUsuarioEnviadorValidoTest() {
		assertTrue(muestra.esUsuarioEnviador(dami));
	}
	
	@Test
	void generadaEnUltimosValidoTest() {
		int ultimosDias = 8; //hace 8 dias
		assertTrue(muestra.generadaEnUltimos(ultimosDias));
	}
	
	@Test
	void opinionInicialProcesadaTest() {
	    assertEquals(1, muestra.getOpiniones().size());
	    assertEquals(dami, muestra.getOpiniones().get(0).getUsuario());
	}
	
	@Test
	void resultadoActualValido() {
		assertEquals(resultadoOpDami, muestra.resultadoActual());
	}
	
	@Test
	void getOpinionesDeValido() {
		assertEquals(1, muestra.getOpinionesDe(dami).size());
	}
	
	@Test
	void buscarFechaUltimaVotacionValido() {
		assertEquals(fecha, muestra.buscarFechaUltimaVotacion());
	}
	
	@Test
	void usuarioHizoRevisionExitosaValido() {
		int ultimosDias = 8;
		assertTrue(muestra.usuarioHizoRevisionExitosa(dami, ultimosDias));
	}
	
	@Test
	void muestraTestProcesarOpinion(){
		
	}
}
