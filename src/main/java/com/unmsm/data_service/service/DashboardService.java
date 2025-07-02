package com.unmsm.data_service.service;

import com.unmsm.data_service.dto.*;
import com.unmsm.data_service.dto.DashboardDTO.*;
import com.unmsm.data_service.repository.DashboardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class DashboardService {

    private final DashboardRepository repo;

    public DashboardService(DashboardRepository repo) {
        this.repo = repo;
    }

    /* ──────────────────────────────────────────────────────────── */
    public DashboardDTO generar() {

        /* ───── 1. KPIs ───── */
        KpiView kv = repo.kpis();
        KpiBlock kpi = new KpiBlock(
                nz(kv.getMermaTransporte()),
                nz(kv.getMermaTanques()),
                nz(kv.getVolumenTransportado()),
                nz(kv.getTemperaturaProm7d())
        );

        /* helpers para mapear */
        Function<PointView, Point> toPoint = v -> new Point(v.getLabel(), nz(v.getValue()));
        Function<NameValueView, NameValue> toNV = v -> new NameValue(v.getName(), nz(v.getValue()));

        /* ───── 2-4. Series y pies ───── */
        List<Point>       mermaDiaria   = repo.mermaDiariaTransporte().stream().map(toPoint).toList();
        List<NameValue>   mermaTipo     = repo.mermaPorTipo().stream().map(toNV).toList();
        List<NameValue>   tanquesTop    = repo.tanquesTop().stream().map(toNV).toList();

        /* ───── 5. Cisternas ───── */
        List<CisternaResumen> cisternas = repo.cisternasResumen().stream()
                .map(c -> new CisternaResumen(
                        c.getPlaca(),
                        nz(c.getVolumen()),
                        nz(c.getMerma()),
                        nz(c.getPorcentaje())
                ))
                .toList();

        /* ───── 6-7. Temperatura ───── */
        List<Point>     tempDia   = repo.temperaturaDiaria().stream().map(toPoint).toList();
        List<NameValue> tempRange = repo.temperaturaRangos().stream().map(toNV).toList();

        /* ───── 8. Inventario comparativo ───── */
        List<InvComparativo> invComp = repo.invComparativo().stream()
                .map(i -> new InvComparativo(i.getFecha(), nz(i.getObservado()), nz(i.getA60())))
                .toList();

        /* ───── 9. Ranking operadores ───── */
        List<NameValue> ranking = repo.rankingOperadores().stream().map(toNV).toList();

        /* ---- inventario diario total ---- */
        List<Point> inventarioTotal = repo.inventarioDiarioTotal()
                .stream().map(toPoint).toList();


        /* ───── DTO final ───── */
        return new DashboardDTO(
                kpi,
                mermaDiaria,
                mermaTipo,
                tanquesTop,
                cisternas,
                tempDia,
                tempRange,
                invComp,
                ranking,      // nuevo
                inventarioTotal            // nuevo
        );
    }

    /* helper: null → 0.0 */
    private static Double nz(Double d) { return Optional.ofNullable(d).orElse(0.0); }
}
