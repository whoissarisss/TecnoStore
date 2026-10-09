package view.Validaciones;

public class DecimalPositivo extends Decimal {
    public double validarDecimalPositivo(String mensaje) {
        double d;
        do {
            d = validarDecimal(mensaje);
            if (d <= 0) {
                System.out.println("Error, debe ser mayor que 0");
            }
        } while (d <= 0);
        return d;
    }
}
