package com.unmsm.data_service.service;

import com.unmsm.data_service.model.Venta;
import com.unmsm.data_service.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    // Obtener todas las ventas
    public List<Venta> getAllVentas() {
        return ventaRepository.findAll();
    }

    // Obtener una venta por ID
    public Optional<Venta> getVentaById(Long id) {
        return ventaRepository.findById(id);
    }

    // Crear o actualizar una venta
    public Venta saveOrUpdateVenta(Venta venta) {
        return ventaRepository.save(venta);
    }

    // Eliminar una venta
    public void deleteVenta(Long id) {
        ventaRepository.deleteById(id);
    }
}
