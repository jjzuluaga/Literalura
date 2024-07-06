package com.alurachallenge.demo.principal;

import com.alurachallenge.demo.model.*;
import com.alurachallenge.demo.service.ConsumoApi;
import com.alurachallenge.demo.service.ConvierteDatos;
import com.alurachallenge.demo.repository.AutorRepository;
import com.alurachallenge.demo.repository.LibroRepository;
import org.springframework.beans.factory.support.AutowireCandidateQualifier;

import java.util.List;
import java.util.Scanner;

public class Principal {
    private final String URL_BASE = "https://gutendex.com/books/";
    Scanner scanner = new Scanner(System.in);
    ConsumoApi consumoApi = new ConsumoApi();
    ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private List<Libros> listalibros;
    private List<Autor> listaAutores;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void mostrarMenu() {
        var opcion = -1;

        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo
                    2 - Listar libros almacenados
                    3 - Listar autores almacenados
                    4- Listar autores vivos por determinado año
                                        
                    0 - Salir del programa
                    """;
            System.out.println(menu);
            opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion){

                case 1:
                    buscarLibroTitulo();
                    break;
                case 2:
                    listarLibrosBuscados();
                    break;
                case 3:
                    listarAutoresBuscados();
                    break;
                case 4:
                    listarAutoresVivosAño();
                    break;
                default:
                    System.out.println("Opción no valida");


            }
        }

    }
    public Datos obtenerDatos(){
        System.out.println("Porfa digite el nombre del libro que deseas buscar");
        var titulo = scanner.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE+"?search=" + titulo.replace(" ", "+"));
        System.out.println(json);
        var datos = conversor.obtenerDatos(json,Datos.class);
        return datos;
    }
    public Libros AlmacenarLibro(DatosLibro datosLibro, Autor autor){
        Libros libros = new Libros(datosLibro, autor);
        return libroRepository.save(libros);

    }
    public void buscarLibroTitulo(){
        Datos datos = obtenerDatos();

        if(!datos.resultados().isEmpty()){
            DatosLibro datosLibro = datos.resultados().get(0);
            DatosAutor datosAutor = datosLibro.autor().get(0);
            Libros librosBuscados = libroRepository.findByTituloIgnoreCase(datosLibro.titulo());
            if (librosBuscados != null){
                System.out.println(librosBuscados);
                System.out.println("El libro buscado ya esta registrado, y no se puede volver a incluir");
            }else {
                Autor autorBuscado = autorRepository.findByNombreIgnoreCase(datosAutor.nombre());
                if (autorBuscado == null){
                    Autor autor = new Autor(datosAutor);
                    autorRepository.save(autor);
                    Libros libros = AlmacenarLibro(datosLibro,autor);
                    System.out.println(libros);
                }
            }

        }else {
            System.out.println("El libro buscado no se encontro");

        }
    }
    public void listarLibrosBuscados(){
        listalibros = libroRepository.findAll();
        if (!listalibros.isEmpty()){
            listalibros.forEach(System.out::println);

        }else {
            System.out.println("Ningun libro almacenado");
        }
    }
    public void listarAutoresBuscados(){
        listaAutores = autorRepository.findAll();
        if (!listaAutores.isEmpty()){
            listaAutores.forEach(System.out::println);

        }else {
            System.out.println("Ningun autor almacenado");
        }
    }

    public void listarAutoresVivosAño(){
        System.out.println("Desde que año deseas buscar los autores vivos");
        var año = scanner.nextInt();
        List<Autor> autoresBuscadosVivos = autorRepository.autorVivoPorAño(año);
        if (!autoresBuscadosVivos.isEmpty()){
            autoresBuscadosVivos.forEach(System.out::println);

        }else {
            System.out.println("No hay autores encontrados para ese año");
        }
    }
}
