package controller.admin;

import dao.AuditoriaDao;
import dao.CelularDao;
import dao.VentaDao;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import model.Auditoria;
import model.Celularr;
import model.DetalleVentaa;
import model.Ventaa;
import view.MenuAdministrador;

public class GestionReporte {

    private static final int STOCK_BAJO = 5;
    private static final String ARCHIVO = "reporte_ventas.txt";

    CelularDao daoC = new CelularDao();
    VentaDao daoV = new VentaDao();
    AuditoriaDao daoA = new AuditoriaDao();
    MenuAdministrador menuAdmin = new MenuAdministrador();

    // Solo las ventas pagadas cuentan como ventas reales
    private List<Ventaa> ventasPagadas() {
        return daoV.obtenerVentas().stream()
                .filter(v -> v.getEstado() == Ventaa.Estado.PAGADA)
                .collect(Collectors.toList());
    }

    private void stockBajo() {
        List<Celularr> bajos = daoC.obtenerCelulares().stream()
                .filter(c -> c.getStock() < STOCK_BAJO)
                .sorted(Comparator.comparingInt(Celularr::getStock))
                .collect(Collectors.toList());
        if (bajos.isEmpty()) {
            System.out.println("Ningun celular tiene menos de " + STOCK_BAJO + " unidades.");
            return;
        }
        System.out.println("\n--- Celulares con stock menor a " + STOCK_BAJO + " ---");
        System.out.printf("%-4s | %-28s | %5s%n", "Id", "Celular", "Stock");
        for (Celularr c : bajos) {
            System.out.printf("%-4d | %-28s | %5d%n", c.getId(),
                    c.getMarca().getNombre() + " " + c.getModelo(), c.getStock());
        }
    }

    private void masVendidos() {
        // Une los detalles de todas las ventas pagadas y suma las unidades por celular
        Map<String, Integer> unidades = ventasPagadas().stream()
                .flatMap(v -> v.getDetalles().stream())
                .collect(Collectors.groupingBy(
                        d -> d.getCelular().getMarca().getNombre() + " " + d.getCelular().getModelo(),
                        Collectors.summingInt(DetalleVentaa::getCantidad)));
        if (unidades.isEmpty()) {
            System.out.println("Aun no hay ventas pagadas.");
            return;
        }
        List<Map.Entry<String, Integer>> top = unidades.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("\n--- Top 3 celulares mas vendidos ---");
        for (int i = 0; i < top.size(); i++) {
            System.out.printf("%d. %-28s | %d unidades%n", i + 1, top.get(i).getKey(), top.get(i).getValue());
        }
    }

    private void ventasPorMes() {
        Map<YearMonth, DoubleSummaryStatistics> porMes = ventasPagadas().stream()
                .collect(Collectors.groupingBy(
                        v -> YearMonth.from(v.getFecha()),
                        TreeMap::new,
                        Collectors.summarizingDouble(Ventaa::getTotal)));
        if (porMes.isEmpty()) {
            System.out.println("Aun no hay ventas pagadas.");
            return;
        }
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("MM/yyyy");
        System.out.println("\n--- Ventas totales por mes (IVA incluido) ---");
        System.out.printf("%-8s | %6s | %18s%n", "Mes", "Ventas", "Total");
        porMes.forEach((mes, est) ->
                System.out.printf("%-8s | %6d | $%,17.2f%n", mes.format(formato), est.getCount(), est.getSum()));
    }

    // Escribe el resumen de todas las ventas en un archivo de texto
    private void generarArchivo() {
        List<Ventaa> ventas = daoV.obtenerVentas().stream()
                .sorted(Comparator.comparingInt(Ventaa::getId))
                .collect(Collectors.toList());
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas para reportar.");
            return;
        }
        Map<Ventaa.Estado, DoubleSummaryStatistics> porEstado = ventas.stream()
                .collect(Collectors.groupingBy(
                        Ventaa::getEstado,
                        () -> new EnumMap<>(Ventaa.Estado.class),
                        Collectors.summarizingDouble(Ventaa::getTotal)));
        double totalVendido = ventas.stream()
                .filter(v -> v.getEstado() == Ventaa.Estado.PAGADA)
                .mapToDouble(Ventaa::getTotal)
                .sum();

        Path ruta = Path.of(ARCHIVO);
        try (BufferedWriter w = Files.newBufferedWriter(ruta, StandardCharsets.UTF_8)) {
            w.write("TECNOSTORE - REPORTE DE VENTAS");
            w.newLine();
            w.write("Generado: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            w.newLine();
            for (Ventaa v : ventas) {
                w.write(v.toString());
                w.newLine();
            }
            w.newLine();
            w.write("================== RESUMEN ==================");
            w.newLine();
            for (Map.Entry<Ventaa.Estado, DoubleSummaryStatistics> e : porEstado.entrySet()) {
                w.write(String.format("%-10s: %d ventas, $%,.2f",
                        e.getKey(), e.getValue().getCount(), e.getValue().getSum()));
                w.newLine();
            }
            w.write(String.format("TOTAL VENDIDO (ventas pagadas): $%,.2f", totalVendido));
            w.newLine();
        } catch (IOException e) {
            System.out.println("No se pudo escribir el archivo: " + e.getMessage());
            return;
        }
        System.out.println("Reporte generado en: " + ruta.toAbsolutePath());
    }

    private void auditoria() {
        List<Auditoria> lista = daoA.obtenerRegistros();
        if (lista.isEmpty()) {
            System.out.println("No hay movimientos registrados.");
            return;
        }
        System.out.println("\n--- Ultimos movimientos ---");
        for (Auditoria a : lista) {
            System.out.println(a);
        }
    }

    public void menu() {
        int op;
        do {
            op = menuAdmin.escogerReporte();
            switch (op) {
                case 1 -> stockBajo();
                case 2 -> masVendidos();
                case 3 -> ventasPorMes();
                case 4 -> generarArchivo();
                case 5 -> auditoria();
                case 6 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion no encontrada!");
            }
        } while (op != 6);
    }
}