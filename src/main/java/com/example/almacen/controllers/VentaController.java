package com.example.almacen.controllers;

import com.example.almacen.dto.ventas.VentaRequest;
import com.example.almacen.dto.ventas.VentaResponse;
import com.example.almacen.services.ventas.VentaService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaResponse>> listar(){
        return ResponseEntity.ok(ventaService.listar());
    }

    @GetMapping("/canceladas")
    public ResponseEntity<List<VentaResponse>> listarCanceladas(){
        return ResponseEntity.ok(ventaService.listarCanceladas());
    }

    @PostMapping
    public ResponseEntity<VentaResponse> registrar(
            @Valid @RequestBody VentaRequest request
            ){
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.registrar(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(
            @Parameter(description = "Id de la venta", example = "1")
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ){
        ventaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

}
