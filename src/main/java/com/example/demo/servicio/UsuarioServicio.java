package com.example.demo.servicio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.entidad.Usuario;
import com.example.demo.excepcion.CorreoDuplicadoException;
import com.example.demo.excepcion.RecursoNoEncontradoException;
import com.example.demo.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepositorio.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + id));
    }

    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepositorio.findByCorreo(correo)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con correo: " + correo));
    }

    public Usuario crear(UsuarioDTO dto) {
        if (usuarioRepositorio.existsByCorreo(dto.correo())) {
            throw new CorreoDuplicadoException("Ya existe un usuario registrado con el correo: " + dto.correo());
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.nombre());
        usuario.setCorreo(dto.correo());
        usuario.setTelefono(dto.telefono());
        return usuarioRepositorio.save(usuario);
    }

    public Usuario actualizar(Long id, UsuarioDTO dto) {
        Usuario usuario = buscarPorId(id);
        boolean correoTomado = usuarioRepositorio.existsByCorreo(dto.correo())
                && !usuario.getCorreo().equalsIgnoreCase(dto.correo());
        if (correoTomado) {
            throw new CorreoDuplicadoException("Ya existe otro usuario registrado con el correo: " + dto.correo());
        }
        usuario.setNombre(dto.nombre());
        usuario.setCorreo(dto.correo());
        usuario.setTelefono(dto.telefono());
        return usuarioRepositorio.save(usuario);
    }

    public void eliminar(Long id) {
        buscarPorId(id);
        usuarioRepositorio.deleteById(id);
    }
}
