package usuario;

import java.time.LocalDateTime;

public class Opinion {
	private LocalDateTime fecha;
	private Resultado resultado;
	private Usuario usuario;
	
	public Opinion(Usuario usuario, Resultado resultado) {
		this.fecha = LocalDateTime.now();
		this.usuario = usuario;
		this.resultado = resultado;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}
	
	public Resultado getResultado() {
		return this.resultado;
	}
	
	public LocalDateTime getFecha() {
		return this.fecha;
	}
}
