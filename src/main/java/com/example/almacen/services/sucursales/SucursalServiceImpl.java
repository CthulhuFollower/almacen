package com.example.almacen.services.sucursales;

import com.example.almacen.entities.Sucursal;
import com.example.almacen.exceptions.ConflictoException;
import com.example.almacen.exceptions.RecursoNoEncontradoException;
import com.example.almacen.dto.sucursales.SucursalRequest;
import com.example.almacen.dto.sucursales.SucursalResponse;
import com.example.almacen.mappers.SucursalMapper;
import com.example.almacen.repositories.SucursalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor //Buscar pero con explicacion sencilla y rapida
@Transactional
@Slf4j
public class SucursalServiceImpl implements SucursalService
{

    private final SucursalRepository sucursalRepository;

    private final SucursalMapper sucursalMapper;


    @Override
    public List<SucursalResponse> listar() {
        log.info("Listando todos los productos");

        return sucursalRepository.findAll().stream()
                .map(sucursalMapper::entidadAResponse).toList();
    }

    @Override
    public SucursalResponse obtenerPorId(Long id) {
        log.info("Obteniendo sucursal por id {}...", id);
        return sucursalMapper.entidadAResponse(obtenerSucursalOException(id));
    }

    @Override
    public SucursalResponse registrar(SucursalRequest request) {

        log.info("Registrando nueva sucursal...");

        validarDatosUnicos(request);

        Sucursal sucursal = sucursalMapper.requestAEntidad(request);

        sucursalRepository.saveAndFlush(sucursal);

        log.info("Sucursal {} registrada", request.nombre());

        return sucursalMapper.entidadAResponse(sucursal);
    }

    @Override
    public SucursalResponse actualizar(SucursalRequest request, Long id) {

        log.info("Actualizando sucursal...");

        Sucursal sucursal = obtenerSucursalOException(id);

        validarDatosUnicos(request, id);

        sucursal.actualizar(
                request.nombre(),
                request.direccion()
        );

        sucursalRepository.saveAndFlush(sucursal);

        log.info("Sucursal con id {} fue actualizada", id);

        return sucursalMapper.entidadAResponse(sucursal);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando sucursal con id {}", id);

         Sucursal sucursal = obtenerSucursalOException(id);

         sucursalRepository.delete(sucursal);
         sucursalRepository.flush();

        log.info("La sucursal con id {} fue eliminada", id);

    }

    @Override
    public Sucursal obtenerSucursalOException(Long id){
        log.info("Buscando sucursal con id: {}", id);

        return sucursalRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException(
                        "Sucursal no encontrada con id: " + id
                )
        );
    }

    private void validarDatosUnicos(SucursalRequest request){
        if (sucursalRepository.existsByNombreIgnoreCase(request.nombre().trim()))
            throw new ConflictoException(
                    "Ya existe una sucursal con el nombre de: " + request.nombre());
    }

    private void validarDatosUnicos(SucursalRequest request, Long id){
        if (sucursalRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre().trim(), id))
            throw new ConflictoException(
                    "Ya existe una sucursal con el nombre de: " + request.nombre());
    }
}
