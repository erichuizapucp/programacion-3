package pe.edu.pucp.inf30.softprog.pruebas;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.Area;
import pe.edu.pucp.inf30.softprog.dao.rrhh.AreaDAO;
import pe.edu.pucp.inf30.softprog.dao.almacen.ProductoDAO;
import pe.edu.pucp.inf30.softprog.dao.clientes.ClienteDAO;
import pe.edu.pucp.inf30.softprog.dao.rrhh.CuentaUsuarioDAO;
import pe.edu.pucp.inf30.softprog.dao.rrhh.EmpleadoDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.almacen.ProductoDAOImpl;
import pe.edu.pucp.inf30.softprog.daoimpl.clientes.ClienteDAOImpl;
import pe.edu.pucp.inf30.softprog.daoimpl.rrhh.AreaDAOImpl;
import pe.edu.pucp.inf30.softprog.daoimpl.rrhh.CuentaUsuarioDAOImpl;
import pe.edu.pucp.inf30.softprog.daoimpl.rrhh.EmpleadoDAOImpl;
import pe.edu.pucp.inf30.softprog.modelo.Genero;
import pe.edu.pucp.inf30.softprog.modelo.almacen.Producto;
import pe.edu.pucp.inf30.softprog.modelo.almacen.UnidadMedida;
import pe.edu.pucp.inf30.softprog.modelo.clientes.CategoriaCliente;
import pe.edu.pucp.inf30.softprog.modelo.clientes.Cliente;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.Cargo;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.CuentaUsuario;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.Empleado;

/**
 *
 * @author eric
 */
public class SoftProgPruebas {
    static void probarArea() {
        Area area = new Area();
        area.setNombre("Recursos Humanos");
        area.setActivo(true);
        
        AreaDAO areaDao = new AreaDAOImpl();
        int id = areaDao.crear(area);
        area.setId(id);
        
        area.setNombre("Ventas");
        areaDao.actualizar(area);
        
        area = areaDao.leer(id);
        if (area != null) {
            System.out.println(area);
        }
        
        List<Area> areas = areaDao.leerTodos();
        for (Area a : areas) {
            System.out.println(a);
        }
        
        if (area != null) {
            areaDao.eliminar(area.getId());
        }
    }
    
    static void probarProducto() {
        Producto producto = new Producto();
        producto.setNombre("Laptop HP");
        producto.setPrecio(3500.00);
        producto.setUnidadMedida(UnidadMedida.Unidad);
        producto.setActivo(true);
        
        ProductoDAO productoDao = new ProductoDAOImpl();
        int id = productoDao.crear(producto);
        producto.setId(id);
        
        producto = productoDao.leer(id);
        if (producto != null) {
            System.out.println(producto);
        }
        
        if (producto != null) {
            producto.setNombre("Laptop Lenovo");
            producto.setPrecio(4500.00);
            productoDao.actualizar(producto);
        }
        
        List<Producto> productos = productoDao.leerTodos();
        for (Producto p : productos) {
            System.out.println(p);
        }
        
        if (producto != null) {
            productoDao.eliminar(producto.getId());
        }
    }
    
    static void probarCuentaUsuario() {
        CuentaUsuario cuentaUsuario = new CuentaUsuario();
        cuentaUsuario.setUserName("Usuario01");
        cuentaUsuario.setPassword("p01");
        cuentaUsuario.setActivo(true);
        
        CuentaUsuarioDAO cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        int id = cuentaUsuarioDao.crear(cuentaUsuario);
        cuentaUsuario.setId(id);
        
        cuentaUsuario = cuentaUsuarioDao.leer(id);
        if (cuentaUsuario != null) {
            System.out.println(cuentaUsuario);
        }
        
        if (cuentaUsuario != null) {
            cuentaUsuario.setUserName("Usuario02");
            cuentaUsuario.setPassword("p02");
            cuentaUsuarioDao.actualizar(cuentaUsuario);
        }
        
        List<CuentaUsuario> cuentas = cuentaUsuarioDao.leerTodos();
        for (CuentaUsuario c : cuentas) {
            System.out.println(c);
        }
        
        if (cuentaUsuario != null) {
            cuentaUsuarioDao.eliminar(cuentaUsuario.getId());
        }
    }
    
    static void probarCliente() {
        CuentaUsuario cuentaUsuario = new CuentaUsuario();
        cuentaUsuario.setUserName("Usuario01");
        cuentaUsuario.setPassword("p01");
        cuentaUsuario.setActivo(true);
        
        CuentaUsuarioDAO cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        int cuentaUsuarioId = cuentaUsuarioDao.crear(cuentaUsuario);
        cuentaUsuario.setId(cuentaUsuarioId);
        
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan");
        cliente.setApellidoPaterno("Perez");
        cliente.setCuentaUsuario(cuentaUsuario);
        cliente.setDni("12345678");
        cliente.setCategoria(CategoriaCliente.ESTANDARD);
        cliente.setFechaNacimiento(new GregorianCalendar(2000, 
                Calendar.SEPTEMBER, 1).getTime());
        cliente.setGenero(Genero.MASCULINO);
        cliente.setLineaCredito(5000.80);
        cliente.setActivo(true);
        
        ClienteDAO clienteDao = new ClienteDAOImpl();
        int id = clienteDao.crear(cliente);
        cliente.setId(id);
        
        cliente = clienteDao.leer(id);
        if (cliente != null) {
            System.out.println(cliente);
        }
        
        if (cliente != null) {
            cliente.setCategoria(CategoriaCliente.PREMIUM);
            cliente.setFechaNacimiento(new GregorianCalendar(2001, 
                    Calendar.OCTOBER, 1).getTime());
            cliente.setApellidoPaterno("Diaz");
            clienteDao.actualizar(cliente);
        }
        
        List<Cliente> clientes = clienteDao.leerTodos();
        for (Cliente c : clientes) {
            System.out.println(c);
        }
        
        if (cliente != null) {
            clienteDao.eliminar(cliente.getId());
        }
        
        cuentaUsuarioDao.eliminar(cuentaUsuario.getId());
    }
    
    static void probarEmpleado() {
        Area area = new Area();
        area.setNombre("Recursos Humanos");
        area.setActivo(true);
        
        AreaDAO areaDao = new AreaDAOImpl();
        int idArea = areaDao.crear(area);
        area.setId(idArea);
        
        CuentaUsuario cuentaUsuario = new CuentaUsuario();
        cuentaUsuario.setUserName("Usuario01");
        cuentaUsuario.setPassword("p01");
        cuentaUsuario.setActivo(true);
        
        CuentaUsuarioDAO cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        int cuentaUsuarioId = cuentaUsuarioDao.crear(cuentaUsuario);
        cuentaUsuario.setId(cuentaUsuarioId);
        
        Empleado empleado = new Empleado();
        empleado.setNombre("Juan");
        empleado.setApellidoPaterno("Perez");
        empleado.setArea(area);
        empleado.setCuentaUsuario(cuentaUsuario);
        empleado.setDni("12345678");
        empleado.setFechaNacimiento(new GregorianCalendar(2000, 
                Calendar.SEPTEMBER, 1).getTime());
        empleado.setGenero(Genero.MASCULINO);
        empleado.setCargo(Cargo.TECNICO);
        empleado.setSueldo(5000.00);
        empleado.setActivo(true);
        
        EmpleadoDAO empleadoDao = new EmpleadoDAOImpl();
        int id = empleadoDao.crear(empleado);
        empleado.setId(id);
        
        empleado = empleadoDao.leer(id);
        if (empleado != null) {
            System.out.println(empleado);
        }
        
        if (empleado != null) {
            empleado.setCargo(Cargo.SUBDIRECTOR);
            empleado.setFechaNacimiento(new GregorianCalendar(1995, 
                    Calendar.JULY, 10).getTime());
            empleado.setApellidoPaterno("Diaz");
            empleado.setSueldo(10000.00);
            empleadoDao.actualizar(empleado);
        }
        
        List<Empleado> empleados = empleadoDao.leerTodos();
        for (Empleado e : empleados) {
            System.out.println(e);
        }
        
        if (empleado != null) {
            empleadoDao.eliminar(empleado.getId());
        }
        
        cuentaUsuarioDao.eliminar(cuentaUsuario.getId());
        areaDao.eliminar(area.getId());
    }
    
    public static void main(String[] args) {
//        probarArea();
        probarProducto();
//        probarCuentaUsuario();
//        probarCliente();
//        probarEmpleado();
    }
}
