package com.example.almacen.services.productos;

import com.example.almacen.dto.productos.ProductoRequest;
import com.example.almacen.dto.productos.ProductoResponse;
import com.example.almacen.entities.Producto;
import com.example.almacen.enums.Categoria;

import java.math.BigDecimal;
import java.util.List;

public interface ProductosService {

    List<ProductoResponse> listar(
            String nombre, Categoria categoria,
            BigDecimal precioMin, BigDecimal precioMax
    );

    ProductoResponse obtenerPorId(Long id);

    ProductoResponse registrar(ProductoRequest request);

    ProductoResponse actualizar(ProductoRequest request, Long id);

    void eliminar (Long id);

    Producto obtenerProductoOException(Long id);
}
