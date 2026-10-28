package com.example.almacen.dto.productos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Datos necesarios para crear o actualizar un producto")
public record ProductoRequest (

        @Schema(description = "Nombre del producto", example = "Laptop Gamer")
        @NotBlank(message = "EL nombre es requerido")
        @Size(min = 5, max = 30, message = "El nombre debe tener entre 5 y 30 caracteres")
        String nombre,

        @Schema(
                description = "Categoria del producto",
                example = "Electronico",
                allowableValues = {
                        "ALIMENTO",
                        "HIGIENE",
                        "JUGUETE",
                        "ELECTRONICA",
                        "ROPA",
                        "ACCESORIO",
                        "FARMACIA"
                }
        )
        @NotBlank(message = "EL nombre es requerido")
        String categoria,

        @Schema(description = "Precio del producto", example = "15999.99")
        @NotNull(message = "El precio es requerido")
        @Positive(message = "El precio debe ser positivo")
        BigDecimal precio,

        @Schema(description = "Cantidad disponible del producto", example = "300")
        @NotNull(message = "La cantidad es requeriao")
        @Positive(message = "La cantidad debe ser positiva")
        Integer cantidad
){

}
