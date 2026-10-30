package com.example.almacen.entities;

import com.example.almacen.enums.EstadoVenta;
import com.example.almacen.exceptions.DatoInvalidoException;
import com.example.almacen.utils.ValoresNumericosUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "DETALLES_VENTAS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE_VENTA")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_VENTA")
    private Venta venta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PRODUCTO")
    private Producto producto;

    @Column(name = "CANTIDAD_PRODUCTO", nullable = false)
    private Integer cantidadProducto;

    @Column(name = "PRECIO_PRODUCTO", nullable = false)
    private BigDecimal precioProducto;

    public void asignarVenta(Venta venta){
        if (venta == null)
            throw new DatoInvalidoException("La venta es requerida");

        this.venta = venta;
    }

    public static DetalleVenta crear(Producto producto, Integer cantidadProducto){

        validarDatos(producto, cantidadProducto);

        return DetalleVenta.builder()
                .producto(producto)
                .cantidadProducto(cantidadProducto)
                .precioProducto(producto.getPrecio())
                .build();

    }

    public static void validarDatos(Producto producto, Integer cantidadProducto){
        if (producto == null)
            throw new DatoInvalidoException("El producto es obligatorio");

        ValoresNumericosUtils.validarEnteroPositivo(cantidadProducto, "La cantidad es obligatoria y debe ser mayor a 0");

    }


}
