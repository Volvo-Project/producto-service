package cl.volvo.productoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import cl.volvo.productoservice.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria,Long> {

    List<Categoria> findByActivoTrue();

    Optional<Categoria> findByIdAndActivoTrue(Long id);



}

