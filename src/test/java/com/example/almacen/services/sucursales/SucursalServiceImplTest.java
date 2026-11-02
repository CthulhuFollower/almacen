package com.example.almacen.services.sucursales;

import com.example.almacen.dto.sucursales.SucursalRequest;
import com.example.almacen.dto.sucursales.SucursalResponse;
import com.example.almacen.entities.Sucursal;
import com.example.almacen.exceptions.ConflictoException;
import com.example.almacen.exceptions.RecursoNoEncontradoException;
import com.example.almacen.mappers.SucursalMapper;
import com.example.almacen.repositories.SucursalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SucursalServiceImplTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @Mock
    private SucursalMapper sucursalMapper;

    @InjectMocks
    private SucursalServiceImpl sucursalService;

    @Test
    void registrar_debeLanzarExcepcion_cuandoYaExisteUnaSucursalConEseNombre() {
        // Arrange
        SucursalRequest request = new SucursalRequest("Sucursal Norte", "Av. Siempre Viva 123");
        Sucursal sucursal = Sucursal.builder()
                .nombre("Sucursal Norte")
                .direccion("Av. Siempre Viva 123")
                .build();

        //when(sucursalMapper.requestAEntidad(request)).thenReturn(sucursal);
        when(sucursalRepository.existsByNombreIgnoreCase("Sucursal Norte")).thenReturn(true);

        // Act + Assert
        assertThatThrownBy(() -> sucursalService.registrar(request))
                .isInstanceOf(ConflictoException.class)
                .hasMessageContaining("Ya existe una sucursal con el nombre de: Sucursal Norte");

        verify(sucursalRepository, never()).save(any());
    }

    @Test
    void registrar_debeGuardarSucursal_cuandoElNombreNoExistePreviamente() {
        // Arrange
        SucursalRequest request = new SucursalRequest("Sucursal Sur", "Calle Falsa 456, Springfield");
        Sucursal sucursal = Sucursal.builder()
                .nombre("Sucursal Sur")
                .direccion("Calle Falsa 456, Springfield")
                .build();
        SucursalResponse response = new SucursalResponse(2L, "Sucursal Sur", "Calle Falsa 456, Springfield");

        when(sucursalMapper.requestAEntidad(request)).thenReturn(sucursal);
        when(sucursalRepository.existsByNombreIgnoreCase("Sucursal Sur")).thenReturn(false);
        when(sucursalMapper.entidadAResponse(sucursal)).thenReturn(response);

        // Act
        SucursalResponse resultado = sucursalService.registrar(request);

        // Assert
        assertThat(resultado).isEqualTo(response);
        verify(sucursalRepository).saveAndFlush(sucursal);
    }

    @Test
    void actualizar_debeLanzarExcepcion_cuandoElNuevoNombreYaLoUsaOtraSucursal() {
        // Arrange: la sucursal con id=1 existe, pero el nombre nuevo ya lo tiene OTRA sucursal
        Sucursal sucursalExistente = Sucursal.builder()
                .id(1L).nombre("Sucursal Vieja").direccion("Dirección Vieja 123")
                .build();

        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursalExistente));

        SucursalRequest request = new SucursalRequest("Sucursal Norte", "Nueva Dirección 456");
        when(sucursalRepository.existsByNombreIgnoreCaseAndIdNot("Sucursal Norte", 1L)).thenReturn(true);

        // Act + Assert
        assertThatThrownBy(() -> sucursalService.actualizar(request, 1L))
                .isInstanceOf(ConflictoException.class);
    }

    @Test
    void obtenerPorId_debeLanzarExcepcion_cuandoLaSucursalNoExiste() {
        when(sucursalRepository.findById(50L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sucursalService.obtenerPorId(50L))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Sucursal no encontrada con id: 50");
    }
}