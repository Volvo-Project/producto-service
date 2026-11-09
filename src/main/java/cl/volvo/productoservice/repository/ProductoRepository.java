package cl.volvo.productoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import cl.volvo.productoservice.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByActivoTrue();

    Optional<Producto> findByIdAndActivoTrue(Long id);

    Optional<Producto> findByCodigoAndActivoTrue(String codigo);

    List<Producto> findByCategoriaIdAndActivoTrue(Long categoriaId);



}
