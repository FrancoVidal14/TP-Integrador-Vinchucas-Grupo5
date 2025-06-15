package usuario;

import java.time.LocalDateTime;

public class Opinion {
	private LocalDateTime fecha = LocalDateTime.now(); //se entiende que se guarda la fecha cuando se manda la opinion
	private Resultado resultado;
	private Usuario usuario;
	private boolean esDeExperto;
	
	public Opinion(Usuario usuario, Resultado resultado) {
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
