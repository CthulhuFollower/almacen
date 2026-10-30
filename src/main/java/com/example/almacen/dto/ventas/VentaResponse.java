package com.example.almacen.dto.ventas;

import com.example.almacen.dto.sucursales.SucursalResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

public record VentaResponse(

        @Schema(description = "Identificador de la venta", example = "1")
        Long id,

        @Schema(description = "Fecha de la venta", example = "12/12/2020")
        String fecha,

        @Schema(description = "Estado de la venta", example = "Registrada")
        String estado,

        @Schema(description = "Sucursal donde se realizo la venta")
        SucursalResponse sucursal,

        @Schema(description = "Lista de productos de la venta")
        List<DetalleVentaResponse> detalles,

        @Schema(description = "Total de la venta", example = "1500.00")
        BigDecimal total
) {
}
