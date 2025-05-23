package usuario;

public class UsuarioGeneral extends Usuario {

	public UsuarioGeneral() {
		super.setNivelConocimiento(NivelConocimiento.BASICO);
	}
	
//	@Override
//	protected void cambiarNivelConocimiento() {
//		NivelConocimiento nivelActual = super.getNivelConocimiento();
//		super.setNivelConocimiento(nivelActual.cambiarNivel(nivelActual));
//	}
}
