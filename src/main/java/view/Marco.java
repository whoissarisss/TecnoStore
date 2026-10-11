package view;


public final class Marco {
    private static final int ANCHO = 38;

    private Marco() {}

    // pregunta puede llevar "\n" si ocupa más de una línea
    public static String menu(String subtitulo, String pregunta, String... opciones) {
        StringBuilder sb = new StringBuilder();
        sb.append("╭").append("─".repeat(ANCHO)).append("╮\n");
        sb.append(linea(centrar("⋆｡°✩  T E C N O S T O R E  ✩°｡⋆")));
        sb.append(linea(centrar("── " + subtitulo + " ──")));
        sb.append("├").append("─".repeat(ANCHO)).append("┤\n");
        sb.append(linea(""));
        for (int i = 0; i < opciones.length; i++) {
            sb.append(linea("   " + (i + 1) + ".  " + opciones[i]));
        }
        sb.append(linea(""));
        sb.append("├").append("─".repeat(ANCHO)).append("┤\n");
        String[] partes = pregunta.split("\n");
        for (int i = 0; i < partes.length; i++) {
            sb.append(linea((i == 0 ? "   ▸ " : "     ") + partes[i]));
        }
        sb.append(linea("     Ingresa una opción:"));
        sb.append("╰").append("─".repeat(ANCHO)).append("╯\n");
        return sb.toString();
    }

    private static String linea(String contenido) {
        return "│" + ajustar(contenido, ANCHO) + "│\n";
    }

    private static String centrar(String s) {
        return " ".repeat(Math.max(0, (ANCHO - s.length()) / 2)) + s;
    }

    private static String ajustar(String s, int n) {
        if (s.length() > n) return s.substring(0, n - 1) + "…";
        return s + " ".repeat(n - s.length());
    }
}

