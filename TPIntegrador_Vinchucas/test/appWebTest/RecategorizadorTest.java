package appWebTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import appWeb.IDatosUsuario;
import appWeb.Recategorizador;
import muestra.Muestra;
import usuario.Usuario;

class RecategorizadorTest {

	Recategorizador recategorizador;
	IDatosUsuario datos;
	Usuario joaco;
	Usuario fran;

	@BeforeEach
	void setUp() throws Exception {
		datos = mock(IDatosUsuario.class);
		joaco = mock(Usuario.class);
		fran = mock(Usuario.class);
		List<Usuario> usuarios = List.of(joaco, fran);
		when(datos.getUsuarios()).thenReturn(usuarios);
		recategorizador = new Recategorizador(datos);
	}

	@Test
	void testRecategorizarUsuarios() {
		// recategorizamos los usuarios mockeados
		recategorizador.recategorizarUsuarios(10, 20, 30);
		//corroboramos que llamo a get usuarios
		verify(datos, times(1)).getUsuarios();
		// Verificamos que se les haya pedido a los usuarios que se recategoricen con
		// los mismos parámetros
		verify(joaco).recategorizarSiCorresponde(recategorizador, 10, 20, 30);
		verify(fran).recategorizarSiCorresponde(recategorizador, 10, 20, 30);
	}

	@Test
	void testCumpleCondiciones() {
		Muestra muestraPrueba = mock(Muestra.class);

		// Simula que joaco envió una muestra reciente
		when(datos.getMuestrasEnviadasPor(joaco)).thenReturn(List.of(muestraPrueba));

		// Simula que esa muestra fue creada recientemente (ej. hace 30 días)
		when(muestraPrueba.generadaEnUltimos(30)).thenReturn(true);

		// Simula que joaco hizo una revisión exitosa en esa muestra dentro de los 30 días
		when(muestraPrueba.usuarioHizoRevisionExitosa(joaco, 30)).thenReturn(true);
		assertTrue(recategorizador.cumpleCondiciones(joaco, 1, 1, 30));
	}
}
