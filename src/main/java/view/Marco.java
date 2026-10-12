package view;

public final class Marco {

    private static final int ANCHO = 38;
    private static final int ETIQUETA = 8;   // ancho de la etiqueta en las tarjetas

    private Marco() {}

    // Menu numerado con pregunta al final (pregunta puede llevar "\n")
    public static String menu(String subtitulo, String pregunta, String... opciones) {
        StringBuilder sb = new StringBuilder();
        sb.append(encabezado(subtitulo));
        sb.append(vacia());
        for (int i = 0; i < opciones.length; i++) {
            sb.append(linea("   " + (i + 1) + ".  " + opciones[i]));
        }
        sb.append(vacia());
        sb.append(separador());
        String[] partes = pregunta.split("\n");
        for (int i = 0; i < partes.length; i++) {
            sb.append(linea((i == 0 ? "   > " : "     ") + partes[i]));
        }
        sb.append(linea("     Ingresa una opción:"));
        sb.append(cierre());
        return sb.toString();
    }

    // Tarjeta de datos: una fila por cada par etiqueta / valor
    public static String tarjeta(String subtitulo, String[]... filas) {
        StringBuilder sb = new StringBuilder();
        sb.append(encabezado(subtitulo));
        sb.append(vacia());
        for (String[] f : filas) {
            sb.append(linea("   * " + ajustar(f[0], ETIQUETA) + ": " + f[1]));
        }
        sb.append(vacia());
        sb.append(cierre());
        return sb.toString();
    }

    // Ayuda para armar las filas de una tarjeta
    public static String[] fila(String etiqueta, Object valor) {
        return new String[]{etiqueta, String.valueOf(valor)};
    }

    // Texto a la izquierda y valor a la derecha (para recibos)
    public static String par(String izq, String der) {
        int disponible = ANCHO - 5;                    // 3 de margen izquierdo y 2 derecho
        int maxIzq = disponible - der.length() - 1;
        if (izq.length() > maxIzq) {
            izq = izq.substring(0, Math.max(0, maxIzq - 1)) + "…";
        }
        int hueco = disponible - izq.length() - der.length();
        return linea("   " + izq + " ".repeat(hueco) + der);
    }

    public static String encabezado(String subtitulo) {
        return "╭" + "─".repeat(ANCHO) + "╮\n"
                + linea(centrar("~ T E C N O S T O R E ~"))
                + linea(centrar("── " + subtitulo + " ──"))
                + separador();
    }

    public static String separador() {
        return "├" + "─".repeat(ANCHO) + "┤\n";
    }

    public static String cierre() {
        return "╰" + "─".repeat(ANCHO) + "╯\n";
    }

    public static String vacia() {
        return linea("");
    }

    public static String linea(String contenido) {
        return "│" + ajustar(contenido, ANCHO) + "│\n";
    }

    private static String centrar(String s) {
        return " ".repeat(Math.max(0, (ANCHO - s.length()) / 2)) + s;
    }

    // Recorta con "…" si no cabe, o rellena con espacios si sobra
    private static String ajustar(String s, int n) {
        if (s.length() > n) {
            return s.substring(0, n - 1) + "…";
        }
        return s + " ".repeat(n - s.length());
    }
}