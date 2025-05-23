package usuario;

import java.time.LocalDateTime;

public class Opinion {
	private LocalDateTime fecha;
	private Resultado resultado;
	private NivelConocimiento nivelUsuario;
	private Usuario usuario;
	
	public Opinion(Usuario usuario, Resultado resultado) {
		this.fecha = LocalDateTime.now();
		this.usuario = usuario;
		this.resultado = resultado;
		this.nivelUsuario = usuario.getNivelConocimiento();
	}
	
}
