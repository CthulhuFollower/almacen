package com.example.almacen.specifications;

import com.example.almacen.entities.Producto;
import com.example.almacen.enums.Categoria;
import com.example.almacen.utils.SpecificationsUtils;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductoSpecifications {

    public static Specification<Producto> nombreContiene(String nombre){

        return SpecificationsUtils.whenPresent(
                nombre,
                valor -> (root, query, cb) ->
                        cb.like(cb.lower(root.get("nombre")), "%" + nombre.toLowerCase().trim() + "%"));
    }

    public static Specification<Producto> categoriaContiene(Categoria categoria){

        return SpecificationsUtils.whenPresent(
                categoria,
                valor ->
                        (root, query, cb) ->
                                cb.equal(root.get("categoria"), categoria)
        );

    }

    public static Specification<Producto> precioMin(BigDecimal precio){
        return SpecificationsUtils.whenPresent(
                precio,
                valor ->
                        (root, query, cb) ->
                                cb.greaterThanOrEqualTo(root.get("precio"), precio));
    }

    public static Specification<Producto> precioMax(BigDecimal precio){

        return SpecificationsUtils.whenPresent(
                precio,
                valor -> (root, query, cb) ->
                        cb.lessThanOrEqualTo(root.get("precio"), precio)
        );

    }
}
