package com.example.almacen.utils;

import com.example.almacen.entities.Producto;
import com.example.almacen.entities.Sucursal;
import com.example.almacen.enums.Categoria;
import com.example.almacen.repositories.ProductoRepository;
import com.example.almacen.repositories.SucursalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DatosIniciales implements CommandLineRunner {

    private final ProductoRepository productoRepository;

    private final SucursalRepository sucursalRepository;



    @Override
    public void run(String... args) throws Exception {
        if (productoRepository.count() == 0){
            productoRepository.saveAll(List.of(

                    new Producto(
                            null,
                            "Laptop Gamer",
                            Categoria.ELECTRONICA,
                            BigDecimal.valueOf(25),
                            50
                    ),

                    new Producto(
                            null,
                            "Mouse Inalambrico",
                            Categoria.ELECTRONICA,
                            BigDecimal.valueOf(25),
                            50
                    ),

                    new Producto(
                            null,
                            "Camiseta Deportiva",
                            Categoria.ROPA,
                            BigDecimal.valueOf(20),
                            100
                    )
            ));
            log.info("Productos de prueba cargados correctamente");

        }

        if (sucursalRepository.count() == 0){
            sucursalRepository.saveAll(List.of(

                    new Sucursal(
                            null,
                            "Sucursal sur",
                            "Periferico sur 123"
                    ),
                    new Sucursal(
                            null,
                            "Sucursal este",
                            "Periferico este 234"
                    ),
                    new Sucursal(
                            null,
                            "Sucursal oeste",
                            "Periferico oeste 345"
                    )
            ));
            log.info("Sucursales de prueba cargadas correctamente");
        }
    }
}
