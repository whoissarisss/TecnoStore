package view.Validaciones;

import java.util.Scanner;

public class Entero {
    private final Scanner sc = new Scanner(System.in);
    public int validarEntero(String mensaje) {
        Scanner x = new Scanner(System.in);
        System.out.println(mensaje);
        while (!x.hasNextInt()) {
            System.out.println("Error, se espera un valor entero");
            x.next();
        }
        return x.nextInt();
    }

}
