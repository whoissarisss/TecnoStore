package view.Validaciones;

import java.util.Scanner;

/**
 *
 * @author saras
 */
public class Decimal {
    private final Scanner sc = new Scanner(System.in);
    public double validarDecimal(String mensaje) {
        System.out.println(mensaje);
        Scanner x = new Scanner(System.in);
        while (!x.hasNextDouble()) {
            System.out.println("Error, se espera un valor decimal");
        }
        return x.nextDouble();
    }
}
