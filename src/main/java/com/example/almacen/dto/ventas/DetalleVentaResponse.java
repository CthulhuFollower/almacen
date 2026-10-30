package com.example.almacen.dto.ventas;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Informacion de un producto")
public record DetalleVentaResponse(

        @Schema(description = "ID unico del producto", example = "1")
        Long id,

        @Schema(description = "Nombre del producto", example = "Laptop Gamer")
        String nombre,

        @Schema(description = "Categoria del producto", example = "Electronico")
        String categoria,

        @Schema(description = "Precio del producto", example = "15999.99")
        BigDecimal precio,

        @Schema(description = "Cantidad disponible del producto", example = "300")
        Integer cantidad
) {

}
