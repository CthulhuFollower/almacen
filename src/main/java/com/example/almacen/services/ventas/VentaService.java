package com.example.almacen.services.ventas;

import com.example.almacen.dto.ventas.VentaRequest;
import com.example.almacen.dto.ventas.VentaResponse;

import java.util.List;

public interface VentaService{
    List<VentaResponse> listar();

    List<VentaResponse> listarCanceladas();

    VentaResponse obtenerPorIdActiva(Long id);

    VentaResponse registrar(VentaRequest request);

    void cancelar(Long id);
}
