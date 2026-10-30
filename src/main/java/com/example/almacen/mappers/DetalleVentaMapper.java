package com.example.almacen.mappers;

import com.example.almacen.dto.ventas.DetalleVentaRequest;
import com.example.almacen.dto.ventas.DetalleVentaResponse;
import com.example.almacen.entities.DetalleVenta;
import com.example.almacen.entities.Producto;
import org.springframework.stereotype.Component;

@Component
public class DetalleVentaMapper {

    public DetalleVenta requestAEntidad(DetalleVentaRequest request, Producto producto){

        return request == null
                ? null
                : DetalleVenta.crear(
                        producto,
                        request.cantidadProducto()
        );
    }

    public DetalleVentaResponse entidadAResponse(DetalleVenta detalleVenta){

        return detalleVenta == null
                ? null
                : new DetalleVentaResponse(
                        detalleVenta.getProducto().getId(),
                        detalleVenta.getProducto().getNombre(),
                        detalleVenta.getProducto().getCategoria().toString(),
                        detalleVenta.getPrecioProducto(),
                        detalleVenta.getCantidadProducto()
        );
    }

}
