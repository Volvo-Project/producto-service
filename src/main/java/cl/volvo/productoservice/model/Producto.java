package cl.volvo.productoservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //id juego . ej warframe -> WFRMMMR-01
    @Column(nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private Integer precio;

    @Column(nullable = false)
    private Integer stock;

    @Column(length = 1000)
    private String descripcion;


    @Column(nullable = false)
    private String imagen;

    //esto es la portada/foto grande q esta en DetalelProducto.jsx, línea 65
    @Column(nullable = false)
    private String imagenDetalle;

    @Column(nullable = false, length = 100)
    private String genero;


    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    private boolean activo = true;


}
