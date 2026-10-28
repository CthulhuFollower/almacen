package com.example.almacen.controllers;

import com.example.almacen.dto.sucursales.SucursalRequest;
import com.example.almacen.dto.sucursales.SucursalResponse;
import com.example.almacen.services.sucursales.SucursalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
@RequiredArgsConstructor
@Tag(name = "API Sucursales", description = "Gestion de sucursales")
public class SucursalController {

    private final SucursalService sucursalService;

    @GetMapping
    @Operation(summary = "Listar sucursales", description = "Gestion de sucursales")
    @ApiResponse(responseCode = "200", description = "Listado obtenido")
    public ResponseEntity<List<SucursalResponse>> listar(){
      return ResponseEntity.ok(sucursalService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener sucursal por id", description = "Busqueda de sucursales especificas")
    @ApiResponse(responseCode = "200", description = "Producto obtenido")
    public ResponseEntity<SucursalResponse> obtenerPorId(

            @Parameter(description = "Id de la sucursal", example = "1")
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ){
        return ResponseEntity.ok(sucursalService.obtenerPorId(id));
    }

    @PostMapping()
    @Operation(summary = "Registrar una sucursal", description = "Registrar sucursal")
    @ApiResponse(responseCode = "201", description = "Sucursal registrada")
    public ResponseEntity<SucursalResponse> registrar(
            @Valid @RequestBody SucursalRequest request
            ){

        return ResponseEntity.status(HttpStatus.CREATED).body(sucursalService.registrar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una sucursal", description = "Actualizar sucursales")
    @ApiResponse(responseCode = "200", description = "Sucursal actualizada")
    public ResponseEntity<SucursalResponse> actualizar(

            @Valid @RequestBody SucursalRequest request,
            @Parameter(description = "Id de la sucursal", example = "1")
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ){

        return ResponseEntity.ok(sucursalService.actualizar(request, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar sucursal por id", description = "Eliminar sucursal por id")
    @ApiResponse(responseCode = "204", description = "Sucursal eliminada")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "Id de la sucursal", example = "1")
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ){
        sucursalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
