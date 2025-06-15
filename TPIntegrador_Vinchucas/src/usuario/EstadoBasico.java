package usuario;

public class EstadoBasico implements EstadoUsuario {

	@Override
	public boolean esExperto() {
		return false;
	}

	@Override
	public void cambiarEstado(Usuario usuario) {
		usuario.setEstado(new EstadoExperto());
	}

}
