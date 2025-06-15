package usuario;

public class EstadoExperto implements EstadoUsuario {

	@Override
	public boolean esExperto() {
		return true;
	}

	@Override
	public void cambiarEstado(Usuario usuario) {
		usuario.setEstado(new EstadoBasico());
	}

}
