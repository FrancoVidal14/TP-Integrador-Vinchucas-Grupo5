package CriterioTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import FiltrosDeBusqueda.CriterioAND;
import FiltrosDeBusqueda.CriterioFechaCreacion;
import FiltrosDeBusqueda.CriterioFechaUltimaVotacion;
import FiltrosDeBusqueda.CriterioNivelDeVerificacion;
import FiltrosDeBusqueda.CriterioOR;
import FiltrosDeBusqueda.CriterioTipoDeInsecto;
import Vinchucas.Ubicacion;
import muestra.Muestra;
import muestra.MuestraVerificada;
import usuario.Opinion;
import usuario.Resultado;
import usuario.UsuarioGeneral;
import usuario.UsuarioValidado;

class CriterioTest {
	
	private Muestra m1;
	private Muestra m2;
	private Muestra m3;
	private Muestra m4;
	private Muestra m5;
	
	private CriterioFechaCreacion criterioFecha;
	private CriterioFechaUltimaVotacion criterioUltVotacion;
	private CriterioNivelDeVerificacion criterioNivelVerif;
	private CriterioTipoDeInsecto criterioInsecto;
	private CriterioAND criterioAnd;
	private CriterioOR criterioOr;
	
	private LocalDateTime fecha1;
	private LocalDateTime fecha2;
	private LocalDateTime fecha3;
	private LocalDateTime fecha4;
	private LocalDateTime fecha5;
	
	private Ubicacion ubi1;
	private Ubicacion ubi2;
	private Ubicacion ubi3;
	private Ubicacion ubi4;
	private Ubicacion ubi5;
	
	private UsuarioValidado us1;
	private UsuarioGeneral us2;
	private UsuarioValidado us3;
	private UsuarioValidado us4;
	private UsuarioValidado us5;
	
	private Opinion op1;
	private Opinion op2;
	private Opinion op3;
	private Opinion op4;
	private Opinion op5;
	
	private Resultado res1;
	private Resultado res2;
	private Resultado res3;
	private Resultado res4;
	private Resultado res5;
	
	@BeforeEach
	void setUp() throws Exception {
		
		fecha1 = LocalDateTime.of(2020, 9, 18, 19, 25);
		fecha2 = LocalDateTime.of(2021, 10, 29, 20, 20);
		fecha3 = LocalDateTime.of(2022, 6, 8, 8, 15);
		fecha4 = LocalDateTime.of(2022, 3, 1, 12, 00);
		fecha5 = LocalDateTime.of(2024, 5, 10, 10, 36);
		
		criterioFecha = new CriterioFechaCreacion(fecha1);
		criterioUltVotacion = new CriterioFechaUltimaVotacion(fecha5);
		criterioNivelVerif = new CriterioNivelDeVerificacion(new MuestraVerificada());
		criterioInsecto = new CriterioTipoDeInsecto(Resultado.CHINCHE_FOLIADA);
		criterioAnd = new CriterioAND(criterioFecha, criterioUltVotacion);
		criterioOr = new CriterioOR(criterioFecha, criterioNivelVerif);
		
		us1 = new UsuarioValidado();
		us2 = new UsuarioGeneral();
		us3 = new UsuarioValidado();
		us4 = new UsuarioValidado();
		us5 = new UsuarioValidado();
		
		res1 = Resultado.CHINCHE_FOLIADA;
		res2 = Resultado.CHINCHE_FOLIADA;
		res3 = Resultado.IMAGEN_POCO_CLARA;
		res4 = Resultado.NO_DEFINIDO;
		res5 = Resultado.PHTIA_CHINCE;
		
		op1 = new Opinion(us1, res1);
		op2 = new Opinion(us2, res2);
		op3 = new Opinion(us3, res3);
		op4 = new Opinion(us4, res3);
		op5 = new Opinion(us5, res5);
		
		m1 = new Muestra(fecha1, ubi1, us1, op2);
		m2 = new Muestra(fecha2, ubi2, us2, op3);
		m3 = new Muestra(fecha3, ubi3, us3, op4);
		m4 = new Muestra(fecha4, ubi4, us4, op5);
		m5 = new Muestra(fecha5, ubi5, us5, op1);
		
	}	

	@Test
	void testCumpleCriterioMuestraFechaCreacion() {
		assertTrue(criterioFecha.cumpleMuestra(m1));
	}
	
	@Test
	void testNoCumpleCriterioMuestraFechaCreacion() {
		assertFalse(criterioFecha.cumpleMuestra(m2));
	}
	
	@Test
	void testCumpleCriterioMuestraFechaUltimaVotacion() throws Exception {
		m4.procesarOpinion(op1);
		assertTrue(criterioUltVotacion.cumpleMuestra(m4));//falta hacer
	}
	
	@Test
	void testNoCumpleCriterioMuestraFechaUltimaVotacion() {
		assertFalse(criterioUltVotacion.cumpleMuestra(m3)); //falta
	}
	
	@Test
	void testCumpleCriterioMuestraNivelVerificacion() throws Exception {
		m2.procesarOpinion(op4); 
		assertTrue(criterioNivelVerif.cumpleMuestra(m2)); //falta hacer
	}
	
	@Test
	void testNoCumpleCriterioMuestraNivelVerificacion() {
		assertFalse(criterioNivelVerif.cumpleMuestra(m2)); //falta hacer
	}
	
	@Test
	void testCumpleCriterioTipoDeInsecto() {
		Resultado resultadoEsperado = Resultado.CHINCHE_FOLIADA;
		//falta el getResultado en muestra
	}
	
	@Test
	void testNoCumpleCriterioTipoDeInsecto() {
		Resultado resultadoEsperado = Resultado.CHINCHE_FOLIADA;
		//falta el getResultado en muestra
	}
	
	@Test
	void testCumpleCriterioAND() {
		assertTrue(criterioAnd.cumpleMuestra(m1)); //falta
	}
	
	@Test
	void testCumpleCriterioOR() {
		assertTrue(criterioAnd.cumpleMuestra(m1)); //falta
	}
	
	@Test
	void testNoCumpleCriterioAND() {
		assertTrue(criterioOr.cumpleMuestra(m1)); //falta
	}
	
	@Test
	void testNoCumpleCriterioOR() {
		assertTrue(criterioOr.cumpleMuestra(m1)); //falta
	}
}
