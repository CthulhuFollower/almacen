package com.example.almacen.controllers;

import com.example.almacen.dto.productos.ProductoRequest;
import com.example.almacen.dto.productos.ProductoResponse;
import com.example.almacen.services.productos.ProductosService;
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

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "API Productos", description = "Gestion de invetario de productos")
public class ProductoController {

    private final ProductosService productosService;

    @GetMapping
    @Operation(summary = "Listar productos", description = "Gestion de inventario de productos")
    @ApiResponse(responseCode = "200", description = "Listado obtenido")
    public ResponseEntity<List<ProductoResponse>> listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) BigDecimal precioMin,
            @RequestParam(required = false) BigDecimal precioMax
    ){
      return ResponseEntity.ok(productosService.listar(nombre, categoria, precioMin, precioMax));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por id", description = "Busqueda de productos especificos")
    @ApiResponse(responseCode = "200", description = "Producto obtenido")
    public ResponseEntity<ProductoResponse> obtenerPorId(

            @Parameter(description = "Id del producto", example = "1")
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ){
        return ResponseEntity.ok(productosService.obtenerPorId(id));
    }

    @PostMapping()
    @Operation(summary = "Registrar un producto", description = "Registrar productos")
    @ApiResponse(responseCode = "201", description = "Producto registrado")
    public ResponseEntity<ProductoResponse> registrar(
            @Valid @RequestBody ProductoRequest request
            ){

        return ResponseEntity.status(HttpStatus.CREATED).body(productosService.registrar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto", description = "Actualizar productos")
    @ApiResponse(responseCode = "200", description = "Producto actualizado")
    public ResponseEntity<ProductoResponse> actualizar(

            @Valid @RequestBody ProductoRequest request,
            @Parameter(description = "Id del producto", example = "1")
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ){

        return ResponseEntity.ok(productosService.actualizar(request, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto por id", description = "Eliminar producto por id")
    @ApiResponse(responseCode = "204", description = "Producto eliminado")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "Id del producto", example = "1")
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ){
        productosService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
