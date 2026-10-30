package com.example.almacen.services.sucursales;

import com.example.almacen.dto.sucursales.SucursalRequest;
import com.example.almacen.dto.sucursales.SucursalResponse;
import com.example.almacen.entities.Sucursal;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

public interface SucursalService {

    List<SucursalResponse> listar();

    SucursalResponse obtenerPorId(Long id);

    SucursalResponse registrar(SucursalRequest request);

    SucursalResponse actualizar(SucursalRequest request, Long id);

    void eliminar (Long id);

    Sucursal obtenerSucursalOException(Long id);
}
