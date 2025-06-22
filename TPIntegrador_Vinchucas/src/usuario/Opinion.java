package usuario;

import java.time.LocalDateTime;

public class Opinion{
	private LocalDateTime fecha;
	private Resultado resultado;
	private Usuario usuario;
	private boolean esDeExperto;
	
	public Opinion(LocalDateTime fecha, Usuario usuario, Resultado resultado) {
		this.fecha = fecha;
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
	
	public boolean fueEmitidaPorExperto() {
		return esDeExperto;
	}
	
	public boolean esUsuarioOpinador(Usuario usuario) {
		return this.usuario.equals(usuario);
	}
	
	public boolean esMismoResultado(Resultado resultado) {
		return this.resultado.equals(resultado);
	}
	
	public boolean generadaEnUltimos(int ultimosDias) {
		LocalDateTime fechaComienzoValidez = LocalDateTime.now().minusDays(ultimosDias);
		return this.fecha.isAfter(fechaComienzoValidez);
	}
}
