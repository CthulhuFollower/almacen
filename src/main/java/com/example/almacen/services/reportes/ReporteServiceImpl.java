package com.example.almacen.services.reportes;

import com.example.almacen.dto.reportes.ReporteVentasSucursalResponse;
import com.example.almacen.enums.EstadoVenta;
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
public class ReporteServiceImpl implements ReporteService{

    private final VentaRepository ventaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ReporteVentasSucursalResponse> listarVentasPorSucursal() {
        log.info("Listando reporte de ventas por sucursal");

        return ventaRepository.obtenerReporteVentasPorSucursal(EstadoVenta.REGISTRADA);
    }
}
