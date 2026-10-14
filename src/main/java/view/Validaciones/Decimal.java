package view.Validaciones;

import java.util.Scanner;

/**
 *
 * @author saras
 */

public class Decimal {

    public double validarDecimal(String mensaje) {
        Scanner x = new Scanner(System.in);
        while (true) {
            System.out.println(mensaje);
            // acepta 3500.50 y 3500,50
            String t = x.nextLine().trim().replace(',', '.');
            if (t.matches("-?\\d+(\\.\\d+)?")) {
                return Double.parseDouble(t);
            }
            System.out.println("Error, se espera un valor decimal");
        }
    }
}