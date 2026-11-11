package cl.volvo.productoservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import cl.volvo.productoservice.dto.ProductoRequestDTO;
import cl.volvo.productoservice.dto.ProductoResponseDTO;
import cl.volvo.productoservice.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "CRUD DE PRODUCTOS (JUEGOS) DE LA TIENDA")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    @Operation(summary = "Listar todos los productos activos")
    public ResponseEntity<List<ProductoResponseDTO>> listar() {
        log.info("GET /api/v1/productos - LISTAR TODOS");
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar un producto por id")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        log.info("GET /api/v1/productos/{} - BUSCAR POR ID", id);
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @GetMapping("/categoria/{categoriaId}")
    @Operation(summary = "Listar los productos de una categoria")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerPorCategoria (@PathVariable Long categoriaId){
        log.info("GET /api/v1/productos/categoria/{} - BUSCAR POR CATEGORIA", categoriaId);
        return ResponseEntity.ok(productoService.obtenerPorCategoria(categoriaId));
    }


    @PostMapping
    @Operation(summary = "Crear un producto")
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoRequestDTO dto){
        log.info("POST /api/v1/productos - CREAR PRODUCTO {}", dto.getCodigo());
        return ResponseEntity.status(201).body(productoService.crear(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoRequestDTO dto){
        log.info("PUT /api/v1/productos/{} - ACTUALIZAR PRODUCTO", id);
        return ResponseEntity.ok(productoService.actualizar(id,dto));

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desactivar un producto (borrado lógico)")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/v1/productos/{} - ELIMINAR PRODUCTO", id);
        productoService.eliminar(id);
        return ResponseEntity.ok(Map.of("MENSAJE", "PRODUCTO ELIMINADO CORRECTAMENTE"));
    }
}
