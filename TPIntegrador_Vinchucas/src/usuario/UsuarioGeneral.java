package usuario;

public class UsuarioGeneral extends Usuario {

	public UsuarioGeneral() {
		this.estado = new EstadoBasico();
	}
	
	@Override
	protected void cambiarNivelConocimiento() {
		this.estado.cambiarEstado(this);
	}
	
	protected void setEstado(EstadoUsuario estado) {
		this.estado = estado;
	}
}
