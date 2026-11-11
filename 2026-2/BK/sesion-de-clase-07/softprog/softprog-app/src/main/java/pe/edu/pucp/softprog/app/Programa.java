package pe.edu.pucp.softprog.app;

import pe.edu.pucp.softprog.dao.AreaDAO;
import pe.edu.pucp.softprog.dao.ClienteDAO;
import pe.edu.pucp.softprog.dao.CuentaUsuarioDAO;
import pe.edu.pucp.softprog.dao.EmpleadoDAO;
import pe.edu.pucp.softprog.dao.ProductoDAO;
import pe.edu.pucp.softprog.dao.impl.AreaDAOImpl;
import pe.edu.pucp.softprog.dao.impl.ClienteDAOImpl;
import pe.edu.pucp.softprog.dao.impl.CuentaUsuarioDAOImpl;
import pe.edu.pucp.softprog.dao.impl.EmpleadoDAOImpl;
import pe.edu.pucp.softprog.dao.impl.ProductoDAOImpl;
import pe.edu.pucp.softprog.modelo.Genero;
import pe.edu.pucp.softprog.modelo.almacen.Producto;
import pe.edu.pucp.softprog.modelo.almacen.UnidadMedida;
import pe.edu.pucp.softprog.modelo.rrhh.Area;
import pe.edu.pucp.softprog.modelo.rrhh.Cargo;
import pe.edu.pucp.softprog.modelo.rrhh.Empleado;
import pe.edu.pucp.softprog.modelo.seguridad.CuentaUsuario;
import pe.edu.pucp.softprog.modelo.ventas.CategoriaCliente;
import pe.edu.pucp.softprog.modelo.ventas.Cliente;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Programa {

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final AreaDAO areaDAO = new AreaDAOImpl();
    private static final CuentaUsuarioDAO cuentaUsuarioDAO = new CuentaUsuarioDAOImpl();
    private static final EmpleadoDAO empleadoDAO = new EmpleadoDAOImpl();
    private static final ClienteDAO clienteDAO = new ClienteDAOImpl();
    private static final ProductoDAO productoDAO = new ProductoDAOImpl();

    public static void main(String[] args) {
        try {
            probarAreas();
            probarCuentasUsuario();
            probarEmpleados();
            probarClientes();
            probarProductos();
        } catch (SQLException ex) {
            System.out.println("La prueba se detuvo por un error: " + ex.getMessage());
        }
    }

    private static void probarAreas() throws SQLException {
        titulo("AREA");

        Area area = new Area();
        area.setNombre("Area de Prueba");
        area.setActivo(true);
        areaDAO.insert(area);
        System.out.println("Insertada:   " + describir(area));

        area = areaDAO.findById(area.getId());
        System.out.println("Recuperada:  " + describir(area));

        area.setNombre("Area de Prueba (editada)");
        area.setActivo(false);
        areaDAO.update(area);
        System.out.println("Actualizada: " + describir(areaDAO.findById(area.getId())));

        areaDAO.delete(area.getId());
        System.out.println("Eliminada:   id " + area.getId());

        listarAreas(areaDAO.findAll());
    }

    private static void probarCuentasUsuario() throws SQLException {
        titulo("CUENTA USUARIO");

        CuentaUsuario cuenta = new CuentaUsuario();
        cuenta.setUserName("prueba@softprog.pe");
        cuenta.setPassword("prueba123");
        cuenta.setActivo(true);
        cuentaUsuarioDAO.insert(cuenta);
        System.out.println("Insertada:   " + describir(cuenta));

        cuenta = cuentaUsuarioDAO.findById(cuenta.getId());
        System.out.println("Recuperada:  " + describir(cuenta));

        cuenta.setUserName("prueba.editada@softprog.pe");
        cuenta.setPassword("nueva456");
        cuenta.setActivo(false);
        cuentaUsuarioDAO.update(cuenta);
        System.out.println("Actualizada: " + describir(cuentaUsuarioDAO.findById(cuenta.getId())));

        cuentaUsuarioDAO.delete(cuenta.getId());
        System.out.println("Eliminada:   id " + cuenta.getId());

        listarCuentas(cuentaUsuarioDAO.findAll());
    }

    private static void probarEmpleados() throws SQLException {
        titulo("EMPLEADO");

        Area area = new Area();
        area.setNombre("Almacen");
        area.setActivo(true);
        areaDAO.insert(area);

        CuentaUsuario cuenta = new CuentaUsuario();
        cuenta.setUserName("empleado.demo@softprog.pe");
        cuenta.setPassword("demo123");
        cuenta.setActivo(true);
        cuentaUsuarioDAO.insert(cuenta);

        Empleado empleado = new Empleado();
        empleado.setArea(area);
        empleado.setCuentaUsuario(cuenta);
        empleado.setDni("99887766");
        empleado.setNombre("Elena");
        empleado.setApellidoPaterno("Prueba");
        empleado.setGenero(Genero.FEMENINO);
        empleado.setFechaNacimiento(LocalDate.of(1995, 5, 12));
        empleado.setCargo(Cargo.EJECUTIVO_COMERCIAL);
        empleado.setSueldo(3100.00);
        empleado.setActivo(true);
        empleadoDAO.insert(empleado);
        System.out.println("Insertado:   " + describir(empleado));

        empleado = empleadoDAO.findById(empleado.getId());
        System.out.println("Recuperado:  " + describir(empleado));

        empleado.setApellidoPaterno("Prueba Editada");
        empleado.setCargo(Cargo.VENDEDOR_SENIOR);
        empleado.setSueldo(3550.50);
        empleado.setActivo(false);
        empleadoDAO.update(empleado);
        System.out.println("Actualizado: " + describir(empleadoDAO.findById(empleado.getId())));

        empleadoDAO.delete(empleado.getId());
        System.out.println("Eliminado:   id " + empleado.getId());

        listarEmpleados(empleadoDAO.findAll());

        cuentaUsuarioDAO.delete(cuenta.getId());
        areaDAO.delete(area.getId());
    }

    private static void probarClientes() throws SQLException {
        titulo("CLIENTE");

        CuentaUsuario cuenta = new CuentaUsuario();
        cuenta.setUserName("cliente.demo@softprog.pe");
        cuenta.setPassword("demo123");
        cuenta.setActivo(true);
        cuentaUsuarioDAO.insert(cuenta);

        Cliente cliente = new Cliente();
        cliente.setCuentaUsuario(cuenta);
        cliente.setDni("55443322");
        cliente.setNombre("Marco");
        cliente.setApellidoPaterno("Prueba");
        cliente.setGenero(Genero.MASCULINO);
        cliente.setFechaNacimiento(LocalDate.of(1990, 8, 20));
        cliente.setCategoria(CategoriaCliente.PLATA);
        cliente.setLineaCredito(5000.00);
        cliente.setActivo(true);
        clienteDAO.insert(cliente);
        System.out.println("Insertado:   " + describir(cliente));

        cliente = clienteDAO.findById(cliente.getId());
        System.out.println("Recuperado:  " + describir(cliente));

        cliente.setApellidoPaterno("Prueba Editada");
        cliente.setCategoria(CategoriaCliente.ORO);
        cliente.setLineaCredito(8200.75);
        cliente.setActivo(false);
        clienteDAO.update(cliente);
        System.out.println("Actualizado: " + describir(clienteDAO.findById(cliente.getId())));

        clienteDAO.delete(cliente.getId());
        System.out.println("Eliminado:   id " + cliente.getId());

        listarClientes(clienteDAO.findAll());

        cuentaUsuarioDAO.delete(cuenta.getId());
    }

    private static void probarProductos() throws SQLException {
        titulo("PRODUCTO");

        Producto producto = new Producto();
        producto.setNombre("Cuaderno A4");
        producto.setUnidadMedida(UnidadMedida.UND);
        producto.setPrecio(12.50);
        producto.setActivo(true);
        productoDAO.insert(producto);
        System.out.println("Insertado:   " + describir(producto));

        producto = productoDAO.findById(producto.getId());
        System.out.println("Recuperado:  " + describir(producto));

        producto.setNombre("Cuaderno A4 cuadriculado");
        producto.setPrecio(14.90);
        producto.setActivo(false);
        productoDAO.update(producto);
        System.out.println("Actualizado: " + describir(productoDAO.findById(producto.getId())));

        productoDAO.delete(producto.getId());
        System.out.println("Eliminado:   id " + producto.getId());

        listarProductos(productoDAO.findAll());
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

    private static void titulo(String nombre) {
        System.out.println();
        System.out.println("=== " + nombre + " ===");
    }

    private static String describir(Area area) {
        return String.format("[%d] %-28s activo=%s",
                area.getId(), area.getNombre(), area.isActivo());
    }

    private static String describir(CuentaUsuario cuenta) {
        return String.format("[%d] %-30s %-12s activo=%s",
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
        return String.format("[%d] %-28s %-6s S/ %.2f  activo=%s",
                producto.getId(),
                producto.getNombre(),
                producto.getUnidadMedida(),
                producto.getPrecio(),
                producto.isActivo());
    }
}
