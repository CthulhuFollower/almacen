package com.example.almacen.services.productos;

import com.example.almacen.dto.productos.ProductoRequest;
import com.example.almacen.dto.productos.ProductoResponse;
import com.example.almacen.entities.Producto;
import com.example.almacen.enums.Categoria;
import com.example.almacen.exceptions.RecursoNoEncontradoException;
import com.example.almacen.mappers.ProductoMapper;
import com.example.almacen.repositories.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductoServiceImpl implements ProductosService{

    private final ProductoRepository productoRepository;

    private final ProductoMapper productoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> listar(String nombre, String categoria, BigDecimal precioMin, BigDecimal precioMax) {

        log.info("Listando todos los productos");

        return productoRepository.findAll().stream()
                .map(productoMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponse obtenerPorId(Long id) {
        return productoMapper.entidadAResponse(obtenerProductoOException(id));
    }

    @Override
    public ProductoResponse registrar(ProductoRequest request) {
        log.info("Registrando nuevo producto...");

        Producto producto = productoMapper.requestAEntidad(
                request,
                Categoria.obtenerCategoriaPorDescripcion((request.categoria().trim()))
        );

        productoRepository.saveAndFlush(producto);

        return productoMapper.entidadAResponse(producto);
    }

    @Override
    public ProductoResponse actualizar(ProductoRequest request, Long id) {

        Producto producto = obtenerProductoOException(id);

        log.info("Actualizando producto con id {}", id);
        producto.actualizar(
                request.nombre(),
                Categoria.obtenerCategoriaPorDescripcion(request.categoria().trim()),
                request.precio(),
                request.cantidad()
        );

        productoRepository.saveAndFlush(producto);

        log.info("Producto con id {} actualizado", id);

        return productoMapper.entidadAResponse(producto);
    }

    @Override
    public void eliminar(Long id) {
        Producto producto = obtenerProductoOException(id);

        log.info("Eliminando producto con id {}", id);

        productoRepository.delete(producto);
        productoRepository.flush();

        log.info("Producto con id {} eliminado", id);
    }

    private Producto obtenerProductoOException(Long id){
        log.info("Buscando producto con id: {}", id);

        return productoRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException(
                        "Producto no encontrado con id: " + id
                )
        );
    }
}
