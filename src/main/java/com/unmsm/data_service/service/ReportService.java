package com.unmsm.data_service.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.unmsm.data_service.dto.ReportFilter;
import com.unmsm.data_service.dto.ReportType;
import com.unmsm.data_service.dto.rows.*;
import com.unmsm.data_service.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReportService {

    private final ReportRepository repo;
    public ReportService(ReportRepository repo) { this.repo = repo; }

    /* ================================================================ */
    public byte[] generarPdf(ReportFilter f) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        /* documento apaisado A4 con márgenes */
        Document doc = new Document(PageSize.A4.rotate(), 36, 36, 36, 36);
        try {
            PdfWriter.getInstance(doc, baos);
            doc.open();

            /* ── Cabecera ───────────────────────────────────────────── */
            Font h1 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            doc.add(new Paragraph(
                    "REPORTE – " + f.tipo().name().replace('_', ' '), h1));
            doc.add(new Paragraph(
                    "Período: %s a %s".formatted(f.fechaInicio(), f.fechaFin())));
            doc.add(Chunk.NEWLINE);

            /* ── Contenido dinámico ────────────────────────────────── */
            switch (f.tipo()) {
                case MERMA_TRANSPORTE      -> tablaMermaTransporte(doc, f);
                case MERMA_INVENTARIO      -> tablaMermaInventario(doc, f);
                case OPERACIONES           -> tablaOperaciones(doc, f);
                case TEMPERATURA_TANQUES   -> tablaTemperaturas(doc, f);
                case MERMA_POR_RESPONSABLE -> tablaResponsables(doc, f);
            }

        } catch (DocumentException de) {
            throw new RuntimeException("Error generando PDF", de);
        } finally {
            doc.close();
        }
        return baos.toByteArray();
    }

    /* ================================================================ */
    /* ============  TABLAS INDIVIDUALES  ============================= */

    private void tablaMermaTransporte(Document d, ReportFilter f)
            throws DocumentException {

        List<MermaTransporteRow> rows = repo.mermaTransporte(
                f.fechaInicio(), f.fechaFin(), f.cisternaId());

        PdfPTable t = createTable(3, "Fecha", "Cisterna", "Merma (L 60°)");
        for (MermaTransporteRow r : rows)
            addRow(t, r.getFecha(), r.getCisterna(), format(r.getMermaL()));

        d.add(t);  d.add(Chunk.NEWLINE);
    }

    private void tablaMermaInventario(Document d, ReportFilter f)
            throws DocumentException {

        List<MermaInventarioRow> rows = repo.mermaInventario(
                f.fechaInicio(), f.fechaFin(), f.estacionId(), f.tanqueId());

        PdfPTable t = createTable(3, "Día", "Tanque", "Merma (L 60°)");
        for (MermaInventarioRow r : rows)
            addRow(t, r.getDia(), r.getTanque(), format(r.getMerma60()));

        d.add(t);  d.add(Chunk.NEWLINE);
    }

    private void tablaOperaciones(Document d, ReportFilter f)
            throws DocumentException {

        List<OperacionRow> rows = repo.operaciones(
                f.fechaInicio(), f.fechaFin());

        PdfPTable t = createTable(4, "Fecha", "Tipo", "Entidad", "Volumen (L 60°)");
        for (OperacionRow r : rows)
            addRow(t, r.getFecha(), r.getTipo(), r.getEntidad(), format(r.getVolumen60()));

        d.add(t);  d.add(Chunk.NEWLINE);
    }

    private void tablaTemperaturas(Document d, ReportFilter f)
            throws DocumentException {

        List<TemperaturaRow> rows = repo.temperaturas(
                f.fechaInicio(), f.fechaFin(), f.estacionId(), f.tanqueId());

        PdfPTable t = createTable(3, "Fecha", "Tanque", "Temperatura (°C)");
        for (TemperaturaRow r : rows)
            addRow(t, r.getFecha(), r.getTanque(), format(r.getTemperatura()));

        d.add(t);  d.add(Chunk.NEWLINE);
    }

    private void tablaResponsables(Document d, ReportFilter f)
            throws DocumentException {

        List<ResponsableRow> rows = repo.mermaPorResponsable(
                f.fechaInicio(), f.fechaFin(), f.responsable());

        PdfPTable t = createTable(2, "Responsable", "Merma Promedio (L 60°)");
        for (ResponsableRow r : rows)
            addRow(t, r.getResponsable(), format(r.getMermaPromedio60()));

        d.add(t);  d.add(Chunk.NEWLINE);
    }

    /* ================================================================ */
    /* ============  UTILIDADES  ====================================== */

    /** Crea una tabla con ancho 100 % y cabeceras grises */
    private PdfPTable createTable(int cols, String... headers)
            throws DocumentException {

        PdfPTable t = new PdfPTable(cols);
        t.setWidthPercentage(100);

        Font head = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
        BaseColor bg = new BaseColor(230, 230, 230);

        for (String h : headers) {
            PdfPCell c = new PdfPCell(new Phrase(h, head));
            c.setBackgroundColor(bg);
            c.setPadding(4);
            t.addCell(c);
        }
        return t;
    }

    /** Añade una fila genérica */
    private void addRow(PdfPTable t, Object... vals) {
        for (Object v : vals) {
            PdfPCell c = new PdfPCell(new Phrase(String.valueOf(v)));
            c.setPadding(3);
            t.addCell(c);
        }
    }

    /** Formatea double (null safe) */
    private static String format(Double d) { return d == null ? "-" : String.format("%,.2f", d); }
}
