package cl.volvo.productoservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {

    private Long id;
    private String codigo;
    private String nombre;
    private Integer precio;
    private Integer stock;
    //el esgratis podría sacarse con el puro precio solamente,
    // con un ifelse __tengo q revisar el front
    private boolean esGratis;
    private String descripcion;
    private String imagen;
    private String imagenDetalle;
    private String genero;
    private String categoria;
    private String filtro;




}
