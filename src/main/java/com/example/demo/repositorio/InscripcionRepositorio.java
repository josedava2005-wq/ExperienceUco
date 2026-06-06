package com.example.demo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entidad.Evento;
import com.example.demo.entidad.Inscripcion;
import com.example.demo.entidad.Usuario;

@Repository
public interface InscripcionRepositorio extends JpaRepository<Inscripcion, Long> {

    boolean existsByUsuarioAndEvento(Usuario usuario, Evento evento);

    List<Inscripcion> findByUsuario(Usuario usuario);

    List<Inscripcion> findByEvento(Evento evento);

    long countByEstado(String estado);
}
