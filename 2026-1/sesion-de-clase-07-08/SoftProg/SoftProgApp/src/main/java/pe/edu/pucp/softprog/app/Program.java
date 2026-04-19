package pe.edu.pucp.softprog.app;

import pe.edu.pucp.softprog.dao.almacen.ProductoDAO;
import pe.edu.pucp.softprog.dao.almacen.ProductoDAOImpl;
import pe.edu.pucp.softprog.dao.clientes.ClienteDAO;
import pe.edu.pucp.softprog.dao.clientes.ClienteDAOImpl;
import pe.edu.pucp.softprog.dao.cuentas.CuentaUsuarioDAO;
import pe.edu.pucp.softprog.dao.cuentas.CuentaUsuarioDAOImpl;
import pe.edu.pucp.softprog.dao.rrhh.AreaDAO;
import pe.edu.pucp.softprog.dao.rrhh.AreaDAOImpl;
import pe.edu.pucp.softprog.dao.rrhh.EmpleadoDAO;
import pe.edu.pucp.softprog.dao.rrhh.EmpleadoDAOImpl;
import pe.edu.pucp.softprog.dao.ventas.OrdenVentaDAO;
import pe.edu.pucp.softprog.dao.ventas.OrdenVentaDAOImpl;
import pe.edu.pucp.softprog.modelo.Genero;
import pe.edu.pucp.softprog.modelo.almacen.Producto;
import pe.edu.pucp.softprog.modelo.almacen.UnidadMedida;
import pe.edu.pucp.softprog.modelo.clientes.CategoriaCliente;
import pe.edu.pucp.softprog.modelo.clientes.Cliente;
import pe.edu.pucp.softprog.modelo.rrhh.Area;
import pe.edu.pucp.softprog.modelo.rrhh.Cargo;
import pe.edu.pucp.softprog.modelo.rrhh.CuentaUsuario;
import pe.edu.pucp.softprog.modelo.rrhh.Empleado;
import pe.edu.pucp.softprog.modelo.ventas.LineaOrdenVenta;
import pe.edu.pucp.softprog.modelo.ventas.OrdenVenta;

import java.sql.Date;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        String sufijo = String.valueOf(System.currentTimeMillis());

        AreaDAO areaDAO = new AreaDAOImpl();
        CuentaUsuarioDAO cuentaUsuarioDAO = new CuentaUsuarioDAOImpl();
        EmpleadoDAO empleadoDAO = new EmpleadoDAOImpl();
        ClienteDAO clienteDAO = new ClienteDAOImpl();
        ProductoDAO productoDAO = new ProductoDAOImpl();
        OrdenVentaDAO ordenVentaDAO = new OrdenVentaDAOImpl();

        Integer idArea = null;
        Integer idCuentaEmpleado = null;
        Integer idCuentaCliente = null;
        Integer idEmpleado = null;
        Integer idCliente = null;
        Integer idProducto = null;
        Integer idOrdenVenta = null;

        try {
            Area area = new Area();
            area.setNombre("Area demo " + sufijo);
            area.setActivo(true);
            idArea = areaDAO.crear(area);
            area.setId(idArea);
            System.out.println("Area creada: " + areaDAO.leer(idArea));

            area.setNombre("Area demo actualizada " + sufijo);
            areaDAO.actualizar(area);

            CuentaUsuario cuentaEmpleado = new CuentaUsuario();
            cuentaEmpleado.setUserName("emp_" + sufijo);
            cuentaEmpleado.setPassword("123456");
            cuentaEmpleado.setActivo(true);
            idCuentaEmpleado = cuentaUsuarioDAO.crear(cuentaEmpleado);
            cuentaEmpleado.setId(idCuentaEmpleado);

            CuentaUsuario cuentaCliente = new CuentaUsuario();
            cuentaCliente.setUserName("cli_" + sufijo);
            cuentaCliente.setPassword("123456");
            cuentaCliente.setActivo(true);
            idCuentaCliente = cuentaUsuarioDAO.crear(cuentaCliente);
            cuentaCliente.setId(idCuentaCliente);

            System.out.println("Login cuenta empleado: "
                    + cuentaUsuarioDAO.login(cuentaEmpleado.getUserName(), "123456"));

            Empleado empleado = new Empleado();
            empleado.setArea(area);
            empleado.setCuentaUsuario(cuentaEmpleado);
            empleado.setDni(("10" + sufijo).substring(0, 8));
            empleado.setNombre("Juan");
            empleado.setApellidoPaterno("Perez");
            empleado.setGenero(Genero.MASCULINO);
            empleado.setFechaNacimiento(Date.valueOf("1995-06-15"));
            empleado.setCargo(Cargo.ASISTENTE);
            empleado.setSueldo(2200.50);
            empleado.setActivo(true);
            idEmpleado = empleadoDAO.crear(empleado);
            empleado.setId(idEmpleado);
            System.out.println("Empleado creado: " + empleadoDAO.leer(idEmpleado));

            empleado.setCargo(Cargo.TECNICO);
            empleado.setSueldo(2500.00);
            empleadoDAO.actualizar(empleado);

            Cliente cliente = new Cliente();
            cliente.setCuentaUsuario(cuentaCliente);
            cliente.setDni(("20" + sufijo).substring(0, 8));
            cliente.setNombre("Ana");
            cliente.setApellidoPaterno("Lopez");
            cliente.setGenero(Genero.FEMENINO);
            cliente.setFechaNacimiento(Date.valueOf("1998-02-10"));
            cliente.setCategoria(CategoriaCliente.ESTANDARD);
            cliente.setLineaCredito(1500.00);
            cliente.setActivo(true);
            idCliente = clienteDAO.crear(cliente);
            cliente.setId(idCliente);
            System.out.println("Cliente creado: " + clienteDAO.leer(idCliente));

            cliente.setCategoria(CategoriaCliente.PREMIUM);
            cliente.setLineaCredito(3000.00);
            clienteDAO.actualizar(cliente);

            Producto producto = new Producto();
            producto.setNombre("Producto demo " + sufijo);
            producto.setUnidadMedida(UnidadMedida.UND);
            producto.setPrecio(19.90);
            producto.setActivo(true);
            idProducto = productoDAO.crear(producto);
            producto.setId(idProducto);
            System.out.println("Producto creado: " + productoDAO.leer(idProducto));

            producto.setPrecio(24.50);
            productoDAO.actualizar(producto);

            LineaOrdenVenta linea = new LineaOrdenVenta();
            linea.setProducto(producto);
            linea.setCantidad(2);
            linea.setSubTotal(producto.getPrecio() * linea.getCantidad());
            linea.setActivo(true);

            OrdenVenta ordenVenta = new OrdenVenta();
            ordenVenta.setCliente(cliente);
            ordenVenta.setEmpleado(empleado);
            ordenVenta.setLineas(List.of(linea));
            ordenVenta.setTotal(linea.getSubTotal());
            ordenVenta.setActivo(true);
            idOrdenVenta = ordenVentaDAO.crear(ordenVenta);
            ordenVenta.setId(idOrdenVenta);
            System.out.println("Orden creada: " + ordenVentaDAO.leer(idOrdenVenta));

            linea.setCantidad(3);
            linea.setSubTotal(producto.getPrecio() * linea.getCantidad());
            ordenVenta.setTotal(linea.getSubTotal());
            ordenVenta.setLineas(List.of(linea));
            ordenVentaDAO.actualizar(ordenVenta);

            System.out.println("Resumen ordenes: " + ordenVentaDAO.leerTodos().size());
            System.out.println("Flujo de prueba completado.");
        }
        finally {
            if (idOrdenVenta != null) {
                ordenVentaDAO.eliminar(idOrdenVenta);
            }
            if (idCliente != null) {
                clienteDAO.eliminar(idCliente);
            }
            if (idEmpleado != null) {
                empleadoDAO.eliminar(idEmpleado);
            }
            if (idProducto != null) {
                productoDAO.eliminar(idProducto);
            }
            if (idCuentaCliente != null) {
                cuentaUsuarioDAO.eliminar(idCuentaCliente);
            }
            if (idCuentaEmpleado != null) {
                cuentaUsuarioDAO.eliminar(idCuentaEmpleado);
            }
            if (idArea != null) {
                areaDAO.eliminar(idArea);
            }
            System.out.println("Limpieza final completada.");
        }
    }
}
