package com.example.almacen.services.ventas;

import com.example.almacen.dto.ventas.DetalleVentaRequest;
import com.example.almacen.dto.ventas.VentaRequest;
import com.example.almacen.dto.ventas.VentaResponse;
import com.example.almacen.entities.DetalleVenta;
import com.example.almacen.entities.Producto;
import com.example.almacen.entities.Sucursal;
import com.example.almacen.entities.Venta;
import com.example.almacen.enums.EstadoVenta;
import com.example.almacen.exceptions.ConflictoException;
import com.example.almacen.exceptions.RecursoNoEncontradoException;
import com.example.almacen.mappers.DetalleVentaMapper;
import com.example.almacen.mappers.VentaMapper;
import com.example.almacen.repositories.SucursalRepository;
import com.example.almacen.repositories.VentaRepository;
import com.example.almacen.services.productos.ProductosService;
import com.example.almacen.services.sucursales.SucursalService;
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

    private final SucursalService sucursalService;

    private final ProductosService productoService;

    private final VentaRepository ventaRepository;

    private final VentaMapper ventaMapper;

    private final DetalleVentaMapper detalleVentaMapper;

    @Override
    public List<VentaResponse> listar() {

        return ventaRepository.findByEstadoVenta(EstadoVenta.REGISTRADA).stream().map(ventaMapper::entidadAResponse).toList();
    }

    @Override
    public List<VentaResponse> listarCanceladas() {

        return ventaRepository.findByEstadoVenta(EstadoVenta.CANCELADA).stream().map(ventaMapper::entidadAResponse).toList();
    }

    @Override
    public VentaResponse obtenerPorIdActiva(Long id) {



        return null;
    }

    @Override
    public VentaResponse registrar(VentaRequest request) {
        log.info("Inicia registro de venta");


        Sucursal sucursal = sucursalService.obtenerSucursalOException(request.idSucursal());

        List<DetalleVenta> detalleVentas = request.productos().stream().map(
                detalleVentaRequest -> {
                    Producto producto = productoService.obtenerProductoOException(detalleVentaRequest.idProducto());

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

    Venta obtenerVentaOException(Long id){
        log.info("Buscando venta con id: {}", id);

        return ventaRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException(
                        "Venta no encontrada con: id" + id
                )
        );
    }
}
