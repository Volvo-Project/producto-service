package cl.volvo.productoservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import cl.volvo.productoservice.dto.ProductoRequestDTO;
import cl.volvo.productoservice.dto.ProductoResponseDTO;
import cl.volvo.productoservice.exception.RecursoNoEncontradoException;
import cl.volvo.productoservice.model.Producto;
import cl.volvo.productoservice.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepo;
    private final CategoriaService categoriaService;

    private ProductoResponseDTO toDto(Producto producto) {
        return new ProductoResponseDTO(
                producto.getId(),
                producto.getCodigo(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getPrecio() == 0,
                producto.getDescripcion(),
                producto.getImagen(),
                producto.getImagenDetalle(),
                producto.getGenero(),
                producto.getCategoria().getNombre(),
                producto.getCategoria().getFiltro()
        );
    }

    private Producto buscarActivo(Long id) {
        return productoRepo.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto", id));
    }


    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerTodos(){
        return productoRepo.findByActivoTrue().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public ProductoResponseDTO obtenerPorId(Long id){
        return toDto(buscarActivo(id));
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerPorCategoria (Long categoriaId){
        categoriaService.buscarActiva(categoriaId);
        return productoRepo.findByCategoriaIdAndActivoTrue(categoriaId).stream().map(this::toDto).toList();
    }

    private void copiarDatos(Producto producto, ProductoRequestDTO dto){
        producto.setCodigo(dto.getCodigo());
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setDescripcion(dto.getDescripcion());
        producto.setImagen(dto.getImagen());
        producto.setImagenDetalle(dto.getImagenDetalle());
        producto.setGenero(dto.getGenero());

        producto.setCategoria(categoriaService.buscarActiva(dto.getCategoriaId()));

    }

    public ProductoResponseDTO crear(ProductoRequestDTO dto){
        Producto producto = new Producto();
        copiarDatos(producto, dto);
        Producto guardado = productoRepo.save(producto);
        log.info("PRODUCTO CREADO CON ID {}",guardado.getId());
        return toDto(guardado);
    }

    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO dto){
        Producto producto = buscarActivo(id);
        copiarDatos(producto, dto);
        log.info("PRODUCTO {} ACTUALIZADO", id);
        return toDto(productoRepo.save(producto));
    }

    public void eliminar(Long id){
        Producto producto = buscarActivo(id);
        producto.setActivo(false);
        productoRepo.save(producto);
        log.info("PRODUCTO {} DESACTIVADO", id);
    }












}
