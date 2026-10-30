package com.example.almacen.converters;

import com.example.almacen.enums.Categoria;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoriaConverter implements Converter<String, Categoria> {

    @Override
    public Categoria convert(String categoria) {
        if (categoria == null || categoria.isBlank()) {
            return null;
        }

        return Categoria.obtenerCategoriaPorDescripcion(categoria.trim());
    }

}
