package com.example.almacen.services.reportes;

import com.example.almacen.dto.reportes.ReporteVentasSucursalResponse;

import java.util.List;

public interface ReporteService {

    List<ReporteVentasSucursalResponse> listarVentasPorSucursal();
}
