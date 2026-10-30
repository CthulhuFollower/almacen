package com.example.almacen.utils;

import org.springframework.data.jpa.domain.Specification;

import java.util.function.Function;

public class SpecificationsUtils {

    public static <T, V> Specification<T> whenPresent(
            V valor,
            Function<V, Specification<T>> filtro
    ) {
        if (valor == null ||
                (valor instanceof String texto && texto.isBlank())) {
            return Specification.unrestricted();
        }

        return filtro.apply(valor);
    }

}
