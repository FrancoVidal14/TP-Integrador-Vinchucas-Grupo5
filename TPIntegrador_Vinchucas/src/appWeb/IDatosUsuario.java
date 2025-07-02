package appWeb;

import java.util.List;
import java.util.Set;

import muestra.Muestra;
import usuario.Usuario;

public interface IDatosUsuario {
	Set<Usuario> getUsuarios();
    List<Muestra> getMuestrasEnviadasPor(Usuario usuario);
}
