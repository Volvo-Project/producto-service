package cl.volvo.productoservice.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaRequestDTO {

    @NotBlank(message = "EL NOMBRE ES OBLIGATORIO")
    @Size(max = 50, message = "EL NOMBRE NO PUEDE SUPERAR LOS 50 CARACTERES")
    private String nombre;

    @NotBlank(message = "EL FILTRO ES OBLIGATORIO")
    @Size(max = 10, message = "EL FILTRO NO PUEDE SUPERAR LOS 10 CARACTERES")
    private String filtro;

}
