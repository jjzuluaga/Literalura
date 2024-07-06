package com.alurachallenge.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")

public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    @Column(unique = true)

    private String titulo;
    private int descargas;

    @ManyToOne()

    private Autor autor;

    public Libros(){

    }
    public Libros(DatosLibro datosLibro, Autor autor){
        this.titulo = datosLibro.titulo();
        this.descargas = datosLibro.descargas();
        this.autor = autor;

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDescargas() {
        return descargas;
    }

    public void setDescargas(int descargas) {
        this.descargas = descargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Libros{" +
                "titulo='" + titulo + '\'' +
                ", descargas=" + descargas +
                '}';
    }
}
