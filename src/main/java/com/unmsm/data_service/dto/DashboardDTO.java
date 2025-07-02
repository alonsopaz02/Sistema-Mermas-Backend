/// src/main/java/com/unmsm/data_service/dto/DashboardDTO.java
package com.unmsm.data_service.dto;

import java.util.List;

public record DashboardDTO(
        KpiBlock                kpis,
        List<Point>             mermaDiariaTransporte,
        List<NameValue>         mermaPorTipo,
        List<NameValue>         tanquesMermaTop,
        List<CisternaResumen>   cisternasResumen,
        List<Point>             temperaturaDiaria,
        List<NameValue>         temperaturaRangos,
        List<InvComparativo>    inventarioComparativo,
        List<NameValue>       rankingOperadores,   // ← nuevo
        List<Point> inventarioDiarioTotal
) {
    /* ---------- sub-records ---------- */
    public static record KpiBlock(
            double mermaTransporte,
            double mermaTanques,
            double volumenTransportado,
            double temperaturaProm7d
    ) {}

    public static record Point(String label, double value) {}

    public static record NameValue(String name, double value) {}

    public static record CisternaResumen(
            String placa, double volumen, double merma, double porcentaje
    ) {}

    public static record InvComparativo(
            String fecha, double observado, double a60
    ) {}

    /* y al final de la clase, junto a los otros records */
    public static record WaterfallDaily(
            String fecha,
            Double inicial,
            Double descargas,
            Double ventas,
            Double consumo,
            Double merma,
            Double finalInv   // inventario final
    ) {}
}
