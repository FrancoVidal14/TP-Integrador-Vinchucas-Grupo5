package FiltrosTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test;

import appWeb.AplicacionWeb;
import filtroBusqueda.CriterioAND;
import filtroBusqueda.CriterioFechaCreacion;
import filtroBusqueda.CriterioFechaUltimaVotacion;
import filtroBusqueda.CriterioOR;
import filtroBusqueda.CriterioPorMuestraEnVotacion;
import filtroBusqueda.CriterioPorMuestraVerificada;
import filtroBusqueda.CriterioTipoDeInsecto;
import filtroBusqueda.FiltroDeBusqueda;
import muestra.Muestra;
import muestra.IObserverMuestra;
import usuario.Opinion;
import usuario.Resultado;
import usuario.Usuario;
import zonaCobertura.Ubicacion;
import zonaCobertura.ZonaDeCobertura;

class FiltrosTest {

	private AplicacionWeb app;
	private ArrayList<Muestra> muestras;
	private ArrayList<ZonaDeCobertura> zonasDeCobertura;
	private ArrayList<Usuario> usuarios;
	
	private ZonaDeCobertura zonaMock1, zonaMock2;
	private Muestra m1;
	private Muestra m2;
	private Muestra m3;
	private Muestra m4;
	private Muestra m5;
	private CriterioFechaCreacion criterioFecha;
	private CriterioFechaUltimaVotacion criterioUltVotacion;
	private CriterioPorMuestraVerificada criterioMuestraVerif;
	private CriterioTipoDeInsecto criterioInsecto;
	private CriterioPorMuestraEnVotacion criterioVotacion;
	private CriterioAND criterioAnd;
	private CriterioOR criterioOr;
	
	private LocalDateTime fecha1;
	private LocalDateTime fecha2;
	private LocalDateTime fecha4;
	private LocalDateTime fecha5;
	
	private Ubicacion ubi1;
	private Ubicacion ubi2;
	private Ubicacion ubi3;
	private Ubicacion ubi4;
	private Ubicacion ubi5;
	
	private Usuario us1;
	private Usuario us2;
	private Usuario us3;
	private Usuario us4;
	private Usuario us5;
	
	private Opinion op1;
	private Opinion op2;
	private Opinion op3;
	private Opinion op4;
	private Opinion op5;
	
	private Resultado res1;
	private Resultado res2;
	private Resultado res4;
	private Resultado res5;
	
	private FiltroDeBusqueda filtro;
	
	private IObserverMuestra registro;
	
	@BeforeEach
	void setUp() throws Exception {
		
		us1 = new Usuario();
		us2 = new Usuario();
		us3 = new Usuario();
		us4 = new Usuario();
		us5 = new Usuario();
		
		res1 = Resultado.CHINCHE_FOLIADA;
		res2 = Resultado.CHINCHE_FOLIADA;
		res4 = Resultado.NO_DEFINIDO;
		res5 = Resultado.PHTIA_CHINCE;
		
		fecha1 = LocalDateTime.of(2020, 9, 18, 19, 25);
		fecha2 = LocalDateTime.of(2021, 10, 29, 20, 20);
		fecha4 = LocalDateTime.of(2022, 3, 1, 12, 00);
		fecha5 = LocalDateTime.of(2024, 5, 10, 10, 36);
		
		op1 = new Opinion(fecha1, us1, res1);
		op2 = new Opinion(fecha2, us2, res2);
		op3 = new Opinion(fecha5, us3, res4);
		op4 = new Opinion(fecha5, us4, res4);
		op5 = new Opinion(fecha5, us5, res5);
		
		ubi1 = new Ubicacion(20, 60);
		ubi2 = new Ubicacion(45.76, 10.2);
		ubi3 = new Ubicacion(19.2, 5.47);
		ubi4 = new Ubicacion(89.78, 50);
		ubi5 = new Ubicacion(47, 32);
		
		filtro = new FiltroDeBusqueda();
		
        zonaMock1 = mock(ZonaDeCobertura.class);
        zonaMock2 = mock(ZonaDeCobertura.class);
		
		m1 = new Muestra(fecha1, ubi1, us1, op2, registro);
		m2 = new Muestra(fecha1, ubi2, us2, op3, registro);
		m3 = new Muestra(fecha1, ubi3, us3, op1, registro);
		m4 = new Muestra(fecha4, ubi4, us4, op5, registro);
		m5 = new Muestra(fecha5, ubi5, us5, op1, registro);
		
		criterioFecha = new CriterioFechaCreacion(fecha1);
		criterioUltVotacion = new CriterioFechaUltimaVotacion(fecha5);
		criterioMuestraVerif = new CriterioPorMuestraVerificada();
		criterioInsecto = new CriterioTipoDeInsecto(Resultado.CHINCHE_FOLIADA);
		criterioVotacion = new CriterioPorMuestraEnVotacion();
		criterioAnd = new CriterioAND(criterioFecha, criterioVotacion);
		criterioOr = new CriterioOR(criterioFecha, criterioMuestraVerif);
		
		muestras = new ArrayList<>(List.of(m1, m2, m3, m4, m5));
        zonasDeCobertura = new ArrayList<>(List.of(zonaMock1, zonaMock2));
        usuarios = new ArrayList<>(List.of(us1, us2, us3, us4, us5));
        
        app = new AplicacionWeb(muestras, zonasDeCobertura, usuarios, filtro);
	}
	
	@Test
	void testFiltroPorCriterioFechaCreacion() {
		assertEquals(3, app.filtrarMuestras(criterioFecha).size());
		assertTrue(app.filtrarMuestras(criterioFecha).stream().allMatch(m -> m.getFecha().equals(fecha1)));
	}
	
	@Test
	void testFiltroPorCriterioFechaUltimaVotacion() throws Exception {
		assertEquals(3, app.filtrarMuestras(criterioUltVotacion).size()); 
		assertTrue(app.filtrarMuestras(criterioFecha).stream().allMatch(m -> m.buscarFechaUltimaVotacion() == (fecha5)));
	}
	
	@Test
	void testFiltroCriterioPorMuestrasVerificadas() {
		assertEquals(0, app.filtrarMuestras(criterioMuestraVerif).size());
		assertTrue(app.filtrarMuestras(criterioMuestraVerif).stream().allMatch(m -> m.esMuestraVerificada()));
	}
	
	@Test
	void testFiltroCriterioTipoDeInsecto() {
		assertEquals(1, app.filtrarMuestras(criterioInsecto).size());
		assertTrue(app.filtrarMuestras(criterioInsecto).stream().allMatch(m -> m.resultadoActual().equals(Resultado.CHINCHE_FOLIADA)));
	}
	
	@Test
	void testFiltroCriterioPorMuestrasEnVotacion() {
		assertEquals(5, app.filtrarMuestras(criterioVotacion).size());
		assertTrue(app.filtrarMuestras(criterioVotacion).stream().allMatch(m -> !m.esMuestraVerificada()));
	}
	
	@Test
	void testFiltroPorCriterioAND() {
		assertEquals(3, app.filtrarMuestras(criterioAnd).size());
		assertTrue(app.filtrarMuestras(criterioAnd).stream().allMatch(m -> m.getFecha().equals(fecha1) && !m.esMuestraVerificada()));
	}
	
	@Test
	void testFiltroPorCriterioOR() {
		assertEquals(3, app.filtrarMuestras(criterioOr).size());
		assertTrue(app.filtrarMuestras(criterioOr).stream().allMatch(m -> m.getFecha().equals(fecha1) || m.esMuestraVerificada()));
	}
}
