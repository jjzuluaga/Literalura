package com.alurachallenge.demo.repository;

import com.alurachallenge.demo.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libros,Long> {

    Libros findByTituloIgnoreCase (String titulo);
}

