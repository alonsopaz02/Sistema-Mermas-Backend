package com.unmsm.data_service.service;

import com.unmsm.data_service.dto.MermaResumenView;
import com.unmsm.data_service.repository.MermaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MermaService {

    private final MermaRepository mermaRepository;

    public MermaService(MermaRepository mermaRepository) {
        this.mermaRepository = mermaRepository;
    }

    public List<MermaResumenView> obtenerMermas(LocalDate inicio, LocalDate fin,
                                                Long estacionId, Long tanqueId) {
        return mermaRepository.findResumenMermas(inicio, fin, estacionId, tanqueId);
    }
}
