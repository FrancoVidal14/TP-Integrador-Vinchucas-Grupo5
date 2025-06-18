package usuario;

import java.time.LocalDateTime;

public class Opinion {
	private LocalDateTime fecha;
	private Resultado resultado;
	private Usuario usuario;
	private boolean esDeExperto;
	
	public Opinion(LocalDateTime fecha, Usuario usuario, Resultado resultado) {
		this.usuario = usuario;
		this.resultado = resultado;
		this.esDeExperto = usuario.esExperto();
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
	
	public boolean esDeExperto() {
		return esDeExperto;
	}
}
