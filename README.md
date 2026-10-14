# ✿ TecnoStore — Sistema de gestión de celulares, clientes y ventas
---

## 🌷 Descripción

**TecnoStore** es una aplicación de consola desarrollada en Java para administrar las operaciones principales de una tienda minorista de teléfonos celulares. El sistema permite gestionar el inventario, los clientes y los pedidos, además de consultar reportes relacionados con las ventas.

El proyecto utiliza Java 17, Maven, MySQL y JDBC. Su organización separa las clases de modelo, controladores, acceso a datos y menús de consola para facilitar la lectura y el mantenimiento del código.

## 🎀 Funcionalidades

### 📱 Gestión de celulares
- Registrar celulares con marca, modelo, sistema operativo, gama, precio y stock.
- Listar los celulares registrados.
- Actualizar información del celular.
- Eliminar celulares.
- Registrar marcas nuevas cuando sea necesario.
- Validar datos ingresados mediante clases de validación.

### 🧸 Gestión de clientes y cuentas
- Registrar una cuenta e iniciar sesión.
- Consultar y actualizar datos personales.
- Ver el perfil del cliente.
- Eliminar una cuenta.
- Consultar y eliminar clientes desde el menú de administración.

### 🛍️ Gestión de pedidos y ventas
- Crear pedidos y agregar celulares al pedido.
- Actualizar pedidos, retirar celulares o cancelar pedidos.
- Consultar pedidos del cliente.
- Consultar ventas desde el menú de administración.
- Actualizar el estado de una venta: `PENDIENTE`, `PAGADA` o `CANCELADA`.
- Calcular subtotal, IVA del 19 % y total de la venta.

### 📊 Reportes y análisis
El módulo de reportes contempla:
- Celulares con stock bajo (menos de 5 unidades).
- Top 3 de celulares más vendidos, considerando las ventas pagadas.
- Totales de ventas pagadas agrupadas por mes.
- Generación del archivo `reporte_ventas.txt` con el resumen de las ventas.

Los reportes utilizan colecciones y operaciones de **Stream API** para filtrar, agrupar y resumir información.

## 🧰 Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| Java 17 | Lenguaje de programación |
| Maven | Gestión del proyecto y dependencias |
| MySQL | Base de datos relacional |
| JDBC / MySQL Connector/J | Conexión entre Java y MySQL |
| Java Collections y Stream API | Procesamiento de datos y reportes |
| Git y GitHub | Control de versiones |
| Apache NetBeans | Entorno de desarrollo utilizado |

## 🗂️ Estructura del proyecto

```text
TecnoStore/
├── pom.xml
├── scriptTecnoStoree.sql
├── README.md
└── src/
    └── main/
        └── java/
            ├── com/s2/tecnostore/
            │   └── TecnoStore.java
            ├── controller/
            │   ├── GestionGeneral.java
            │   ├── admin/
            │   │   ├── GestionAdministrador.java
            │   │   ├── GestionCelular.java
            │   │   ├── GestionCliente.java
            │   │   ├── GestionReporte.java
            │   │   └── GestionVenta.java
            │   └── cliente/
            │       ├── GestionCuenta.java
            │       └── GestionPedido.java
            ├── dao/
            │   ├── AdministradorDao.java
            │   ├── CelularDao.java
            │   ├── ClienteDao.java
            │   ├── Conexion.java
            │   ├── DetalleVentaDao.java
            │   ├── MarcaDao.java
            │   ├── PersonaDao.java
            │   └── VentaDao.java
            ├── model/
            │   ├── Administradorr.java
            │   ├── Celularr.java
            │   ├── Clientee.java
            │   ├── DetalleVentaa.java
            │   ├── Marcaa.java
            │   ├── Personaa.java
            │   └── Ventaa.java
            └── view/
                ├── MenuAdministrador.java
                ├── MenuCliente.java
                ├── MenuIngreso.java
                └── Validaciones/
                    ├── Confirmacion.java
                    ├── Correo.java
                    ├── Decimal.java
                    ├── DecimalPositivo.java
                    ├── Documento.java
                    ├── Entero.java
                    ├── EnteroGrande.java
                    ├── EnteroRango.java
                    ├── Fecha.java
                    ├── SoloLetras.java
                    ├── Telefono.java
                    └── Texto.java
```

### ✿ ¿Qué hace cada paquete?

- **`model`**: contiene las entidades del sistema, como celulares, clientes, personas, marcas, ventas y detalles de venta.
- **`controller`**: coordina el flujo de la aplicación y las operaciones disponibles para clientes y administradores.
- **`dao`**: contiene las clases de acceso a datos (*Data Access Object*) y la conexión con MySQL.
- **`view`**: presenta los menús de consola y las clases que validan las entradas del usuario.
- **`TecnoStore.java`**: punto de entrada de la aplicación.

## 🗃️ Base de datos

El script SQL incluido se llama **`scriptTecnoStoree.sql`** y crea la base de datos `tecnoStore_SaraRamirez`.

Incluye las siguientes tablas:
- `persona`
- `cliente`
- `administrador`
- `marca`
- `celular`
- `venta`
- `detalle_venta`

El script también incluye datos de prueba para facilitar la comprobación de los menús y reportes.

### Configurar MySQL

1. Instala e inicia MySQL Server.
2. Abre MySQL Workbench.
3. Abre el archivo `scriptTecnoStoree.sql`.
4. Ejecuta el script para crear la base de datos, las tablas y los datos de prueba.
5. Revisa la configuración de conexión de la clase `dao/Conexion.java` y ajústala a tu instalación local.
6. Confirma que el usuario de MySQL tenga permisos sobre la base de datos.

## ▶️ Requisitos y ejecución

### Requisitos previos
- JDK 17 o una versión compatible con la configuración de Maven.
- MySQL Server.
- Apache NetBeans o un IDE compatible con Maven.
- Conexión JDBC mediante la dependencia MySQL Connector/J, declarada en `pom.xml`.

### Ejecutar desde NetBeans
1. Descomprime el proyecto.
2. Abre NetBeans y selecciona **File → Open Project**.
3. Selecciona la carpeta `TecnoStore` que contiene `pom.xml`.
4. Configura y ejecuta el script SQL en MySQL.
5. Ajusta los datos de conexión en `dao/Conexion.java`.
6. Ejecuta la clase principal `com.s2.tecnostore.TecnoStore`.

### Ejecutar desde una terminal
Desde la carpeta raíz del proyecto, con Java y Maven disponibles:
ejecuta la clase principal desde NetBeans o configura el complemento Maven Exec para tu instalación.

## 🖥️ Ejemplo del menú

La aplicación inicia con un menú general para seleccionar el tipo de acceso. Desde el menú de administración se pueden gestionar clientes, celulares, ventas y reportes. Desde el menú de cliente se pueden administrar la cuenta y los pedidos.

Ejemplo ilustrativo del menú de administración:

```text
========================================
               TECNOSTORE
          ¿Qué área deseas gestionar?
========================================
  1. Gestionar Clientes
  2. Gestionar Celulares
  3. Gestionar Ventas
  4. Reportes y Análisis
  5. Salir
========================================
```

## 🧩 Conceptos de programación aplicados

- **Programación Orientada a Objetos:** entidades representadas mediante clases y objetos.
- **Encapsulamiento:** atributos privados y métodos de acceso.
- **Enumeraciones (`enum`):** opciones controladas para sistema operativo, gama y estado de venta.
- **DAO:** separación del acceso a datos respecto a la lógica de los menús.
- **Singleton:** la clase `Conexion` utiliza una instancia compartida para administrar el acceso al objeto de conexión.
- **Colecciones y Stream API:** procesamiento y agrupación de datos para los reportes.
- **Manejo de excepciones y validaciones:** control de entradas y errores de las operaciones.

## 📄 Archivo de reporte

Al generar el reporte, el sistema crea `reporte_ventas.txt` en el directorio de trabajo desde el que se ejecuta la aplicación. El archivo contiene las ventas recuperadas de la base de datos, su estado y un resumen de los totales.

## 📸 Evidencias

Agrega aquí capturas reales de la ejecución cuando estén disponibles:

- Menú general.
- Inicio de sesión o registro de cliente.
- Gestión del inventario.
- Gestión de pedidos y ventas.
- Reportes en consola.
- Contenido de `reporte_ventas.txt`.

## 🔗 Repositorio

**GitHub:** [Agrega aquí la URL de tu repositorio]

---
<p align="center">✿ Gracias por visitar TecnoStore ✿</p>
