package com.example.almacen.dto.reportes;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Reporte de ventas por sucursal")
public record ReporteVentasSucursalResponse(

        @Schema(description = "ID de la sucursal", example = "1")
        Long idSucursal,

        @Schema(description = "Nombre de la sucursal", example = "Sucursal norte")
        String nombreSucursal,

        @Schema(description = "Total facturado en ventas registradas", example = "1500.00")
        BigDecimal totalFacturado,

        @Schema(description = "Cantidad de piezas vendidas en ventas registradas", example = "10")
        Long cantidadProductosVendidos
) {

}
