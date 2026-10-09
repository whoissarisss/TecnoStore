package view.Validaciones;

import java.util.Scanner;

public class EnteroGrande {
    private final Scanner sc = new Scanner(System.in);
     public long validarEnteroGrande(String mensaje) {
        Scanner x = new Scanner(System.in);
        System.out.println(mensaje);
        while (!x.hasNextLong()) {
            System.out.println("Error, se espera un valor entero");
            x.next();
        }
        return x.nextLong();
    }
}
