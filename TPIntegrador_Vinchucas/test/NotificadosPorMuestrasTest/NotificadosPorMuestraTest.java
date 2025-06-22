package NotificadosPorMuestrasTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import appWeb.AplicacionWeb;
import filtroBusqueda.FiltroDeBusqueda;
import muestra.Muestra;
import usuario.Opinion;
import usuario.Resultado;
import usuario.Usuario;
import zonaCobertura.Ubicacion;

class NotificadosPorMuestraTest {
	
	private LocalDateTime fecha1;
	private LocalDateTime fecha2;
	private LocalDateTime fecha3;
	private LocalDateTime fecha4;
	private LocalDateTime fecha5;
	private LocalDateTime fecha6;
	
	private Ubicacion ubi1;
	private Ubicacion ubi2;
	private Ubicacion ubi3;
	private Ubicacion ubi4;
	private Ubicacion ubi5;
	private Ubicacion ubi6;
	
	private Muestra m1;
	private Muestra m2;
	private Muestra m3;
	private Muestra m4;
	private Muestra m5;
	private Muestra m6;
	
	private AplicacionWeb app;
	
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
		
		app = new AplicacionWeb(filtro);
		
		m1 = new Muestra(fecha1, ubi1, us1, op2);
		m2 = new Muestra(fecha1, ubi2, us2, op3);
		m3 = new Muestra(fecha1, ubi3, us3, op1);
		m4 = new Muestra(fecha4, ubi4, us4, op5);
		m5 = new Muestra(fecha5, ubi5, us5, op1);
		
		app.recibirMuestra(m1, us1, op3);
		app.recibirMuestra(m2, us2, op4);
		app.recibirMuestra(m3, us3, op5);
		app.recibirMuestra(m4, us4, op1);
		app.recibirMuestra(m5, us5, op2);
		
	}
	
	@Test
	void test() {
		
	}

}
