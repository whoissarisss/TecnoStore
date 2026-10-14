package view.Validaciones;

import java.util.Scanner;

public class Texto {

    public String validarTexto(String mensaje) {
        Scanner x = new Scanner(System.in);
        while (true) {
            System.out.println(mensaje);
            String texto = x.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("Error, no puede estar vacío");
            } else if (texto.length() > 80) {
                System.out.println("Error, máximo 80 caracteres");
            } else {
                return texto.replaceAll("\\s+", " ");
            }
        }
    }
}