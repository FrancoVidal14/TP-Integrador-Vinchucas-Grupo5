package Vinchucas;

import java.util.List;

import muestra.Muestra;
import usuario.Usuario;

public interface IDatosUsuario {
    List<Usuario> getUsuarios();
    List<Muestra> getMuestrasEnviadasPor(Usuario usuario);
}
