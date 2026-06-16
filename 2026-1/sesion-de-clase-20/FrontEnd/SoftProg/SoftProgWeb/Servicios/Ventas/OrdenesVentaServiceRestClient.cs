using System.Net;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.Servicios.Rest.Dtos.Ventas;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Ventas;

public class OrdenesVentaServiceRestClient : BaseRestServiceClient<OrdenVentaViewModel, OrdenVentaRestDto>, IOrdenesVentaServiceClient {
    public OrdenesVentaServiceRestClient(IConfiguration configuration, IHttpClientFactory httpClientFactory)
        : base(configuration, httpClientFactory) {
        // IConfiguration e IHttpClientFactory son inyectados por el contenedor de DI.
    }

    public List<OrdenVentaViewModel> Listar() {
        var payload = Api.Get<List<OrdenVentaRestDto>>("/ordenes");

        var response = new List<OrdenVentaViewModel>(payload.Count);
        foreach (var item in payload) {
            response.Add(ToViewModel(item));
        }

        return response;
    }

    public List<OrdenVentaViewModel> ListarPorCuenta(string cuenta) {
        var path = $"/cuentas/{Uri.EscapeDataString(cuenta)}/ordenes";

        List<OrdenVentaRestDto> payload;
        try {
            payload = Api.Get<List<OrdenVentaRestDto>>(path);
        } catch (HttpRequestException ex) when (
            ex.StatusCode == HttpStatusCode.BadRequest ||
            ex.StatusCode == HttpStatusCode.NotFound) {
            payload = [];
        }

        var response = new List<OrdenVentaViewModel>(payload.Count);
        foreach (var item in payload) {
            response.Add(ToViewModel(item));
        }

        return response;
    }

    public OrdenVentaViewModel? Obtener(int id) {
        try {
            var payload = Api.Get<OrdenVentaRestDto>($"/ordenes/{id}");
            return ToViewModel(payload);
        } catch (HttpRequestException ex) when (ex.StatusCode == HttpStatusCode.NotFound) {
            return null;
        }
    }

    public void Guardar(OrdenVentaViewModel modelo, Estado estado) {
        var payload = ToRest(modelo);
        switch (estado) {
            case Estado.Nuevo:
                Api.Post("/ordenes", payload);
                break;
            case Estado.Modificado:
                Api.Put($"/ordenes/{modelo.Id}", payload);
                break;
            default:
                throw new InvalidOperationException($"Estado no soportado: {estado}");
        }
    }

    public void Eliminar(int id) {
        Api.Delete($"/ordenes/{id}");
    }

    protected override OrdenVentaViewModel ToViewModel(OrdenVentaRestDto source) {
        return new OrdenVentaViewModel {
            Id = source.Id,
            Activo = source.Activo,
            FechaRegistro = ParseFecha(source.Fecha),
            TotalRegistrado = source.Total,
            Cliente = source.Cliente is null ? null : ToViewModel(source.Cliente),
            ClienteIdSeleccionado = source.Cliente?.Id ?? 0,
            Lineas = ToViewModel(source.Lineas)
        };
    }

    private static List<LineaOrdenVentaViewModel> ToViewModel(List<LineaOrdenVentaRestDto>? source) {
        var lineas = new List<LineaOrdenVentaViewModel>();
        if (source is null) {
            return lineas;
        }

        foreach (var item in source) {
            var precioUnitario = item.Cantidad <= 0 ? 0 : item.SubTotal / item.Cantidad;
            lineas.Add(new LineaOrdenVentaViewModel {
                Id = item.Id,
                ProductoId = item.Producto?.Id ?? 0,
                ProductoNombre = item.Producto?.Nombre ?? string.Empty,
                Cantidad = item.Cantidad,
                PrecioUnitario = precioUnitario
            });
        }

        return lineas;
    }

    private static ClienteViewModel ToViewModel(ClienteRestDto source) {
        return new ClienteViewModel {
            Id = source.Id,
            Activo = source.Activo,
            Dni = source.Dni,
            Nombre = source.Nombre,
            ApellidoPaterno = source.ApellidoPaterno,
            ApellidoMaterno = source.ApellidoMaterno ?? string.Empty,
            Correo = source.Correo ?? string.Empty,
            Telefono = source.Telefono ?? string.Empty,
            Genero = ParseGenero(source.Genero),
            FechaNacimiento = ParseFecha(source.FechaNacimiento),
            Categoria = ParseCategoria(source.Categoria),
            LineaCredito = Convert.ToDecimal(source.LineaCredito ?? 0),
            CuentaUsuario = source.CuentaUsuario is null
                ? null
                : new CuentaUsuarioViewModel {
                    Id = source.CuentaUsuario.Id,
                    Activo = source.CuentaUsuario.Activo,
                    UserName = source.CuentaUsuario.UserName,
                    Password = string.Empty,
                    ConfirmarPassword = string.Empty
                }
        };
    }

    protected override OrdenVentaRestDto ToRest(OrdenVentaViewModel source) {
        var cliente = source.Cliente is null
            ? (source.ClienteIdSeleccionado > 0
                ? new ClienteRestDto {
                    Id = source.ClienteIdSeleccionado,
                    Activo = true
                }
                : null)
            : ToRest(source.Cliente);

        return new OrdenVentaRestDto {
            Id = source.Id,
            Activo = source.Activo,
            Fecha = FormatFecha(source.FechaRegistro),
            Total = source.Subtotal,
            Cliente = cliente,
            Lineas = ToRest(source.Lineas)
        };
    }

    private static ClienteRestDto ToRest(ClienteViewModel source) {
        return new ClienteRestDto {
            Id = source.Id,
            Activo = source.Activo,
            Dni = source.Dni.Trim(),
            Nombre = source.Nombre.Trim(),
            ApellidoPaterno = source.ApellidoPaterno.Trim(),
            ApellidoMaterno = source.ApellidoMaterno.Trim(),
            Correo = source.Correo.Trim(),
            Telefono = source.Telefono.Trim(),
            Genero = ParseGenero(source.Genero),
            FechaNacimiento = FormatFecha(source.FechaNacimiento ?? DateTime.Today),
            Categoria = ParseCategoria(source.Categoria),
            LineaCredito = Convert.ToDouble(source.LineaCredito),
            CuentaUsuario = source.CuentaUsuario is null
                ? null
                : new CuentaUsuarioRestDto {
                    Id = source.CuentaUsuario.Id,
                    Activo = source.CuentaUsuario.Activo,
                    UserName = source.CuentaUsuario.UserName.Trim(),
                    Password = source.CuentaUsuario.Password
                }
        };
    }

    private static List<LineaOrdenVentaRestDto> ToRest(List<LineaOrdenVentaViewModel> source) {
        var lineas = new List<LineaOrdenVentaRestDto>(source.Count);
        foreach (var item in source) {
            lineas.Add(new LineaOrdenVentaRestDto {
                Id = item.Id,
                Activo = true,
                Cantidad = item.Cantidad,
                SubTotal = item.SubTotal,
                Producto = item.ProductoId <= 0
                    ? null
                    : new ProductoRestDto {
                        Id = item.ProductoId,
                        Activo = true,
                        Nombre = item.ProductoNombre,
                        Precio = item.PrecioUnitario,
                        UnidadMedida = "UND"
                    }
            });
        }

        return lineas;
    }

    private static string ParseGenero(string? source) {
        return string.Equals(source, "FEMENINO", StringComparison.OrdinalIgnoreCase)
            ? "FEMENINO"
            : "MASCULINO";
    }

    private static string ParseCategoria(string? source) {
        if (string.IsNullOrWhiteSpace(source)) {
            return "ESTANDARD";
        }

        return source.Trim().ToUpperInvariant();
    }

}
