using SoftProg.Modelo;
using SoftProg.Modelo.Almacen;
using SoftProg.Modelo.RRHH;
using SoftProg.Modelo.Seguridad;
using SoftProg.Modelo.Ventas;
using SoftProg.Negocio.BL;
using SoftProg.Negocio.BL.Impl;

public class Program {
    private static readonly long Run = DateTimeOffset.Now.ToUnixTimeMilliseconds() % 1_000_000L;
    private static long DniSeq { get; set; } = 10_000_000L + DateTimeOffset.Now.ToUnixTimeMilliseconds() % 80_000_000L;

    private static readonly IAreaBL AreaBL = new AreaBLImpl();
    private static readonly ICuentaUsuarioBL CuentaUsuarioBL = new CuentaUsuarioBLImpl();
    private static readonly IEmpleadoBL EmpleadoBL = new EmpleadoBLImpl();
    private static readonly IClienteBL ClienteBL = new ClienteBLImpl();
    private static readonly IProductoBL ProductoBL = new ProductoBLImpl();
    private static readonly IOrdenVentaBL OrdenVentaBL = new OrdenVentaBLImpl();

    public static void Main(string[] args) {
        try {
            ProbarAreas();
            ProbarCuentasUsuario();
            ProbarEmpleados();
            ProbarClientes();
            ProbarProductos();
            ProbarOrdenesVenta();
            ProbarReglasDeNegocio();
        } catch (BLException ex) {
            Console.WriteLine($"La prueba se detuvo por un error: {ex.Message}");
            if (ex.InnerException != null) {
                Console.WriteLine($"Causa: {ex.InnerException.Message}");
            }
        }
    }

    private static void ProbarAreas() {
        Titulo("AREA");

        Area area = new() {
            Nombre = $"Área de Prueba {Run}",
            IsActivo = true
        };
        AreaBL.Insert(area);
        Console.WriteLine($"Insertada:   {area}");

        area = AreaBL.FindById(area.Id)!;
        Console.WriteLine($"Recuperada:  {area}");

        area.Nombre = $"Área de Prueba {Run} (editada)";
        area.IsActivo = false;
        AreaBL.Update(area);
        Console.WriteLine($"Actualizada: {AreaBL.FindById(area.Id)}");

        AreaBL.Delete(area.Id);
        Console.WriteLine($"Eliminada:   id {area.Id}");

        Listar(AreaBL.FindAll());
    }

    private static void ProbarCuentasUsuario() {
        Titulo("CUENTA USUARIO");

        CuentaUsuario cuenta = new() {
            UserName = $"prueba.{Run}@softprog.pe",
            Password = "prueba123",
            IsActivo = true
        };
        CuentaUsuarioBL.Insert(cuenta);
        Console.WriteLine($"Insertada:   {cuenta}");

        cuenta = CuentaUsuarioBL.FindById(cuenta.Id)!;
        Console.WriteLine($"Recuperada:  {cuenta}");

        cuenta.UserName = $"prueba.editada.{Run}@softprog.pe";
        cuenta.Password = "nueva456";
        cuenta.IsActivo = false;
        CuentaUsuarioBL.Update(cuenta);
        Console.WriteLine($"Actualizada: {CuentaUsuarioBL.FindById(cuenta.Id)}");

        CuentaUsuarioBL.Delete(cuenta.Id);
        Console.WriteLine($"Eliminada:   id {cuenta.Id}");

        Listar(CuentaUsuarioBL.FindAll());
    }

    private static void ProbarEmpleados() {
        Titulo("EMPLEADO");

        Area area = new() {
            Nombre = $"Almacén {Run}",
            IsActivo = true
        };
        AreaBL.Insert(area);

        CuentaUsuario cuenta = new() {
            UserName = $"empleado.{Run}@softprog.pe",
            Password = "demo123",
            IsActivo = true
        };
        CuentaUsuarioBL.Insert(cuenta);

        Empleado empleado = new() {
            Area = area,
            CuentaUsuario = cuenta,
            Dni = NuevoDni(),
            Nombre = "Elena",
            ApellidoPaterno = "Prueba",
            Genero = Genero.FEMENINO,
            FechaNacimiento = new DateTime(1995, 5, 12),
            Cargo = Cargo.EJECUTIVO_COMERCIAL,
            Sueldo = 3100.00,
            IsActivo = true
        };
        EmpleadoBL.Insert(empleado);
        Console.WriteLine($"Insertado:   {empleado}");

        empleado = EmpleadoBL.FindById(empleado.Id)!;
        Console.WriteLine($"Recuperado:  {empleado}");

        empleado.ApellidoPaterno = "Prueba Editada";
        empleado.Cargo = Cargo.VENDEDOR_SENIOR;
        empleado.Sueldo = 3550.50;
        empleado.IsActivo = false;
        EmpleadoBL.Update(empleado);
        Console.WriteLine($"Actualizado: {EmpleadoBL.FindById(empleado.Id)}");

        EmpleadoBL.Delete(empleado.Id);
        Console.WriteLine($"Eliminado:   id {empleado.Id}");

        Listar(EmpleadoBL.FindAll());

        CuentaUsuarioBL.Delete(cuenta.Id);
        AreaBL.Delete(area.Id);
    }

    private static void ProbarClientes() {
        Titulo("CLIENTE");

        CuentaUsuario cuenta = new() {
            UserName = $"cliente.{Run}@softprog.pe",
            Password = "demo123",
            IsActivo = true
        };
        CuentaUsuarioBL.Insert(cuenta);

        Cliente cliente = new() {
            CuentaUsuario = cuenta,
            Dni = NuevoDni(),
            Nombre = "Marco",
            ApellidoPaterno = "Prueba",
            Genero = Genero.MASCULINO,
            FechaNacimiento = new DateTime(1990, 8, 20),
            Categoria = CategoriaCliente.PLATA,
            LineaCredito = 5000.00,
            IsActivo = true
        };
        ClienteBL.Insert(cliente);
        Console.WriteLine($"Insertado:   {cliente}");

        cliente = ClienteBL.FindById(cliente.Id)!;
        Console.WriteLine($"Recuperado:  {cliente}");

        cliente.ApellidoPaterno = "Prueba Editada";
        cliente.Categoria = CategoriaCliente.ORO;
        cliente.LineaCredito = 8200.75;
        cliente.IsActivo = false;
        ClienteBL.Update(cliente);
        Console.WriteLine($"Actualizado: {ClienteBL.FindById(cliente.Id)}");

        ClienteBL.Delete(cliente.Id);
        Console.WriteLine($"Eliminado:   id {cliente.Id}");

        Listar(ClienteBL.FindAll());

        CuentaUsuarioBL.Delete(cuenta.Id);
    }

    private static void ProbarProductos() {
        Titulo("PRODUCTO");

        Producto producto = new() {
            Nombre = $"Cuaderno A4 {Run}",
            UnidadMedida = UnidadMedida.UND,
            Precio = 12.50,
            IsActivo = true
        };
        ProductoBL.Insert(producto);
        Console.WriteLine($"Insertado:   {producto}");

        producto = ProductoBL.FindById(producto.Id)!;
        Console.WriteLine($"Recuperado:  {producto}");

        producto.Nombre = $"Cuaderno A4 cuadriculado {Run}";
        producto.Precio = 14.90;
        producto.IsActivo = false;
        ProductoBL.Update(producto);
        Console.WriteLine($"Actualizado: {ProductoBL.FindById(producto.Id)}");

        ProductoBL.Delete(producto.Id);
        Console.WriteLine($"Eliminado:   id {producto.Id}");

        Listar(ProductoBL.FindAll());
    }

    private static void ProbarOrdenesVenta() {
        Titulo("ORDEN DE VENTA");

        Area area = new() {
            Nombre = $"Ventas {Run}",
            IsActivo = true
        };
        AreaBL.Insert(area);

        CuentaUsuario cuentaEmpleado = new() {
            UserName = $"vendedor.{Run}@softprog.pe",
            Password = "demo123",
            IsActivo = true
        };
        CuentaUsuarioBL.Insert(cuentaEmpleado);

        Empleado empleado = new() {
            Area = area,
            CuentaUsuario = cuentaEmpleado,
            Dni = NuevoDni(),
            Nombre = "Rosa",
            ApellidoPaterno = "Vendedora",
            Genero = Genero.FEMENINO,
            FechaNacimiento = new DateTime(1992, 3, 4),
            Cargo = Cargo.VENDEDOR_SENIOR,
            Sueldo = 4200.00,
            IsActivo = true
        };
        EmpleadoBL.Insert(empleado);

        CuentaUsuario cuentaCliente = new() {
            UserName = $"comprador.{Run}@softprog.pe",
            Password = "demo123",
            IsActivo = true
        };
        CuentaUsuarioBL.Insert(cuentaCliente);

        Cliente cliente = new() {
            CuentaUsuario = cuentaCliente,
            Dni = NuevoDni(),
            Nombre = "Julio",
            ApellidoPaterno = "Comprador",
            Genero = Genero.MASCULINO,
            FechaNacimiento = new DateTime(1988, 11, 30),
            Categoria = CategoriaCliente.ORO,
            LineaCredito = 10000.00,
            IsActivo = true
        };
        ClienteBL.Insert(cliente);

        Producto lapicero = new() {
            Nombre = $"Lapicero azul {Run}",
            UnidadMedida = UnidadMedida.UND,
            Precio = 2.50,
            IsActivo = true
        };
        ProductoBL.Insert(lapicero);

        Producto resma = new() {
            Nombre = $"Resma papel bond {Run}",
            UnidadMedida = UnidadMedida.UND,
            Precio = 25.00,
            IsActivo = true
        };
        ProductoBL.Insert(resma);

        LineaOrdenVenta linea1 = new() {
            Producto = lapicero,
            Cantidad = 10,
            IsActivo = true
        };

        LineaOrdenVenta linea2 = new() {
            Producto = resma,
            Cantidad = 3,
            IsActivo = true
        };

        OrdenVenta orden = new() {
            Empleado = empleado,
            Cliente = cliente,
            Lineas = [linea1, linea2],
            IsActivo = true
        };
        OrdenVentaBL.Insert(orden);
        Console.WriteLine($"Insertada:   {orden}");
        Console.WriteLine($"Total calculado por la BL (esperado S/ 100.00): S/ {orden.Total:F2}");

        orden = OrdenVentaBL.FindById(orden.Id)!;
        Console.WriteLine($"Recuperada:  {orden}");
        foreach (LineaOrdenVenta linea in orden.Lineas) {
            Console.WriteLine($"  {linea}");
        }

        LineaOrdenVenta lineaExtra = new() {
            Producto = lapicero,
            Cantidad = 5,
            IsActivo = true
        };

        orden.Lineas = [linea1, linea2, lineaExtra];
        orden.IsActivo = false;
        OrdenVentaBL.Update(orden);
        OrdenVenta ordenActualizada = OrdenVentaBL.FindById(orden.Id)!;
        Console.WriteLine($"Actualizada: {ordenActualizada}");
        Console.WriteLine($"Total recalculado por la BL (esperado S/ 112.50): S/ {ordenActualizada.Total:F2}");

        OrdenVentaBL.Delete(orden.Id);
        Console.WriteLine($"Eliminada:   id {orden.Id}");

        Listar(OrdenVentaBL.FindAll());

        ProductoBL.Delete(lapicero.Id);
        ProductoBL.Delete(resma.Id);
        ClienteBL.Delete(cliente.Id);
        EmpleadoBL.Delete(empleado.Id);
        CuentaUsuarioBL.Delete(cuentaCliente.Id);
        CuentaUsuarioBL.Delete(cuentaEmpleado.Id);
        AreaBL.Delete(area.Id);
    }

    private static void ProbarReglasDeNegocio() {
        Titulo("REGLAS DE NEGOCIO");

        try {
            Producto producto = new() {
                Nombre = $"Producto sin precio {Run}",
                UnidadMedida = UnidadMedida.UND,
                Precio = 0,
                IsActivo = true
            };
            ProductoBL.Insert(producto);
            Console.WriteLine("Producto con precio 0: NO se rechazó (falla la regla)");
        } catch (BLException ex) {
            Console.WriteLine($"Producto con precio 0 -> Rechazado: {ex.Message}");
        }

        try {
            string nombre = $"Área repetida {Run}";
            Area area = new() {
                Nombre = nombre,
                IsActivo = true
            };
            AreaBL.Insert(area);
            try {
                Area repetida = new() {
                    Nombre = nombre,
                    IsActivo = true
                };
                AreaBL.Insert(repetida);
                Console.WriteLine("Área con nombre repetido: NO se rechazó (falla la regla)");
            } catch (BLException ex) {
                Console.WriteLine($"Área con nombre repetido -> Rechazado: {ex.Message}");
            }
            AreaBL.Delete(area.Id);
        } catch (BLException ex) {
            Console.WriteLine($"No se pudo preparar la prueba de área repetida: {ex.Message}");
        }

        try {
            Empleado empleado = EmpleadoDemo();
            empleado.Dni = "123";
            EmpleadoBL.Insert(empleado);
            Console.WriteLine("Empleado con DNI inválido: NO se rechazó (falla la regla)");
        } catch (BLException ex) {
            Console.WriteLine($"Empleado con DNI inválido -> Rechazado: {ex.Message}");
        }

        try {
            Cliente cliente = ClienteDemo();
            cliente.Categoria = CategoriaCliente.BRONCE;
            cliente.LineaCredito = 5000.00;
            ClienteBL.Insert(cliente);
            Console.WriteLine("Cliente BRONCE con crédito alto: NO se rechazó (falla la regla)");
        } catch (BLException ex) {
            Console.WriteLine($"Cliente BRONCE con crédito alto -> Rechazado: {ex.Message}");
        }

        try {
            OrdenVenta orden = new() {
                IsActivo = true
            };
            OrdenVentaBL.Insert(orden);
            Console.WriteLine("Orden de venta sin líneas: NO se rechazó (falla la regla)");
        } catch (BLException ex) {
            Console.WriteLine($"Orden de venta sin líneas -> Rechazado: {ex.Message}");
        }
    }

    private static Empleado EmpleadoDemo() {
        Area area = new() {
            Nombre = $"Área temporal {Run}",
            IsActivo = true
        };

        CuentaUsuario cuenta = new() {
            UserName = $"temporal.{Run}@softprog.pe",
            Password = "demo123",
            IsActivo = true
        };

        return new Empleado {
            Area = area,
            CuentaUsuario = cuenta,
            Dni = "70000001",
            Nombre = "Temporal",
            ApellidoPaterno = "Demo",
            Genero = Genero.MASCULINO,
            FechaNacimiento = new DateTime(1990, 1, 1),
            Cargo = Cargo.TECNICO,
            Sueldo = 2000.00,
            IsActivo = true
        };
    }

    private static Cliente ClienteDemo() {
        CuentaUsuario cuenta = new() {
            UserName = $"temporal.cliente.{Run}@softprog.pe",
            Password = "demo123",
            IsActivo = true
        };

        return new Cliente {
            CuentaUsuario = cuenta,
            Dni = "70000002",
            Nombre = "Temporal",
            ApellidoPaterno = "Demo",
            Genero = Genero.FEMENINO,
            FechaNacimiento = new DateTime(1990, 1, 1),
            Categoria = CategoriaCliente.BRONCE,
            LineaCredito = 0.00,
            IsActivo = true
        };
    }

    private static string NuevoDni() {
        DniSeq++;
        return DniSeq.ToString();
    }

    private static void Listar<T>(List<T> registros) where T : Registro {
        Console.WriteLine($"Listado ({registros.Count}):");
        foreach (T registro in registros) {
            Console.WriteLine($"  {registro}");
        }
    }

    private static void Titulo(string nombre) {
        Console.WriteLine();
        Console.WriteLine($"=== {nombre} ===");
    }
}
