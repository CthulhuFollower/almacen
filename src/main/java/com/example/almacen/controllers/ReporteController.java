package com.example.almacen.controllers;

import com.example.almacen.dto.reportes.ReporteVentasSucursalResponse;
import com.example.almacen.services.reportes.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
@Tag(name = "API Reportes", description = "Reportes de ventas")
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/ventas/sucursales")
    @Operation(summary = "Listar ventas por sucursal", description = "Total facturado y piezas vendidas, excluyendo ventas canceladas")
    @ApiResponse(responseCode = "200", description = "Reporte obtenido")
    public ResponseEntity<List<ReporteVentasSucursalResponse>> listarVentasPorSucursal(){
        return ResponseEntity.ok(reporteService.listarVentasPorSucursal());
    }
}
