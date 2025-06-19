package Vinchucas;

import java.util.List;

import muestra.Muestra;
import usuario.Opinion;
import usuario.Usuario;

public interface IDatosUsuario {
    List<Usuario> getUsuarios();
    List<Opinion> getOpinionesDe(Usuario usuario); //no usada actualmente pero podria servir en un futuro
    List<Muestra> getMuestrasEnviadasPor(Usuario usuario);
}
