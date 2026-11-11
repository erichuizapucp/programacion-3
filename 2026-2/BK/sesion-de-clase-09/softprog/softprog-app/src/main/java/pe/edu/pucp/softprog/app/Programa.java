package pe.edu.pucp.softprog.app;

import pe.edu.pucp.softprog.bl.AreaBL;
import pe.edu.pucp.softprog.bl.BLException;
import pe.edu.pucp.softprog.bl.ClienteBL;
import pe.edu.pucp.softprog.bl.CuentaUsuarioBL;
import pe.edu.pucp.softprog.bl.EmpleadoBL;
import pe.edu.pucp.softprog.bl.OrdenVentaBL;
import pe.edu.pucp.softprog.bl.ProductoBL;
import pe.edu.pucp.softprog.bl.impl.AreaBLImpl;
import pe.edu.pucp.softprog.bl.impl.ClienteBLImpl;
import pe.edu.pucp.softprog.bl.impl.CuentaUsuarioBLImpl;
import pe.edu.pucp.softprog.bl.impl.EmpleadoBLImpl;
import pe.edu.pucp.softprog.bl.impl.OrdenVentaBLImpl;
import pe.edu.pucp.softprog.bl.impl.ProductoBLImpl;
import pe.edu.pucp.softprog.modelo.Genero;
import pe.edu.pucp.softprog.modelo.almacen.Producto;
import pe.edu.pucp.softprog.modelo.almacen.UnidadMedida;
import pe.edu.pucp.softprog.modelo.rrhh.Area;
import pe.edu.pucp.softprog.modelo.rrhh.Cargo;
import pe.edu.pucp.softprog.modelo.rrhh.Empleado;
import pe.edu.pucp.softprog.modelo.seguridad.CuentaUsuario;
import pe.edu.pucp.softprog.modelo.ventas.CategoriaCliente;
import pe.edu.pucp.softprog.modelo.ventas.Cliente;
import pe.edu.pucp.softprog.modelo.ventas.LineaOrdenVenta;
import pe.edu.pucp.softprog.modelo.ventas.OrdenVenta;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Programa {

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final long RUN = System.currentTimeMillis() % 1_000_000L;

    private static long dniSeq = 10_000_000L + (System.currentTimeMillis() % 80_000_000L);

    private static final AreaBL areaBL = new AreaBLImpl();
    private static final CuentaUsuarioBL cuentaUsuarioBL = new CuentaUsuarioBLImpl();
    private static final EmpleadoBL empleadoBL = new EmpleadoBLImpl();
    private static final ClienteBL clienteBL = new ClienteBLImpl();
    private static final ProductoBL productoBL = new ProductoBLImpl();
    private static final OrdenVentaBL ordenVentaBL = new OrdenVentaBLImpl();

    public static void main(String[] args) {
        try {
            probarAreas();
            probarCuentasUsuario();
            probarEmpleados();
            probarClientes();
            probarProductos();
            probarOrdenesVenta();
            probarReglasDeNegocio();
        } catch (BLException ex) {
            System.out.println("La prueba se detuvo por un error: " + ex.getMessage());
        }
    }

    private static void probarAreas() throws BLException {
        titulo("AREA");

        Area area = new Area();
        area.setNombre("Área de Prueba " + RUN);
        area.setActivo(true);
        areaBL.insert(area);
        System.out.println("Insertada:   " + describir(area));

        area = areaBL.findById(area.getId());
        System.out.println("Recuperada:  " + describir(area));

        area.setNombre("Área de Prueba " + RUN + " (editada)");
        area.setActivo(false);
        areaBL.update(area);
        System.out.println("Actualizada: " + describir(areaBL.findById(area.getId())));

        areaBL.delete(area.getId());
        System.out.println("Eliminada:   id " + area.getId());

        listarAreas(areaBL.findAll());
    }

    private static void probarCuentasUsuario() throws BLException {
        titulo("CUENTA USUARIO");

        CuentaUsuario cuenta = new CuentaUsuario();
        cuenta.setUserName("prueba." + RUN + "@softprog.pe");
        cuenta.setPassword("prueba123");
        cuenta.setActivo(true);
        cuentaUsuarioBL.insert(cuenta);
        System.out.println("Insertada:   " + describir(cuenta));

        cuenta = cuentaUsuarioBL.findById(cuenta.getId());
        System.out.println("Recuperada:  " + describir(cuenta));

        cuenta.setUserName("prueba.editada." + RUN + "@softprog.pe");
        cuenta.setPassword("nueva456");
        cuenta.setActivo(false);
        cuentaUsuarioBL.update(cuenta);
        System.out.println("Actualizada: " + describir(cuentaUsuarioBL.findById(cuenta.getId())));

        cuentaUsuarioBL.delete(cuenta.getId());
        System.out.println("Eliminada:   id " + cuenta.getId());

        listarCuentas(cuentaUsuarioBL.findAll());
    }

    private static void probarEmpleados() throws BLException {
        titulo("EMPLEADO");

        Area area = new Area();
        area.setNombre("Almacén " + RUN);
        area.setActivo(true);
        areaBL.insert(area);

        CuentaUsuario cuenta = new CuentaUsuario();
        cuenta.setUserName("empleado." + RUN + "@softprog.pe");
        cuenta.setPassword("demo123");
        cuenta.setActivo(true);
        cuentaUsuarioBL.insert(cuenta);

        Empleado empleado = new Empleado();
        empleado.setArea(area);
        empleado.setCuentaUsuario(cuenta);
        empleado.setDni(nuevoDni());
        empleado.setNombre("Elena");
        empleado.setApellidoPaterno("Prueba");
        empleado.setGenero(Genero.FEMENINO);
        empleado.setFechaNacimiento(LocalDate.of(1995, 5, 12));
        empleado.setCargo(Cargo.EJECUTIVO_COMERCIAL);
        empleado.setSueldo(3100.00);
        empleado.setActivo(true);
        empleadoBL.insert(empleado);
        System.out.println("Insertado:   " + describir(empleado));

        empleado = empleadoBL.findById(empleado.getId());
        System.out.println("Recuperado:  " + describir(empleado));

        empleado.setApellidoPaterno("Prueba Editada");
        empleado.setCargo(Cargo.VENDEDOR_SENIOR);
        empleado.setSueldo(3550.50);
        empleado.setActivo(false);
        empleadoBL.update(empleado);
        System.out.println("Actualizado: " + describir(empleadoBL.findById(empleado.getId())));

        empleadoBL.delete(empleado.getId());
        System.out.println("Eliminado:   id " + empleado.getId());

        listarEmpleados(empleadoBL.findAll());

        cuentaUsuarioBL.delete(cuenta.getId());
        areaBL.delete(area.getId());
    }

    private static void probarClientes() throws BLException {
        titulo("CLIENTE");

        CuentaUsuario cuenta = new CuentaUsuario();
        cuenta.setUserName("cliente." + RUN + "@softprog.pe");
        cuenta.setPassword("demo123");
        cuenta.setActivo(true);
        cuentaUsuarioBL.insert(cuenta);

        Cliente cliente = new Cliente();
        cliente.setCuentaUsuario(cuenta);
        cliente.setDni(nuevoDni());
        cliente.setNombre("Marco");
        cliente.setApellidoPaterno("Prueba");
        cliente.setGenero(Genero.MASCULINO);
        cliente.setFechaNacimiento(LocalDate.of(1990, 8, 20));
        cliente.setCategoria(CategoriaCliente.PLATA);
        cliente.setLineaCredito(5000.00);
        cliente.setActivo(true);
        clienteBL.insert(cliente);
        System.out.println("Insertado:   " + describir(cliente));

        cliente = clienteBL.findById(cliente.getId());
        System.out.println("Recuperado:  " + describir(cliente));

        cliente.setApellidoPaterno("Prueba Editada");
        cliente.setCategoria(CategoriaCliente.ORO);
        cliente.setLineaCredito(8200.75);
        cliente.setActivo(false);
        clienteBL.update(cliente);
        System.out.println("Actualizado: " + describir(clienteBL.findById(cliente.getId())));

        clienteBL.delete(cliente.getId());
        System.out.println("Eliminado:   id " + cliente.getId());

        listarClientes(clienteBL.findAll());

        cuentaUsuarioBL.delete(cuenta.getId());
    }

    private static void probarProductos() throws BLException {
        titulo("PRODUCTO");

        Producto producto = new Producto();
        producto.setNombre("Cuaderno A4 " + RUN);
        producto.setUnidadMedida(UnidadMedida.UND);
        producto.setPrecio(12.50);
        producto.setActivo(true);
        productoBL.insert(producto);
        System.out.println("Insertado:   " + describir(producto));

        producto = productoBL.findById(producto.getId());
        System.out.println("Recuperado:  " + describir(producto));

        producto.setNombre("Cuaderno A4 cuadriculado " + RUN);
        producto.setPrecio(14.90);
        producto.setActivo(false);
        productoBL.update(producto);
        System.out.println("Actualizado: " + describir(productoBL.findById(producto.getId())));

        productoBL.delete(producto.getId());
        System.out.println("Eliminado:   id " + producto.getId());

        listarProductos(productoBL.findAll());
    }

    private static void probarOrdenesVenta() throws BLException {
        titulo("ORDEN DE VENTA");

        Area area = new Area();
        area.setNombre("Ventas " + RUN);
        area.setActivo(true);
        areaBL.insert(area);

        CuentaUsuario cuentaEmpleado = new CuentaUsuario();
        cuentaEmpleado.setUserName("vendedor." + RUN + "@softprog.pe");
        cuentaEmpleado.setPassword("demo123");
        cuentaEmpleado.setActivo(true);
        cuentaUsuarioBL.insert(cuentaEmpleado);

        Empleado empleado = new Empleado();
        empleado.setArea(area);
        empleado.setCuentaUsuario(cuentaEmpleado);
        empleado.setDni(nuevoDni());
        empleado.setNombre("Rosa");
        empleado.setApellidoPaterno("Vendedora");
        empleado.setGenero(Genero.FEMENINO);
        empleado.setFechaNacimiento(LocalDate.of(1992, 3, 4));
        empleado.setCargo(Cargo.VENDEDOR_SENIOR);
        empleado.setSueldo(4200.00);
        empleado.setActivo(true);
        empleadoBL.insert(empleado);

        CuentaUsuario cuentaCliente = new CuentaUsuario();
        cuentaCliente.setUserName("comprador." + RUN + "@softprog.pe");
        cuentaCliente.setPassword("demo123");
        cuentaCliente.setActivo(true);
        cuentaUsuarioBL.insert(cuentaCliente);

        Cliente cliente = new Cliente();
        cliente.setCuentaUsuario(cuentaCliente);
        cliente.setDni(nuevoDni());
        cliente.setNombre("Julio");
        cliente.setApellidoPaterno("Comprador");
        cliente.setGenero(Genero.MASCULINO);
        cliente.setFechaNacimiento(LocalDate.of(1988, 11, 30));
        cliente.setCategoria(CategoriaCliente.ORO);
        cliente.setLineaCredito(10000.00);
        cliente.setActivo(true);
        clienteBL.insert(cliente);

        Producto lapicero = new Producto();
        lapicero.setNombre("Lapicero azul " + RUN);
        lapicero.setUnidadMedida(UnidadMedida.UND);
        lapicero.setPrecio(2.50);
        lapicero.setActivo(true);
        productoBL.insert(lapicero);

        Producto resma = new Producto();
        resma.setNombre("Resma papel bond " + RUN);
        resma.setUnidadMedida(UnidadMedida.UND);
        resma.setPrecio(25.00);
        resma.setActivo(true);
        productoBL.insert(resma);

        LineaOrdenVenta linea1 = new LineaOrdenVenta();
        linea1.setProducto(lapicero);
        linea1.setCantidad(10);
        linea1.setActivo(true);

        LineaOrdenVenta linea2 = new LineaOrdenVenta();
        linea2.setProducto(resma);
        linea2.setCantidad(3);
        linea2.setActivo(true);

        OrdenVenta orden = new OrdenVenta();
        orden.setEmpleado(empleado);
        orden.setCliente(cliente);
        orden.setLineas(List.of(linea1, linea2));
        orden.setActivo(true);
        ordenVentaBL.insert(orden);
        System.out.println("Insertada:   " + describir(orden));
        System.out.printf("Total calculado por la BL (esperado S/ 100.00): S/ %.2f%n",
                orden.getTotal());

        orden = ordenVentaBL.findById(orden.getId());
        System.out.println("Recuperada:  " + describir(orden));

        LineaOrdenVenta lineaExtra = new LineaOrdenVenta();
        lineaExtra.setProducto(lapicero);
        lineaExtra.setCantidad(5);
        lineaExtra.setActivo(true);

        orden.setLineas(List.of(linea1, linea2, lineaExtra));
        orden.setActivo(false);
        ordenVentaBL.update(orden);
        OrdenVenta ordenActualizada = ordenVentaBL.findById(orden.getId());
        System.out.println("Actualizada: " + describir(ordenActualizada));
        System.out.printf("Total recalculado por la BL (esperado S/ 112.50): S/ %.2f%n",
                ordenActualizada.getTotal());

        ordenVentaBL.delete(orden.getId());
        System.out.println("Eliminada:   id " + orden.getId());

        listarOrdenes(ordenVentaBL.findAll());

        productoBL.delete(lapicero.getId());
        productoBL.delete(resma.getId());
        clienteBL.delete(cliente.getId());
        empleadoBL.delete(empleado.getId());
        cuentaUsuarioBL.delete(cuentaCliente.getId());
        cuentaUsuarioBL.delete(cuentaEmpleado.getId());
        areaBL.delete(area.getId());
    }

    private static void probarReglasDeNegocio() {
        titulo("REGLAS DE NEGOCIO");

        try {
            Producto producto = new Producto();
            producto.setNombre("Producto sin precio " + RUN);
            producto.setUnidadMedida(UnidadMedida.UND);
            producto.setPrecio(0);
            producto.setActivo(true);
            productoBL.insert(producto);
            System.out.println("Producto con precio 0: NO se rechazó (falla la regla)");
        } catch (BLException ex) {
            System.out.println("Producto con precio 0 -> Rechazado: " + ex.getMessage());
        }

        try {
            String nombre = "Área repetida " + RUN;
            Area area = new Area();
            area.setNombre(nombre);
            area.setActivo(true);
            areaBL.insert(area);
            try {
                Area repetida = new Area();
                repetida.setNombre(nombre);
                repetida.setActivo(true);
                areaBL.insert(repetida);
                System.out.println("Área con nombre repetido: NO se rechazó (falla la regla)");
            } catch (BLException ex) {
                System.out.println("Área con nombre repetido -> Rechazado: " + ex.getMessage());
            }
            areaBL.delete(area.getId());
        } catch (BLException ex) {
            System.out.println("No se pudo preparar la prueba de área repetida: " + ex.getMessage());
        }

        try {
            Empleado empleado = empleadoDemo();
            empleado.setDni("123");
            empleadoBL.insert(empleado);
            System.out.println("Empleado con DNI inválido: NO se rechazó (falla la regla)");
        } catch (BLException ex) {
            System.out.println("Empleado con DNI inválido -> Rechazado: " + ex.getMessage());
        }

        try {
            Cliente cliente = clienteDemo();
            cliente.setCategoria(CategoriaCliente.BRONCE);
            cliente.setLineaCredito(5000.00);
            clienteBL.insert(cliente);
            System.out.println("Cliente BRONCE con crédito alto: NO se rechazó (falla la regla)");
        } catch (BLException ex) {
            System.out.println("Cliente BRONCE con crédito alto -> Rechazado: " + ex.getMessage());
        }

        try {
            OrdenVenta orden = new OrdenVenta();
            orden.setActivo(true);
            ordenVentaBL.insert(orden);
            System.out.println("Orden de venta sin líneas: NO se rechazó (falla la regla)");
        } catch (BLException ex) {
            System.out.println("Orden de venta sin líneas -> Rechazado: " + ex.getMessage());
        }
    }

    private static Empleado empleadoDemo() {
        Area area = new Area();
        area.setNombre("Área temporal " + RUN);
        area.setActivo(true);

        CuentaUsuario cuenta = new CuentaUsuario();
        cuenta.setUserName("temporal." + RUN + "@softprog.pe");
        cuenta.setPassword("demo123");
        cuenta.setActivo(true);

        Empleado empleado = new Empleado();
        empleado.setArea(area);
        empleado.setCuentaUsuario(cuenta);
        empleado.setDni("70000001");
        empleado.setNombre("Temporal");
        empleado.setApellidoPaterno("Demo");
        empleado.setGenero(Genero.MASCULINO);
        empleado.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        empleado.setCargo(Cargo.TECNICO);
        empleado.setSueldo(2000.00);
        empleado.setActivo(true);
        return empleado;
    }

    private static Cliente clienteDemo() {
        CuentaUsuario cuenta = new CuentaUsuario();
        cuenta.setUserName("temporal.cliente." + RUN + "@softprog.pe");
        cuenta.setPassword("demo123");
        cuenta.setActivo(true);

        Cliente cliente = new Cliente();
        cliente.setCuentaUsuario(cuenta);
        cliente.setDni("70000002");
        cliente.setNombre("Temporal");
        cliente.setApellidoPaterno("Demo");
        cliente.setGenero(Genero.FEMENINO);
        cliente.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        cliente.setCategoria(CategoriaCliente.BRONCE);
        cliente.setLineaCredito(0.00);
        cliente.setActivo(true);
        return cliente;
    }

    private static String nuevoDni() {
        return Long.toString(++dniSeq);
    }

    private static void listarAreas(List<Area> areas) {
        System.out.println("Listado (" + areas.size() + "):");
        for (Area area : areas) {
            System.out.println("  " + describir(area));
        }
    }

    private static void listarCuentas(List<CuentaUsuario> cuentas) {
        System.out.println("Listado (" + cuentas.size() + "):");
        for (CuentaUsuario cuenta : cuentas) {
            System.out.println("  " + describir(cuenta));
        }
    }

    private static void listarEmpleados(List<Empleado> empleados) {
        System.out.println("Listado (" + empleados.size() + "):");
        for (Empleado empleado : empleados) {
            System.out.println("  " + describir(empleado));
        }
    }

    private static void listarClientes(List<Cliente> clientes) {
        System.out.println("Listado (" + clientes.size() + "):");
        for (Cliente cliente : clientes) {
            System.out.println("  " + describir(cliente));
        }
    }

    private static void listarProductos(List<Producto> productos) {
        System.out.println("Listado (" + productos.size() + "):");
        for (Producto producto : productos) {
            System.out.println("  " + describir(producto));
        }
    }

    private static void listarOrdenes(List<OrdenVenta> ordenes) {
        System.out.println("Listado (" + ordenes.size() + "):");
        for (OrdenVenta orden : ordenes) {
            System.out.println("  " + describir(orden));
        }
    }

    private static void titulo(String nombre) {
        System.out.println();
        System.out.println("=== " + nombre + " ===");
    }

    private static String describir(Area area) {
        return String.format("[%d] %-32s activo=%s",
                area.getId(), area.getNombre(), area.isActivo());
    }

    private static String describir(CuentaUsuario cuenta) {
        return String.format("[%d] %-34s %-12s activo=%s",
                cuenta.getId(), cuenta.getUserName(), cuenta.getPassword(), cuenta.isActivo());
    }

    private static String describir(Empleado empleado) {
        return String.format("[%d] %s %s  dni=%s  %s  nac=%s  area=%s  %s  S/ %.2f  activo=%s",
                empleado.getId(),
                empleado.getNombre(),
                empleado.getApellidoPaterno(),
                empleado.getDni(),
                empleado.getGenero(),
                FORMATO_FECHA.format(empleado.getFechaNacimiento()),
                empleado.getArea().getNombre(),
                empleado.getCargo(),
                empleado.getSueldo(),
                empleado.isActivo());
    }

    private static String describir(Cliente cliente) {
        return String.format("[%d] %s %s  dni=%s  %s  nac=%s  categoria=%s  credito=S/ %.2f  activo=%s",
                cliente.getId(),
                cliente.getNombre(),
                cliente.getApellidoPaterno(),
                cliente.getDni(),
                cliente.getGenero(),
                FORMATO_FECHA.format(cliente.getFechaNacimiento()),
                cliente.getCategoria(),
                cliente.getLineaCredito(),
                cliente.isActivo());
    }

    private static String describir(Producto producto) {
        return String.format("[%d] %-32s %-6s S/ %.2f  activo=%s",
                producto.getId(),
                producto.getNombre(),
                producto.getUnidadMedida(),
                producto.getPrecio(),
                producto.isActivo());
    }

    private static String describir(OrdenVenta orden) {
        String cliente = orden.getCliente() != null
                ? orden.getCliente().getNombre() + " " + orden.getCliente().getApellidoPaterno()
                : "(sin cliente)";
        String empleado = orden.getEmpleado() != null
                ? orden.getEmpleado().getNombre() + " " + orden.getEmpleado().getApellidoPaterno()
                : "(sin empleado)";
        return String.format("[%d] cliente=%s  empleado=%s  lineas=%d  total=S/ %.2f  activo=%s",
                orden.getId(),
                cliente,
                empleado,
                orden.getLineas().size(),
                orden.getTotal(),
                orden.isActivo());
    }
}
