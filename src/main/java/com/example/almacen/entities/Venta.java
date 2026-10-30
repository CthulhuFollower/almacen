package com.example.almacen.entities;

import com.example.almacen.enums.EstadoVenta;
import com.example.almacen.exceptions.ConflictoException;
import com.example.almacen.exceptions.DatoInvalidoException;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "VENTAS")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Builder
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VENTA")
    private Long id;

    @Column(name = "ESTADO", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoVenta estadoVenta;

    @Column(name = "FECHA", nullable = false)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SUCURSAL", nullable = false)
    private Sucursal sucursal;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalleVentas = new ArrayList<>();

    public void agregarDetalle(DetalleVenta detalleVenta){
        if (detalleVenta == null)
            throw new DatoInvalidoException("El detalle de la venta es requerido");

        this.detalleVentas.add(detalleVenta);
        detalleVenta.asignarVenta(this);
    }

    public void cancelar(){
        if (this.estadoVenta == EstadoVenta.CANCELADA)
            throw new ConflictoException("La venta ya esta cancelada");

        this.estadoVenta = EstadoVenta.CANCELADA;
    }

    //Toca validar que los datos queden bien
    //Y las cosas que se puedan hacer

    public static Venta crear(EstadoVenta estadoVenta, LocalDate fecha, Sucursal sucursal, List<DetalleVenta> detalleVentas){

        validarDatos(estadoVenta, fecha, sucursal, detalleVentas);

        Venta venta = Venta.builder()
                .estadoVenta(estadoVenta)
                .fecha(fecha)
                .sucursal(sucursal)
                .build();

        detalleVentas.forEach(venta::agregarDetalle);

        return venta;
    }

    public static void validarDatos(EstadoVenta estadoVenta, LocalDate fecha, Sucursal sucursal, List<DetalleVenta> detalleVentas){
        if (estadoVenta == null)
            throw new DatoInvalidoException("El estado de venta es requerido");

        if(fecha == null)
            throw new DatoInvalidoException("La fecha es requerida");

        if(fecha.isAfter(LocalDate.now()))
            throw new DatoInvalidoException("La fecha no puede ser futura");

        if(sucursal == null)
            throw new DatoInvalidoException("La sucursal es requerida");

        if(detalleVentas == null || detalleVentas.isEmpty())
            throw new DatoInvalidoException("Los detalles de la venta son requeridos y no puede estar vacio");
    }

    public BigDecimal calcularTotal(){

        return this.detalleVentas.stream()
                .map(detalleVenta -> detalleVenta.getPrecioProducto()
                        .multiply(BigDecimal.valueOf(detalleVenta.getCantidadProducto())))
                .reduce(BigDecimal.ZERO,
                        BigDecimal::add);

    }

}
