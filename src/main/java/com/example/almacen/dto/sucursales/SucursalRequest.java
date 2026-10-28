package com.example.almacen.dto.sucursales;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Datos necesarios para crear o actualizar una Sucursal")
public record SucursalRequest(
        @Schema(description = "Nombre de sucursal", example = "Sucursal norte")
        @NotBlank(message = "EL nombre es requerido")
        @Size(min = 5, max = 50, message = "El nombre debe tener entre 5 y 50 caracteres")
        String nombre,

        @Schema(description = "Direccion de la sucursal", example = "Calle poniente")
        @NotBlank(message = "La sucursal es requerida")
        @Size(min = 5, max = 150, message = "La sucursal debe tener entre 5 y 150 caracteres")
        String direccion
) {

}
