package pe.edu.pucp.softprog.app;

import pe.edu.pucp.softprog.bo.almacen.ProductoBO;
import pe.edu.pucp.softprog.bo.almacen.ProductoBOImpl;
import pe.edu.pucp.softprog.bo.clientes.ClienteBO;
import pe.edu.pucp.softprog.bo.clientes.ClienteBOImpl;
import pe.edu.pucp.softprog.bo.cuentas.CuentaUsuarioBO;
import pe.edu.pucp.softprog.bo.cuentas.CuentaUsuarioBOImpl;
import pe.edu.pucp.softprog.bo.rrhh.AreaBO;
import pe.edu.pucp.softprog.bo.rrhh.AreaBOImpl;
import pe.edu.pucp.softprog.bo.rrhh.EmpleadoBO;
import pe.edu.pucp.softprog.bo.rrhh.EmpleadoBOImpl;
import pe.edu.pucp.softprog.bo.ventas.OrdenVentaBO;
import pe.edu.pucp.softprog.bo.ventas.OrdenVentaBOImpl;
import pe.edu.pucp.softprog.modelo.Estado;
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

        AreaBO areaBO = new AreaBOImpl();
        CuentaUsuarioBO cuentaUsuarioBO = new CuentaUsuarioBOImpl();
        EmpleadoBO empleadoBO = new EmpleadoBOImpl();
        ClienteBO clienteBO = new ClienteBOImpl();
        ProductoBO productoBO = new ProductoBOImpl();
        OrdenVentaBO ordenVentaBO = new OrdenVentaBOImpl();

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
            areaBO.guardar(area, Estado.Nuevo);
            idArea = area.getId();
            System.out.println("Area creada: " + areaBO.obtener(idArea));

            area.setNombre("Area demo actualizada " + sufijo);
            areaBO.guardar(area, Estado.Modificado);

            CuentaUsuario cuentaEmpleado = new CuentaUsuario();
            cuentaEmpleado.setUserName("emp_" + sufijo);
            cuentaEmpleado.setPassword("123456");
            cuentaEmpleado.setActivo(true);
            cuentaUsuarioBO.guardar(cuentaEmpleado, Estado.Nuevo);
            idCuentaEmpleado = cuentaEmpleado.getId();

            CuentaUsuario cuentaCliente = new CuentaUsuario();
            cuentaCliente.setUserName("cli_" + sufijo);
            cuentaCliente.setPassword("123456");
            cuentaCliente.setActivo(true);
            cuentaUsuarioBO.guardar(cuentaCliente, Estado.Nuevo);
            idCuentaCliente = cuentaCliente.getId();

            System.out.println("Login cuenta empleado: "
                    + cuentaUsuarioBO.login(cuentaEmpleado.getUserName(), "123456"));

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
            empleadoBO.guardar(empleado, Estado.Nuevo);
            idEmpleado = empleado.getId();
            System.out.println("Empleado creado: " + empleadoBO.obtener(idEmpleado));

            empleado.setCargo(Cargo.TECNICO);
            empleado.setSueldo(2500.00);
            empleadoBO.guardar(empleado, Estado.Modificado);

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
            clienteBO.guardar(cliente, Estado.Nuevo);
            idCliente = cliente.getId();
            System.out.println("Cliente creado: " + clienteBO.obtener(idCliente));

            cliente.setCategoria(CategoriaCliente.PREMIUM);
            cliente.setLineaCredito(3000.00);
            clienteBO.guardar(cliente, Estado.Modificado);

            Producto producto = new Producto();
            producto.setNombre("Producto demo " + sufijo);
            producto.setUnidadMedida(UnidadMedida.UND);
            producto.setPrecio(19.90);
            producto.setActivo(true);
            productoBO.guardar(producto, Estado.Nuevo);
            idProducto = producto.getId();
            System.out.println("Producto creado: " + productoBO.obtener(idProducto));

            producto.setPrecio(24.50);
            productoBO.guardar(producto, Estado.Modificado);

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
            ordenVentaBO.guardar(ordenVenta, Estado.Nuevo);
            idOrdenVenta = ordenVenta.getId();
            System.out.println("Orden creada: " + ordenVentaBO.obtener(idOrdenVenta));

            linea.setCantidad(3);
            linea.setSubTotal(producto.getPrecio() * linea.getCantidad());
            ordenVenta.setTotal(linea.getSubTotal());
            ordenVenta.setLineas(List.of(linea));
            ordenVentaBO.guardar(ordenVenta, Estado.Modificado);

            System.out.println("Resumen ordenes: " + ordenVentaBO.listar().size());
            System.out.println("Flujo de prueba completado.");
        }
        finally {
            if (idOrdenVenta != null) {
                ordenVentaBO.eliminar(idOrdenVenta);
            }
            if (idCliente != null) {
                clienteBO.eliminar(idCliente);
            }
            if (idEmpleado != null) {
                empleadoBO.eliminar(idEmpleado);
            }
            if (idProducto != null) {
                productoBO.eliminar(idProducto);
            }
            if (idCuentaCliente != null) {
                cuentaUsuarioBO.eliminar(idCuentaCliente);
            }
            if (idCuentaEmpleado != null) {
                cuentaUsuarioBO.eliminar(idCuentaEmpleado);
            }
            if (idArea != null) {
                areaBO.eliminar(idArea);
            }
            System.out.println("Limpieza final completada.");
        }
    }
}
