using Microsoft.Extensions.Configuration;
using SoftProgDBManager.Db;
using SoftProgModelo.Modelos;
using SoftProgModelo.Modelos.Almacen;
using SoftProgModelo.Modelos.Clientes;
using SoftProgModelo.Modelos.Rrhh;
using SoftProgModelo.Modelos.Ventas;
using SoftProgNegocio.Bo.Almacen;
using SoftProgNegocio.Bo.Clientes;
using SoftProgNegocio.Bo.Cuentas;
using SoftProgNegocio.Bo.Rrhh;
using SoftProgNegocio.Bo.Ventas;

public static class Program
{
    public static void Main(string[] args)
    {
        _ = BuildConfiguration();

        var sufijo = DateTimeOffset.UtcNow.ToUnixTimeMilliseconds().ToString();

        var areaBo = new AreaBoImpl();
        var cuentaUsuarioBo = new CuentaUsuarioBoImpl();
        var empleadoBo = new EmpleadoBoImpl();
        var clienteBo = new ClienteBoImpl();
        var productoBo = new ProductoBoImpl();
        var ordenVentaBo = new OrdenVentaBoImpl();

        int? idArea = null;
        int? idCuentaEmpleado = null;
        int? idCuentaCliente = null;
        int? idEmpleado = null;
        int? idCliente = null;
        int? idProducto = null;
        int? idOrdenVenta = null;

        try
        {
            var area = new Area
            {
                Nombre = $"Area demo {sufijo}",
                Activo = true
            };
            areaBo.Guardar(area, Estado.Nuevo);
            idArea = area.Id;
            Console.WriteLine($"Area creada: {areaBo.Obtener(idArea.Value)}");

            area.Nombre = $"Area demo actualizada {sufijo}";
            areaBo.Guardar(area, Estado.Modificado);

            var cuentaEmpleado = new CuentaUsuario
            {
                UserName = $"emp_{sufijo}",
                Password = "123456",
                Activo = true
            };
            cuentaUsuarioBo.Guardar(cuentaEmpleado, Estado.Nuevo);
            idCuentaEmpleado = cuentaEmpleado.Id;

            var cuentaCliente = new CuentaUsuario
            {
                UserName = $"cli_{sufijo}",
                Password = "123456",
                Activo = true
            };
            cuentaUsuarioBo.Guardar(cuentaCliente, Estado.Nuevo);
            idCuentaCliente = cuentaCliente.Id;

            Console.WriteLine($"Login cuenta empleado: {cuentaUsuarioBo.Login(cuentaEmpleado.UserName, "123456")}");

            var empleado = new Empleado
            {
                Area = area,
                CuentaUsuario = cuentaEmpleado,
                Dni = ("10" + sufijo).Substring(0, 8),
                Nombre = "Juan",
                ApellidoPaterno = "Perez",
                Genero = Genero.MASCULINO,
                FechaNacimiento = new DateTime(1995, 6, 15),
                Cargo = Cargo.ASISTENTE,
                Sueldo = 2200.50,
                Activo = true
            };
            empleadoBo.Guardar(empleado, Estado.Nuevo);
            idEmpleado = empleado.Id;
            Console.WriteLine($"Empleado creado: {empleadoBo.Obtener(idEmpleado.Value)}");

            empleado.Cargo = Cargo.TECNICO;
            empleado.Sueldo = 2500.00;
            empleadoBo.Guardar(empleado, Estado.Modificado);

            var cliente = new Cliente
            {
                CuentaUsuario = cuentaCliente,
                Dni = ("20" + sufijo).Substring(0, 8),
                Nombre = "Ana",
                ApellidoPaterno = "Lopez",
                Genero = Genero.FEMENINO,
                FechaNacimiento = new DateTime(1998, 2, 10),
                Categoria = CategoriaCliente.ESTANDARD,
                LineaCredito = 1500.00,
                Activo = true
            };
            clienteBo.Guardar(cliente, Estado.Nuevo);
            idCliente = cliente.Id;
            Console.WriteLine($"Cliente creado: {clienteBo.Obtener(idCliente.Value)}");

            cliente.Categoria = CategoriaCliente.PREMIUM;
            cliente.LineaCredito = 3000.00;
            clienteBo.Guardar(cliente, Estado.Modificado);

            var producto = new Producto
            {
                Nombre = $"Producto demo {sufijo}",
                UnidadMedida = UnidadMedida.UND,
                Precio = 19.90,
                Activo = true
            };
            productoBo.Guardar(producto, Estado.Nuevo);
            idProducto = producto.Id;
            Console.WriteLine($"Producto creado: {productoBo.Obtener(idProducto.Value)}");

            producto.Precio = 24.50;
            productoBo.Guardar(producto, Estado.Modificado);

            var linea = new LineaOrdenVenta
            {
                Producto = producto,
                Cantidad = 2,
                SubTotal = producto.Precio * 2,
                Activo = true
            };

            var ordenVenta = new OrdenVenta
            {
                Cliente = cliente,
                Empleado = empleado,
                Lineas = new List<LineaOrdenVenta> { linea },
                Total = linea.SubTotal,
                Activo = true
            };
            ordenVentaBo.Guardar(ordenVenta, Estado.Nuevo);
            idOrdenVenta = ordenVenta.Id;
            Console.WriteLine($"Orden creada: {ordenVentaBo.Obtener(idOrdenVenta.Value)}");

            linea.Cantidad = 3;
            linea.SubTotal = producto.Precio * linea.Cantidad;
            ordenVenta.Total = linea.SubTotal;
            ordenVenta.Lineas = new List<LineaOrdenVenta> { linea };
            ordenVentaBo.Guardar(ordenVenta, Estado.Modificado);

            Console.WriteLine($"Resumen ordenes: {ordenVentaBo.Listar().Count}");
            Console.WriteLine("Flujo de prueba completado.");
        }
        finally
        {
            if (idOrdenVenta.HasValue)
            {
                ordenVentaBo.Eliminar(idOrdenVenta.Value);
            }

            if (idCliente.HasValue)
            {
                clienteBo.Eliminar(idCliente.Value);
            }

            if (idEmpleado.HasValue)
            {
                empleadoBo.Eliminar(idEmpleado.Value);
            }

            if (idProducto.HasValue)
            {
                productoBo.Eliminar(idProducto.Value);
            }

            if (idCuentaCliente.HasValue)
            {
                cuentaUsuarioBo.Eliminar(idCuentaCliente.Value);
            }

            if (idCuentaEmpleado.HasValue)
            {
                cuentaUsuarioBo.Eliminar(idCuentaEmpleado.Value);
            }

            if (idArea.HasValue)
            {
                areaBo.Eliminar(idArea.Value);
            }

            Console.WriteLine("Limpieza final completada.");
        }
    }

    private static IConfigurationRoot BuildConfiguration()
    {
        var configuration = new ConfigurationBuilder()
            .SetBasePath(AppContext.BaseDirectory)
            .AddJsonFile("appsettings.json", optional: false, reloadOnChange: false)
            .Build();

        ConfigurationContext.Initialize(configuration);
        return configuration;
    }
}
