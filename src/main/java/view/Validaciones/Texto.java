package view.Validaciones;

import java.util.Scanner;


public class Texto {
    private final Scanner sc = new Scanner(System.in);
    public String validarTexto(String mensaje) {
        boolean validacion;
        String texto = "";
        Scanner x = new Scanner(System.in);
        int contador = 0;
        do {
            validacion = true;
            System.out.println(mensaje);
            texto = x.nextLine();
            for (int i = 0; i < texto.length(); i++) {
                contador += texto.charAt(i) == ' ' ? 1 : 0;
                if (!Character.isLetter(texto.charAt(i)) || texto.charAt(i) != ' ') {
                    if (texto.charAt(i) == ' ' && i == 0) {
                        validacion = false;
                        break;
                    }
                }
            }
        } while (validacion == false);
        return texto;
    }

}
