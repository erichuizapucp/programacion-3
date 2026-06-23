using SoftProgWS;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Ventas;

public class OrdenesVentaServiceSoapClient : SoapServiceClient<OrdenVentaViewModel, ordenVenta>, IOrdenesVentaServiceClient {
    private const string EndpointSetting = "SoapEndpoints:OrdenesVenta";

    public OrdenesVentaServiceSoapClient(IConfiguration configuration)
        : base(configuration) {
    }

    public List<OrdenVentaViewModel> Listar() {
        var clienteWs = CrearClienteWs();
        var ordenes = clienteWs.listarOrdenesVenta() ?? [];

        var respuesta = new List<OrdenVentaViewModel>();
        foreach (var item in ordenes) {
            respuesta.Add(ToViewModel(item));
        }

        return respuesta;
    }

    public List<OrdenVentaViewModel> ListarPorCuenta(string cuenta) {
        var clienteWs = CrearClienteWs();
        var ordenes = clienteWs.listarOrdenesVentaPorCuenta(cuenta) ?? [];

        var respuesta = new List<OrdenVentaViewModel>();
        foreach (var item in ordenes) {
            respuesta.Add(ToViewModel(item));
        }

        return respuesta;
    }

    public OrdenVentaViewModel? Obtener(int id) {
        var clienteWs = CrearClienteWs();
        var orden = clienteWs.obtenerOrdenVenta(id);
        return orden is null ? null : ToViewModel(orden);
    }

    public void Guardar(OrdenVentaViewModel ordenVenta, Estado estado) {
        var clienteWs = CrearClienteWs();
        clienteWs.guardarOrdenVenta(ToSoap(ordenVenta), ParseEstado<estado>(estado));
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

    protected override OrdenVentaViewModel ToViewModel(ordenVenta source) {
        return new OrdenVentaViewModel {
            Id = source.id,
            Activo = source.activo,
            FechaRegistro = source.fechaSpecified ? source.fecha : DateTime.Today,
            TotalRegistrado = source.total,
            Cliente = source.cliente is null ? null : ToViewModel(source.cliente),
            ClienteIdSeleccionado = source.cliente?.id ?? 0,
            Lineas = MapearLineas(source.lineas)
        };
    }

    private static List<LineaOrdenVentaViewModel> MapearLineas(lineaOrdenVenta[]? lineasSoap) {
        var lineas = new List<LineaOrdenVentaViewModel>();
        if (lineasSoap is null) {
            return lineas;
        }

        foreach (var linea in lineasSoap) {
            lineas.Add(ToViewModel(linea));
        }

        return lineas;
    }

    private static ClienteViewModel ToViewModel(cliente source) {
        return new ClienteViewModel {
            Id = source.id,
            Activo = source.activo,
            Dni = source.dni ?? string.Empty,
            Nombre = source.nombre ?? string.Empty,
            ApellidoPaterno = source.apellidoPaterno ?? string.Empty,
            ApellidoMaterno = string.Empty,
            Correo = string.Empty,
            Telefono = string.Empty,
            Genero = ParseEnum(source.genero, genero.MASCULINO).ToString(),
            FechaNacimiento = source.fechaNacimientoSpecified ? source.fechaNacimiento : DateTime.Today,
            Categoria = ParseEnum(source.categoria, categoriaCliente.ESTANDARD).ToString(),
            LineaCredito = Convert.ToDecimal(source.lineaCredito),
            CuentaUsuario = source.cuentaUsuario is null
                ? null
                : new CuentaUsuarioViewModel {
                    Id = source.cuentaUsuario.id,
                    Activo = source.cuentaUsuario.activo,
                    UserName = source.cuentaUsuario.userName ?? string.Empty,
                    Password = string.Empty
                }
        };
    }

    private static LineaOrdenVentaViewModel ToViewModel(lineaOrdenVenta source) {
        var precioUnitario = source.cantidad <= 0 ? 0 : source.subTotal / source.cantidad;

        return new LineaOrdenVentaViewModel {
            Id = source.id,
            ProductoId = source.producto?.id ?? 0,
            ProductoNombre = source.producto?.nombre ?? string.Empty,
            Cantidad = source.cantidad,
            PrecioUnitario = precioUnitario
        };
    }

    protected override ordenVenta ToSoap(OrdenVentaViewModel source) {
        var clienteSoap = source.Cliente is null
            ? (source.ClienteIdSeleccionado > 0
                ? new cliente {
                    id = source.ClienteIdSeleccionado,
                    activo = true
                }
                : null)
            : ToSoap(source.Cliente);

        return new ordenVenta {
            id = source.Id,
            activo = source.Activo,
            fecha = source.FechaRegistro,
            fechaSpecified = true,
            total = source.Subtotal,
            cliente = clienteSoap,
            empleado = null,
            lineas = ToSoapLineas(source.Lineas)
        };
    }

    private static cliente ToSoap(ClienteViewModel source) {
        var generoCliente = Enum.TryParse<genero>(source.Genero, true, out var generoParseado)
            ? generoParseado
            : genero.MASCULINO;

        var categoria = Enum.TryParse<categoriaCliente>(source.Categoria, true, out var categoriaParseada)
            ? categoriaParseada
            : categoriaCliente.ESTANDARD;

        return new cliente {
            id = source.Id,
            activo = source.Activo,
            dni = source.Dni.Trim(),
            nombre = source.Nombre.Trim(),
            apellidoPaterno = source.ApellidoPaterno.Trim(),
            fechaNacimiento = source.FechaNacimiento ?? DateTime.Today,
            fechaNacimientoSpecified = true,
            genero = generoCliente,
            generoSpecified = true,
            categoria = categoria,
            categoriaSpecified = true,
            lineaCredito = Convert.ToDouble(source.LineaCredito),
            cuentaUsuario = source.CuentaUsuario is null
                ? null
                : new cuentaUsuario {
                    id = source.CuentaUsuario.Id,
                    activo = source.CuentaUsuario.Activo,
                    userName = source.CuentaUsuario.UserName,
                    password = source.CuentaUsuario.Password
                }
        };
    }

    private static lineaOrdenVenta[] ToSoapLineas(List<LineaOrdenVentaViewModel> lineasDomain) {
        var lineasSoap = new List<lineaOrdenVenta>();

        foreach (var linea in lineasDomain) {
            lineasSoap.Add(new lineaOrdenVenta {
                id = linea.Id,
                activo = true,
                cantidad = linea.Cantidad,
                subTotal = linea.SubTotal,
                producto = linea.ProductoId <= 0
                    ? null
                    : new producto {
                        id = linea.ProductoId,
                        activo = true,
                        nombre = linea.ProductoNombre,
                        precio = linea.PrecioUnitario,
                        unidadMedida = unidadMedida.UND,
                        unidadMedidaSpecified = true
                    }
            });
        }

        return [.. lineasSoap];
    }
}
