package muestraTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import muestra.Muestra;
import muestra.IObserverMuestra;
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
	Opinion opinionAlan;
	private IObserverMuestra reg;

	@BeforeEach
	void setUp() throws Exception {
		fecha = LocalDateTime.now().minusDays(7); //hace 1 semana
		unq = new Ubicacion(-34.7063, -58.2778);
        dami = mock(Usuario.class);
        fran = mock(Usuario.class);
        joaco = mock(Usuario.class);
        alan = mock(Usuario.class); //usuario comodin para varios test
        when(dami.esExperto()).thenReturn(false);
        when(alan.esExperto()).thenReturn(false);
        when(fran.esExperto()).thenReturn(true);
        when(joaco.esExperto()).thenReturn(true);
        resultadoOpDami =  Resultado.CHINCHE_FOLIADA;
        resultadoOpJoaco =  Resultado.VINCHUCA_INFESTANS;
        resultadoOpFran =  Resultado.VINCHUCA_INFESTANS;
		opinionDami = new Opinion(fecha, dami, resultadoOpDami);
		opinionJoaco = new Opinion(fecha, joaco, resultadoOpJoaco);
		opinionFran = new Opinion(fecha, fran, resultadoOpFran);
		opinionAlan = mock(Opinion.class);
		when(opinionAlan.getUsuario()).thenReturn(alan);
		reg = mock(IObserverMuestra.class);
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
		IObserverMuestra regTest = mock(IObserverMuestra.class);
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
	void muestraNoPuedeOpinarUsuarioEnviadorNuevamenteTest() throws Exception{
		assertThrows(
	            Exception.class,
	            () -> muestra.procesarOpinion(opinionDami),
	            "El usuario que envio la muestra no puede volver a opinar sobre ella"
	        );
	}
	
	@Test
	void muestraNoPuedeOpinarUsuarioNuevamenteTest() throws Exception{
		muestra.procesarOpinion(opinionJoaco);
		assertThrows(
	            Exception.class,
	            () -> muestra.procesarOpinion(opinionJoaco),
	            "El usuario no puede volver a opinar sobre esta muestra"
	        );
	}
	
	@Test
	void muestraResultadoEmpate() throws Exception{
		assertEquals(resultadoOpDami, muestra.resultadoActual());
		Resultado resultadoAlan = Resultado.IMAGEN_POCO_CLARA;
		when(opinionAlan.esMismoResultado(resultadoAlan)).thenReturn(true);
		muestra.procesarOpinion(opinionAlan);
		assertEquals(Resultado.NO_DEFINIDO, muestra.resultadoActual());
	}
	
	@Test
	void muestraPasaAVotacionExperto() throws Exception{
		assertFalse(muestra.esMuestraVerificada());
		muestra.procesarOpinion(opinionJoaco);
		assertFalse(muestra.esMuestraVerificada()); //sigue sin estar verificada pese a estar en votacion experto
		//ya se encuentra en votacion experto por lo que no puede procesar opiniones de basico
		assertThrows(
	            Exception.class,
	            () -> muestra.procesarOpinion(opinionAlan),
	            "Solo pueden votar expertos en la evaluacion actual de la muestra"
	        );
	}
	
	@Test
	void muestraResultadoExperto() throws Exception{
		assertEquals(resultadoOpDami, muestra.resultadoActual());
		muestra.procesarOpinion(opinionJoaco);
		//deja de importar la opinion de basicos por lo que solo vale la de Joaco que es experto
		assertEquals(resultadoOpJoaco, muestra.resultadoActual());
	}
	
	@Test
	void muestraResultadoExpertoEmpate() throws Exception{
		assertEquals(resultadoOpDami, muestra.resultadoActual());
		muestra.procesarOpinion(opinionJoaco);
		Resultado resultadoAlan = Resultado.IMAGEN_POCO_CLARA;
		when(opinionAlan.esMismoResultado(resultadoAlan)).thenReturn(true);
		when(opinionAlan.fueEmitidaPorExperto()).thenReturn(true);
		muestra.procesarOpinion(opinionAlan);
		assertEquals(Resultado.NO_DEFINIDO, muestra.resultadoActual());
	}
	
	@Test
	void muestraPasaAVotacionVerificada() throws Exception{
		assertFalse(muestra.esMuestraVerificada());
		//opinan dos expertos igual y verifican la muestra
		muestra.procesarOpinion(opinionJoaco);
		muestra.procesarOpinion(opinionFran);
		assertTrue(muestra.esMuestraVerificada());
		assertEquals(resultadoOpJoaco, muestra.resultadoActual()); //como ambos opinan lo mismo, 
		//el resultado de cualquiera de los dos define el resultado de la muestra verificada
		verify(reg, times(1)).recibirMuestraValidada(muestra); //al validarse la muestra, se envia al receptor
		//ya se encuentra validada por lo que no puede procesar opiniones
		assertThrows(
	            Exception.class,
	            () -> muestra.procesarOpinion(opinionAlan),
	            "No se puede opinar en la muestra porque esta verificada"
	        );
	}
}
