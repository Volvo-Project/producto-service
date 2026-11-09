package cl.volvo.productoservice.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import cl.volvo.productoservice.dto.CategoriaRequestDTO;
import cl.volvo.productoservice.dto.CategoriaResponseDTO;
import cl.volvo.productoservice.exception.RecursoNoEncontradoException;
import cl.volvo.productoservice.model.Categoria;
import cl.volvo.productoservice.repository.CategoriaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepo;

    private CategoriaResponseDTO toDto(Categoria categoria) {
        return new CategoriaResponseDTO(categoria.getId(), categoria.getNombre(), categoria.getFiltro());
    }

    public Categoria buscarActiva(Long id) {
        return categoriaRepo.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoria", id));
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> obtenerTodas() {
        return categoriaRepo.findByActivoTrue().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDTO obtenerPorId(Long id) {
        return toDto(buscarActiva(id));
    }

    public CategoriaResponseDTO crear(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setFiltro(dto.getFiltro());
        Categoria guardada = categoriaRepo.save(categoria);
        log.info("CATEGORIA CREADA CON ID {}", guardada.getId());
        return toDto(guardada);
    }

    public CategoriaResponseDTO actualizar(Long id, CategoriaRequestDTO dto) {
        Categoria categoria = buscarActiva(id);
        categoria.setNombre(dto.getNombre());
        categoria.setFiltro(dto.getFiltro());
        log.info("CATEGORIA {} ACTUALIZADA", id);
        return toDto(categoriaRepo.save(categoria));
    }

    public void eliminar(Long id) {
        Categoria categoria = buscarActiva(id);
        categoria.setActivo(false);
        categoriaRepo.save(categoria);
        log.info("CATEGORIA {} DESACTIVADA", id);
    }


}
