package com.alurachallenge.demo.repository;
import com.alurachallenge.demo.model.Autor;
import com.alurachallenge.demo.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface AutorRepository extends JpaRepository<Autor,Long> {
    Autor findByNombreIgnoreCase (String nombre);

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :fecha AND a.fechaMuerte >= :fecha")
    List<Autor> autorVivoPorAño(int fecha);
}
