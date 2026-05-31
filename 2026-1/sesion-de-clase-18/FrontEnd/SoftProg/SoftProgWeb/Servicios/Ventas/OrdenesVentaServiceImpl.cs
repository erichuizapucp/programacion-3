using Microsoft.Extensions.Configuration;
using SoftProgModelo.Modelos;
using SoftProgModelo.Modelos.Almacen;
using SoftProgModelo.Modelos.Clientes;
using SoftProgModelo.Modelos.Rrhh;
using SoftProgModelo.Modelos.Ventas;
using SoftProgWS.OrdenesVenta;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;
using SoftProgWeb.ViewModels.Mappers;

namespace SoftProgWeb.Servicios.Ventas;

public class OrdenesVentaServiceImpl : SoapServiceBase, IOrdenesVentaService {
    private const string EndpointSetting = "SoapEndpoints:OrdenesVenta";

    public OrdenesVentaServiceImpl(IConfiguration configuration)
        : base(configuration) {
    }

    public List<OrdenVentaViewModel> Listar() {
        var clienteWs = CrearClienteWs();
        var ordenes = clienteWs.listarOrdenesVenta() ?? [];

        var respuesta = new List<OrdenVentaViewModel>();
        foreach (var item in ordenes) {
            var viewModel = OrdenVentaViewModelMapper.ToViewModel(ToDomain(item));
            respuesta.Add(viewModel);
        }

        return respuesta;
    }

    public List<OrdenVentaViewModel> ListarPorCuenta(string cuenta) {
        var clienteWs = CrearClienteWs();
        var ordenes = clienteWs.listarOrdenesVentaPorCuenta(cuenta) ?? [];

        var respuesta = new List<OrdenVentaViewModel>();
        foreach (var item in ordenes) {
            var viewModel = OrdenVentaViewModelMapper.ToViewModel(ToDomain(item));
            respuesta.Add(viewModel);
        }

        return respuesta;
    }

    public OrdenVentaViewModel? Obtener(int id) {
        var clienteWs = CrearClienteWs();
        var orden = clienteWs.obtenerOrdenVenta(id);
        return orden is null ? null : OrdenVentaViewModelMapper.ToViewModel(ToDomain(orden));
    }

    public void Guardar(OrdenVentaViewModel ordenVenta, Estado estado) {
        var clienteWs = CrearClienteWs();
        var domain = OrdenVentaViewModelMapper.ToDomain(ordenVenta);
        clienteWs.guardarOrdenVenta(ToSoap(domain), ParseEstado<estado>(estado));
    }

    public void Eliminar(int id) {
        var clienteWs = CrearClienteWs();
        clienteWs.eliminarOrdenVenta(id);
    }

    private OrdenesVentaWSClient CrearClienteWs() {
        return (OrdenesVentaWSClient)CreateClient();
    }

    protected override object CreateClient() {
        var endpoint = OrdenesVentaWSClient.EndpointConfiguration.OrdenesVentaWSPort;
        var url = Configuration[EndpointSetting]?.Trim();

        if (string.IsNullOrWhiteSpace(url)) {
            return new OrdenesVentaWSClient(endpoint);
        }

        return new OrdenesVentaWSClient(endpoint, url);
    }

    private static OrdenVenta ToDomain(ordenVenta source) {
        return new OrdenVenta {
            Id = source.id,
            Activo = source.activo,
            Fecha = source.fechaSpecified ? source.fecha : DateTime.Today,
            Total = source.total,
            Cliente = source.cliente is null ? null : ToDomain(source.cliente),
            Empleado = source.empleado is null ? null : ToDomain(source.empleado),
            Lineas = MapearLineas(source.lineas)
        };
    }

    private static List<LineaOrdenVenta> MapearLineas(lineaOrdenVenta[]? lineasSoap) {
        var lineas = new List<LineaOrdenVenta>();
        if (lineasSoap is null) {
            return lineas;
        }

        foreach (var linea in lineasSoap) {
            lineas.Add(ToDomain(linea));
        }

        return lineas;
    }

    private static Cliente ToDomain(cliente source) {
        return new Cliente {
            Id = source.id,
            Activo = source.activo,
            Dni = source.dni ?? string.Empty,
            Nombre = source.nombre ?? string.Empty,
            ApellidoPaterno = source.apellidoPaterno ?? string.Empty,
            Genero = ParseEnum(source.genero, SoftProgModelo.Modelos.Genero.MASCULINO),
            FechaNacimiento = source.fechaNacimientoSpecified ? source.fechaNacimiento : DateTime.Today,
            Categoria = ParseEnum(source.categoria, CategoriaCliente.ESTANDARD),
            LineaCredito = source.lineaCredito,
            CuentaUsuario = source.cuentaUsuario is null
                ? null
                : new CuentaUsuario {
                    Id = source.cuentaUsuario.id,
                    Activo = source.cuentaUsuario.activo,
                    UserName = source.cuentaUsuario.userName ?? string.Empty,
                    Password = source.cuentaUsuario.password ?? string.Empty
                }
        };
    }

    private static Empleado ToDomain(empleado source) {
        return new Empleado {
            Id = source.id,
            Activo = source.activo,
            Dni = source.dni ?? string.Empty,
            Nombre = source.nombre ?? string.Empty,
            ApellidoPaterno = source.apellidoPaterno ?? string.Empty,
            Genero = ParseEnum(source.genero, SoftProgModelo.Modelos.Genero.MASCULINO),
            FechaNacimiento = source.fechaNacimientoSpecified ? source.fechaNacimiento : DateTime.Today,
            Cargo = ParseEnum(source.cargo, Cargo.ASISTENTE),
            Sueldo = source.sueldo,
            Area = source.area is null
                ? null
                : new Area {
                    Id = source.area.id,
                    Activo = source.area.activo,
                    Nombre = source.area.nombre ?? string.Empty
                },
            CuentaUsuario = source.cuentaUsuario is null
                ? null
                : new CuentaUsuario {
                    Id = source.cuentaUsuario.id,
                    Activo = source.cuentaUsuario.activo,
                    UserName = source.cuentaUsuario.userName ?? string.Empty,
                    Password = source.cuentaUsuario.password ?? string.Empty
                }
        };
    }

    private static LineaOrdenVenta ToDomain(lineaOrdenVenta source) {
        return new LineaOrdenVenta {
            Id = source.id,
            Activo = source.activo,
            Cantidad = source.cantidad,
            SubTotal = source.subTotal,
            Producto = source.producto is null
                ? null
                : new Producto {
                    Id = source.producto.id,
                    Activo = source.producto.activo,
                    Nombre = source.producto.nombre ?? string.Empty,
                    Precio = source.producto.precio,
                    UnidadMedida = ParseEnum(source.producto.unidadMedida, UnidadMedida.UND)
                }
        };
    }

    private static ordenVenta ToSoap(OrdenVenta source) {
        return new ordenVenta {
            id = source.Id,
            activo = source.Activo,
            fecha = source.Fecha,
            fechaSpecified = true,
            total = source.Total,
            cliente = source.Cliente is null
                ? null
                : new SoftProgWS.OrdenesVenta.cliente {
                    id = source.Cliente.Id,
                    activo = source.Cliente.Activo,
                    dni = source.Cliente.Dni,
                    nombre = source.Cliente.Nombre,
                    apellidoPaterno = source.Cliente.ApellidoPaterno,
                    fechaNacimiento = source.Cliente.FechaNacimiento,
                    fechaNacimientoSpecified = true,
                    genero = ParseEnum(source.Cliente.Genero, genero.MASCULINO),
                    generoSpecified = true,
                    categoria = ParseEnum(source.Cliente.Categoria, categoriaCliente.ESTANDARD),
                    categoriaSpecified = true,
                    lineaCredito = source.Cliente.LineaCredito
                },
            empleado = source.Empleado is null
                ? null
                : new SoftProgWS.OrdenesVenta.empleado {
                    id = source.Empleado.Id,
                    activo = source.Empleado.Activo,
                    dni = source.Empleado.Dni,
                    nombre = source.Empleado.Nombre,
                    apellidoPaterno = source.Empleado.ApellidoPaterno,
                    fechaNacimiento = source.Empleado.FechaNacimiento,
                    fechaNacimientoSpecified = true,
                    genero = ParseEnum(source.Empleado.Genero, genero.MASCULINO),
                    generoSpecified = true,
                    cargo = ParseEnum(source.Empleado.Cargo, cargo.ASISTENTE),
                    cargoSpecified = true,
                    sueldo = source.Empleado.Sueldo
                },
            lineas = ToSoapLineas(source.Lineas)
        };
    }

    private static SoftProgWS.OrdenesVenta.lineaOrdenVenta[] ToSoapLineas(List<LineaOrdenVenta> lineasDomain) {
        var lineasSoap = new List<SoftProgWS.OrdenesVenta.lineaOrdenVenta>();

        foreach (var linea in lineasDomain) {
            lineasSoap.Add(new SoftProgWS.OrdenesVenta.lineaOrdenVenta {
                id = linea.Id,
                activo = linea.Activo,
                cantidad = linea.Cantidad,
                subTotal = linea.SubTotal,
                producto = linea.Producto is null
                    ? null
                    : new SoftProgWS.OrdenesVenta.producto {
                        id = linea.Producto.Id,
                        activo = linea.Producto.Activo,
                        nombre = linea.Producto.Nombre,
                        precio = linea.Producto.Precio,
                        unidadMedida = ParseEnum(linea.Producto.UnidadMedida, unidadMedida.UND),
                        unidadMedidaSpecified = true
                    }
            });
        }

        return lineasSoap.ToArray();
    }
}
