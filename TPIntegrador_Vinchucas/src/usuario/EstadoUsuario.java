package usuario;

import Vinchucas.AplicacionWeb;

public interface EstadoUsuario {
	public boolean esExperto();
	public void cambiarEstado(Usuario usuario);
	public void recategorizarSiCorresponde(AplicacionWeb aplicacionWeb, Usuario usuario,
			int cantEnviosEsperados, int cantRevisionesEsperadas, int cantDiasConsiderados);
}
