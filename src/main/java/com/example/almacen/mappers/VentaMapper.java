package com.example.almacen.mappers;

import com.example.almacen.dto.ventas.DetalleVentaResponse;
import com.example.almacen.dto.ventas.VentaRequest;
import com.example.almacen.dto.ventas.VentaResponse;
import com.example.almacen.entities.DetalleVenta;
import com.example.almacen.entities.Sucursal;
import com.example.almacen.entities.Venta;
import com.example.almacen.enums.EstadoVenta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class VentaMapper {

    private final SucursalMapper sucursalMapper;
    private final DetalleVentaMapper detalleVentaMapper;

    public Venta requestAEntiedad(VentaRequest request, Sucursal sucursal, List<DetalleVenta> detalleVentas){
        return request == null
                ? null
                : Venta.crear(
                        EstadoVenta.REGISTRADA,
                        LocalDate.now(),
                        sucursal,
                        detalleVentas
        );
    }

    public VentaResponse entidadAResponse(Venta venta){

        return venta == null
                ? null
                : new VentaResponse(
                        venta.getId(),
                        venta.getFecha().toString(),
                        venta.getEstadoVenta().toString(),
                        sucursalMapper.entidadAResponse(venta.getSucursal()),
                        venta.getDetalleVentas().stream().map(detalleVentaMapper::entidadAResponse).toList(),
                        venta.calcularTotal()

        );

    }

}
