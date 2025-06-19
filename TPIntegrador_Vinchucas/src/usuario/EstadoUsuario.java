package usuario;

import Vinchucas.Recategorizador;

public interface EstadoUsuario {
	public boolean esExperto();
	public void cambiarEstado(Usuario usuario);
	public void recategorizarSiCorresponde(Recategorizador recategorizador, Usuario usuario,
			int cantEnviosEsperados, int cantRevisionesEsperadas, int cantDiasConsiderados);
}
