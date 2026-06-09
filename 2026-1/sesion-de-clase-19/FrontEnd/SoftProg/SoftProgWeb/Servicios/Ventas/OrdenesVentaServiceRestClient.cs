using System.Net;
using System.Net.Http.Json;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Ventas;

public class OrdenesVentaServiceRestClient : RestServiceClient<OrdenVentaViewModel, OrdenesVentaServiceRestClient.OrdenVentaRestDto>, IOrdenesVentaServiceClient {
    private const string ResourceConfig = "RestResources:OrdenesVenta";
    private const string CuentasResourceSetting = "RestResources:CuentasUsuario";

    protected override string ResourceSetting => ResourceConfig;

    public OrdenesVentaServiceRestClient(IConfiguration configuration, IHttpClientFactory httpClientFactory)
        : base(configuration, httpClientFactory) {
    }

    public List<OrdenVentaViewModel> Listar() {
        var payload = ListarPayload();
        var response = new List<OrdenVentaViewModel>(payload.Count);
        foreach (var item in payload) {
            response.Add(ToViewModel(item));
        }

        return response;
    }

    public List<OrdenVentaViewModel> ListarPorCuenta(string cuenta) {
        var payload = ListarPayloadPorCuenta(cuenta);

        var response = new List<OrdenVentaViewModel>(payload.Count);
        foreach (var item in payload) {
            response.Add(ToViewModel(item));
        }

        return response;
    }

    public OrdenVentaViewModel? Obtener(int id) {
        var payload = ObtenerPayload(id.ToString());
        return payload is null ? null : ToViewModel(payload);
    }

    public void Guardar(OrdenVentaViewModel modelo, Estado estado) {
        GuardarPayload(ToRest(modelo), estado, modelo.Id.ToString());
    }

    public void Eliminar(int id) {
        EliminarPayload(id.ToString());
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
            Dni = source.Dni ?? string.Empty,
            Nombre = source.Nombre ?? string.Empty,
            ApellidoPaterno = source.ApellidoPaterno ?? string.Empty,
            ApellidoMaterno = source.ApellidoMaterno ?? string.Empty,
            Correo = source.Correo ?? string.Empty,
            Telefono = source.Telefono ?? string.Empty,
            Genero = ParseGenero(source.Genero),
            FechaNacimiento = ParseFecha(source.FechaNacimiento),
            Categoria = ParseCategoria(source.Categoria),
            LineaCredito = Convert.ToDecimal(source.LineaCredito),
            CuentaUsuario = source.CuentaUsuario is null
                ? null
                : new CuentaUsuarioViewModel {
                    Id = source.CuentaUsuario.Id,
                    Activo = source.CuentaUsuario.Activo,
                    UserName = source.CuentaUsuario.UserName ?? string.Empty,
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

    private List<OrdenVentaRestDto> ListarPayload() {
        using var client = CreateClient(ResourceSetting);
        using var response = client.GetAsync(string.Empty).GetAwaiter().GetResult();
        EnsureSuccess(response, "Listar ordenes de venta");
        return response.Content.ReadFromJsonAsync<List<OrdenVentaRestDto>>().GetAwaiter().GetResult() ?? [];
    }

    private List<OrdenVentaRestDto> ListarPayloadPorCuenta(string cuenta) {
        using var client = CreateClient(CuentasResourceSetting);
        var path = $"{Uri.EscapeDataString(cuenta)}/ordenes";
        using var response = client.GetAsync(path).GetAwaiter().GetResult();
        if (response.StatusCode == HttpStatusCode.BadRequest || response.StatusCode == HttpStatusCode.NotFound) {
            return [];
        }

        EnsureSuccess(response, "Listar ordenes por cuenta");
        return response.Content.ReadFromJsonAsync<List<OrdenVentaRestDto>>().GetAwaiter().GetResult() ?? [];
    }

    private OrdenVentaRestDto? ObtenerPayload(string path) {
        using var client = CreateClient(ResourceSetting);
        using var response = client.GetAsync(path).GetAwaiter().GetResult();
        if (response.StatusCode == HttpStatusCode.NotFound) {
            return null;
        }

        EnsureSuccess(response, "Obtener orden de venta");
        return response.Content.ReadFromJsonAsync<OrdenVentaRestDto>().GetAwaiter().GetResult();
    }

    private void GuardarPayload(OrdenVentaRestDto payload, Estado estado, string idPath) {
        using var client = CreateClient(ResourceSetting);
        using var response = estado switch {
            Estado.Nuevo => client.PostAsJsonAsync(string.Empty, payload).GetAwaiter().GetResult(),
            Estado.Modificado => client.PutAsJsonAsync(idPath, payload).GetAwaiter().GetResult(),
            Estado.Eliminado => client.DeleteAsync(idPath).GetAwaiter().GetResult(),
            _ => throw new InvalidOperationException($"Estado no soportado: {estado}")
        };

        EnsureSuccess(response, "Guardar orden de venta");
    }

    private void EliminarPayload(string path) {
        using var client = CreateClient(ResourceSetting);
        using var response = client.DeleteAsync(path).GetAwaiter().GetResult();
        EnsureSuccess(response, "Eliminar orden de venta");
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

    private static DateTime ParseFecha(string? source) {
        if (string.IsNullOrWhiteSpace(source)) {
            return DateTime.Today;
        }

        var normalized = source.Trim();
        var bracketIndex = normalized.IndexOf('[');
        if (bracketIndex >= 0) {
            normalized = normalized[..bracketIndex];
        }

        if (DateTimeOffset.TryParse(normalized, out var dto)) {
            return dto.LocalDateTime;
        }

        if (DateTime.TryParse(normalized, out var dt)) {
            return dt;
        }

        return DateTime.Today;
    }

    private static string FormatFecha(DateTime source) {
        return source.ToUniversalTime().ToString("yyyy-MM-dd'T'HH:mm:ss'Z'");
    }

    public sealed class OrdenVentaRestDto {
        public int Id { get; set; }
        public bool Activo { get; set; }
        public string? Fecha { get; set; }
        public double Total { get; set; }
        public ClienteRestDto? Cliente { get; set; }
        public List<LineaOrdenVentaRestDto>? Lineas { get; set; }
    }

    public sealed class LineaOrdenVentaRestDto {
        public int Id { get; set; }
        public bool Activo { get; set; }
        public int Cantidad { get; set; }
        public double SubTotal { get; set; }
        public ProductoRestDto? Producto { get; set; }
    }

    public sealed class ProductoRestDto {
        public int Id { get; set; }
        public bool Activo { get; set; }
        public string? Nombre { get; set; }
        public double Precio { get; set; }
        public string? UnidadMedida { get; set; }
    }

    public sealed class ClienteRestDto {
        public int Id { get; set; }
        public bool Activo { get; set; }
        public string? Dni { get; set; }
        public string? Nombre { get; set; }
        public string? ApellidoPaterno { get; set; }
        public string? ApellidoMaterno { get; set; }
        public string? Correo { get; set; }
        public string? Telefono { get; set; }
        public string? Genero { get; set; }
        public string? FechaNacimiento { get; set; }
        public string? Categoria { get; set; }
        public double LineaCredito { get; set; }
        public CuentaUsuarioRestDto? CuentaUsuario { get; set; }
    }

    public sealed class CuentaUsuarioRestDto {
        public int Id { get; set; }
        public bool Activo { get; set; }
        public string? UserName { get; set; }
        public string? Password { get; set; }
    }
}
