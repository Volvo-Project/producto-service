package cl.volvo.productoservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequestDTO {

    @NotBlank(message = "EL CÓDIGO ES OBLIGATORIO")
    @Size(max = 20, message = "EL CÓDIGO NO PUEDE SUPERAR LOS 20 CARACTERES")
    private String codigo;

    @NotBlank(message = "EL NOMBRE ES OBLIGATORIO")
    @Size(max = 100, message = "EL NOMBRE NO PUEDE SUPERAR LOS 100 CARACTERES")
    private String nombre;

    @NotNull(message = "EL PRECIO ES OBLIGATORIO")
    @PositiveOrZero(message = "EL PRECIO NO PUEDE SER NEGATIVO")
    private Integer precio;

    @NotNull(message = "EL STOCK ES OBLIGATORIO")
    @PositiveOrZero(message = "EL STOCK NO PUEDE SER NEGATIVO")
    private Integer stock;

    @Size(max = 1000, message = "LA DESCRIPCIÓN NO PUEDE SUPERAR LOS 1000 CARACTERES")
    private String descripcion;

    @NotBlank(message = "LA IMAGEN ES OBLIGATORIA")
    private String imagen;

    @NotBlank(message = "LA IMAGEN DEL DETALLE ES OBLIGATORIA")
    private String imagenDetalle;

    @NotBlank(message = "EL GÉNERO ES OBLIGATORIO")
    @Size(max = 100, message = "EL GÉNERO NO PUEDE SUPERAR LOS 100 CARACTERES")
    private String genero;

    @NotNull(message = "LA CATEGORÍA ES OBLIGATORIA")
    @Positive(message = "EL ID DE LA CATEGORÍA DEBE SER MAYOR A CERO")
    private Long categoriaId;

}
