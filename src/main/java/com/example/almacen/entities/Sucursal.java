package com.example.almacen.entities;

import com.example.almacen.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SUCURSALES")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SUCURSAL")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 50, unique = true)
    private String nombre;

    @Column(name = "DIRECCION", nullable = false, length = 150)
    private String direccion;

    public static Sucursal crear(String nombre, String direccion) {
        validarDatos(nombre, direccion);

        return new Sucursal(null, nombre.trim(), direccion);
    }

    public static void validarDatos(String nombre, String direccion) {
        StringCustomUtils.validarTamanio(nombre, 5, 50, "El nombre es requerido y debe tener de 5 a 50 caracteres");
        StringCustomUtils.validarTamanio(direccion, 5, 150, "La direccion es requerida y debe tener de 5 a 150 caracteres");
    }

    public void actualizar(String nombre, String direccion){
        validarDatos(nombre, direccion);
        this.nombre = nombre.trim();
        this.direccion = direccion.trim();
    }
}
