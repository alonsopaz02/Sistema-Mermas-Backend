package com.unmsm.data_service.service;

import com.unmsm.data_service.model.Producto;
import com.unmsm.data_service.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> getProductoById(Long id) {
        return productoRepository.findById(id);
    }

    public Producto addProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto updateProducto(Long id, Producto producto) {
        return productoRepository.findById(id)
                .map(existingProducto -> {
                    existingProducto.setNombre(producto.getNombre());
                    existingProducto.setDescripcion(producto.getDescripcion());
                    existingProducto.setTipo(producto.getTipo());
                    return productoRepository.save(existingProducto);
                }).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
