// controller/AlertController.java
package com.unmsm.data_service.controller;

import com.unmsm.data_service.service.AlertService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import com.unmsm.data_service.dto.AlertDTO;

@RestController
public class AlertController {

    private final AlertService service;

    public AlertController(AlertService service) {
        this.service = service;
    }

    @GetMapping("/api/alertas")
    public Map<String, java.util.List<AlertDTO>> ultimas() {
        return service.ultimas();   // MERMA_DIA | TEMP_EXTREMA | MERMA_TRANS
    }
}
