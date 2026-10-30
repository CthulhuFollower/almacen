package com.example.almacen.repositories;

import com.example.almacen.dto.reportes.ReporteVentasSucursalResponse;
import com.example.almacen.entities.Venta;
import com.example.almacen.enums.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByEstadoVenta(EstadoVenta estadoVenta);

    Optional<Venta> findByEstadoVentaAndId(EstadoVenta estadoVenta, Long id);

    @Query("""
            SELECT new com.example.almacen.dto.reportes.ReporteVentasSucursalResponse(
                sucursal.id,
                sucursal.nombre,
                COALESCE(SUM(detalleVenta.cantidadProducto * detalleVenta.precioProducto), 0),
                COALESCE(SUM(detalleVenta.cantidadProducto), 0L)
            )
            FROM Sucursal sucursal
            LEFT JOIN Venta venta ON venta.sucursal = sucursal AND venta.estadoVenta = :estadoVenta
            LEFT JOIN venta.detalleVentas detalleVenta
            GROUP BY sucursal.id, sucursal.nombre
            ORDER BY sucursal.id
            """)
    List<ReporteVentasSucursalResponse> obtenerReporteVentasPorSucursal(@Param("estadoVenta") EstadoVenta estadoVenta);

}
