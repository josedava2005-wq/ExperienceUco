package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entidad.Inscripcion;

@Repository
public interface InscripcionRepositorio extends JpaRepository<Inscripcion, Long>{

}