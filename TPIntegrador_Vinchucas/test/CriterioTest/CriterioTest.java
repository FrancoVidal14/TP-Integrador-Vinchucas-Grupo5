package CriterioTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import filtroBusqueda.CriterioAND;
import filtroBusqueda.CriterioFechaCreacion;
import filtroBusqueda.CriterioFechaUltimaVotacion;
import filtroBusqueda.CriterioOR;
import filtroBusqueda.CriterioPorMuestraEnVotacion;
import filtroBusqueda.CriterioPorMuestraVerificada;
import filtroBusqueda.CriterioTipoDeInsecto;
import muestra.Muestra;
import usuario.Opinion;
import usuario.Resultado;
import usuario.Usuario;
import zonaCobertura.Ubicacion;

class CriterioTest {
	
	private Muestra m1;
	private Muestra m2;
	private Muestra m4;
	
	private CriterioFechaCreacion criterioFecha;
	private CriterioFechaUltimaVotacion criterioUltVotacion;
	private CriterioPorMuestraVerificada criterioMuestraVerif;
	private CriterioTipoDeInsecto criterioInsecto;
	private CriterioPorMuestraEnVotacion criterioVotacion;
	private CriterioAND criterioAnd;
	private CriterioOR criterioOr;
	
	private LocalDateTime fecha1;
	private LocalDateTime fecha2;
	private LocalDateTime fecha3;
	private LocalDateTime fecha4;
	private LocalDateTime fecha5;
	
	private Ubicacion ubi1;
	private Ubicacion ubi2;
	private Ubicacion ubi4;
	
	private Usuario us1;
	private Usuario us2;
	private Usuario us3;
	private Usuario us4;
	private Usuario us5;
	
	private Opinion op1;
	private Opinion op2;
	private Opinion op3;
	private Opinion op5;
	
	private Resultado res2;
	private Resultado res3;
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
		criterioMuestraVerif = new CriterioPorMuestraVerificada();
		criterioInsecto = new CriterioTipoDeInsecto(Resultado.CHINCHE_FOLIADA);
		criterioVotacion = new CriterioPorMuestraEnVotacion();
		criterioAnd = new CriterioAND(criterioFecha, criterioUltVotacion);
		criterioOr = new CriterioOR(criterioFecha, criterioMuestraVerif);
		
		us1 = mock(Usuario.class);
		us2 = mock(Usuario.class);
		us3 = mock(Usuario.class);
		us4 = mock(Usuario.class);
		us5 = mock(Usuario.class);
				
		res2 = Resultado.CHINCHE_FOLIADA;
		res3 = Resultado.IMAGEN_POCO_CLARA;
		res5 = Resultado.PHTIA_CHINCE;
		
		op1 = new Opinion(fecha2, us1, res3);
		op2 = new Opinion(fecha3, us2, res2);
		op3 = new Opinion(fecha1, us3, res3);
		op5 = new Opinion(fecha5, us5, res5);
		
		m1 = new Muestra(fecha1, ubi1, us1, op2);
		m2 = new Muestra(fecha2, ubi2, us2, op3);
		m4 = new Muestra(fecha4, ubi4, us4, op2);
		
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
		m4.procesarOpinion(op5);
		assertTrue(criterioUltVotacion.cumpleMuestra(m4));
	}
	
	@Test
	void testNoCumpleCriterioMuestraFechaUltimaVotacion() throws Exception {
		m4.procesarOpinion(op1);
		assertFalse(criterioUltVotacion.cumpleMuestra(m4));
	}
	
	@Test
	void testCumpleCriterioMuestraVerificada() throws Exception {
		m2.procesarOpinion(op1);
		assertTrue(criterioMuestraVerif.cumpleMuestra(m2));
	}
	
	@Test
	void testNoCumpleCriterioMuestraVerificada() {
		assertFalse(criterioMuestraVerif.cumpleMuestra(m2));
	}
	
	@Test
	void testCumpleCriterioTipoDeInsecto() {
		assertTrue(criterioInsecto.cumpleMuestra(m1));
	}
	
	@Test
	void testNoCumpleCriterioTipoDeInsecto() {
		assertFalse(criterioInsecto.cumpleMuestra(m2));
	}
	
	@Test
	void testCumpleCriterioPorMuestraEnVotacion() {
		assertTrue(criterioVotacion.cumpleMuestra(m1));
	}
	
	@Test 
	void testNoCumpleCriterioPorMuestraVotacion() throws Exception {
		m2.procesarOpinion(op1);
		assertFalse(criterioVotacion.cumpleMuestra(m2));
	}
	
	@Test
	void testCumpleCriterioAND() throws Exception {
		m1.procesarOpinion(op5);
		assertTrue(criterioAnd.cumpleMuestra(m1));
	}
	
	@Test
	void testCumpleCriterioOR() throws Exception {
		assertTrue(criterioOr.cumpleMuestra(m1));
	}
	
	@Test
	void testNoCumpleCriterioAND() {
		assertFalse(criterioAnd.cumpleMuestra(m1));
	}
	
	@Test
	void testNoCumpleCriterioOR() {
		assertFalse(criterioOr.cumpleMuestra(m2));
	}
}
