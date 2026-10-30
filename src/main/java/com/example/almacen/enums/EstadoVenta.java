package com.example.almacen.enums;

import com.example.almacen.exceptions.DatoInvalidoException;
import com.example.almacen.utils.StringCustomUtils;
import com.example.almacen.utils.ValoresNumericosUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EstadoVenta {

    REGISTRADA(0L, "Venta registrada"),
    CANCELADA(1L, "Venta cancelada");

    private final Long codigo;
    private final String descripcion;

    public static EstadoVenta obtenerEstadoVentaPorDescripcion(String descripcion){
        StringCustomUtils.validarNoVacio(descripcion, "La descripcion es requerida");

        String descripcionNormalizada = StringCustomUtils.normalizarTexto(descripcion);

        for (EstadoVenta estadoVenta : values()){
            if (StringCustomUtils.normalizarTexto(estadoVenta.descripcion).equalsIgnoreCase(descripcionNormalizada))
                return estadoVenta;
        }

        throw new DatoInvalidoException("No existe un estado de venta con la descripcion: " + descripcion);
    }

    public static EstadoVenta obtenerEstadoVentaPorCodigo(Long codigo){
        ValoresNumericosUtils.validarNumeroRequerido(codigo, "La descripcion es requerida");

        for (EstadoVenta estadoVenta : values()){
            if (estadoVenta.codigo.equals(codigo))
                return estadoVenta;
        }

        throw new DatoInvalidoException("No existe un estado de venta con la descripcion: " + codigo);
    }
}
