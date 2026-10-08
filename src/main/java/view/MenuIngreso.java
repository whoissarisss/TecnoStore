package view;

public class MenuIngreso {
    Validaciones v = new Validaciones();

    public Producto ingresarProducto() {
        return new Producto(0, v.validarTexto("Ingrese el nombre"),
                v.validarTexto("Ingrese la descripcion"),
                v.validarDecimal("Ingrese el monto de los ingredientes"),
                v.validarDecimal("Ingrese la mano de obra"));
    }

    public Precio ingresarPrecio() {
        return new Precio(0, v.validarTexto("Ingrese el nombre"),
                v.validarDecimal("Ingrese el monto"),
                null);
    }

    public int escogerPersona() {

        return v.validarEntero("""
                               1. Administrador
                               2. Cliente
                               3. Salir.
                               """);

    }
}
