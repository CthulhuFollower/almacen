package com.example.almacen.services.ventas;

import com.example.almacen.dto.ventas.VentaRequest;
import com.example.almacen.dto.ventas.VentaResponse;
import com.example.almacen.entities.DetalleVenta;
import com.example.almacen.entities.Producto;
import com.example.almacen.entities.Sucursal;
import com.example.almacen.entities.Venta;
import com.example.almacen.enums.EstadoVenta;
import com.example.almacen.exceptions.RecursoNoEncontradoException;
import com.example.almacen.mappers.DetalleVentaMapper;
import com.example.almacen.mappers.VentaMapper;
import com.example.almacen.repositories.ProductoRepository;
import com.example.almacen.repositories.SucursalRepository;
import com.example.almacen.repositories.VentaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VentaServiceImpl implements VentaService{

    private final SucursalRepository sucursalRepository;

    private final ProductoRepository productoRepository;

    private final VentaRepository ventaRepository;

    private final VentaMapper ventaMapper;

    private final DetalleVentaMapper detalleVentaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponse> listar() {

        return ventaRepository.findByEstadoVenta(EstadoVenta.REGISTRADA).stream().map(ventaMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponse> listarCanceladas() {

        return ventaRepository.findByEstadoVenta(EstadoVenta.CANCELADA).stream().map(ventaMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VentaResponse obtenerPorIdActiva(Long id) {
        log.info("Buscando venta registrada con id: {}", id);

        Venta venta = ventaRepository.findByEstadoVentaAndId(EstadoVenta.REGISTRADA, id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Venta registrada no encontrada con id: " + id
                ));

        return ventaMapper.entidadAResponse(venta);
    }

    @Override
    public VentaResponse registrar(VentaRequest request) {
        log.info("Inicia registro de venta");


        Sucursal sucursal = obtenerSucursalOException(request.idSucursal());

        List<DetalleVenta> detalleVentas = request.productos().stream().map(
                detalleVentaRequest -> {
                    Producto producto = obtenerProductoOException(detalleVentaRequest.idProducto());

                    return detalleVentaMapper.requestAEntidad(detalleVentaRequest, producto);
                }
        ).toList();



        detalleVentas.forEach(detalleVenta -> {
            Producto producto = detalleVenta.getProducto();

            producto.descontarCantidad(detalleVenta.getCantidadProducto());
        });

        Venta venta = ventaMapper.requestAEntiedad(request, sucursal, detalleVentas);

        ventaRepository.saveAndFlush(venta);

        log.info("Venta registrada correctamente");
        return ventaMapper.entidadAResponse(venta);
    }

    @Override
    public void cancelar(Long id) {

        log.info("Cancelando venta por id {}", id);

        Venta venta = obtenerVentaOException(id);

        venta.cancelar();
        venta.getDetalleVentas().forEach(
                detalleVenta -> detalleVenta.getProducto().aumentarCantidad(detalleVenta.getCantidadProducto())
        );

        log.info("La venta con id {} fue cancelada exitosamente", id);

    }

    private Sucursal obtenerSucursalOException(Long id){
        log.info("Buscando sucursal con id: {}", id);

        return sucursalRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException(
                        "Sucursal no encontrada con id: " + id
                )
        );
    }

    private Producto obtenerProductoOException(Long id){
        log.info("Buscando producto con id: {}", id);

        return productoRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException(
                        "Producto no encontrado con id: " + id
                )
        );
    }

    Venta obtenerVentaOException(Long id){
        log.info("Buscando venta con id: {}", id);

        return ventaRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException(
                        "Venta no encontrada con: id" + id
                )
        );
    }
}
