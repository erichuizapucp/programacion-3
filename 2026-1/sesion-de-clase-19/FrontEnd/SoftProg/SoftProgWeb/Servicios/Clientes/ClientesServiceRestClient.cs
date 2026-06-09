using System.Net;
using System.Net.Http.Json;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Clientes;

public class ClientesServiceRestClient : RestServiceClient<ClienteViewModel, ClientesServiceRestClient.ClienteRestDto>, IClientesServiceClient {
    private const string ResourceConfig = "RestResources:Clientes";

    protected override string ResourceSetting => ResourceConfig;

    public ClientesServiceRestClient(IConfiguration configuration, IHttpClientFactory httpClientFactory)
        : base(configuration, httpClientFactory) {
    }

    public List<ClienteViewModel> Listar() {
        var payload = ListarPayload();
        var response = new List<ClienteViewModel>(payload.Count);
        foreach (var item in payload) {
            response.Add(ToViewModel(item));
        }

        return response;
    }

    public ClienteViewModel? Obtener(int id) {
        var payload = ObtenerPayload(id.ToString(), "Obtener cliente");
        return payload is null ? null : ToViewModel(payload);
    }

    public ClienteViewModel? BuscarPorDni(string dni) {
        var path = $"dni/{Uri.EscapeDataString(dni)}";
        var payload = ObtenerPayload(path, "Buscar cliente por DNI");
        return payload is null ? null : ToViewModel(payload);
    }

    public ClienteViewModel? BuscarPorCuenta(string cuenta) {
        var path = $"cuenta/{Uri.EscapeDataString(cuenta)}";
        var payload = ObtenerPayload(path, "Buscar cliente por cuenta");
        return payload is null ? null : ToViewModel(payload);
    }

    public void Guardar(ClienteViewModel modelo, Estado estado) {
        GuardarPayload(ToRest(modelo), estado, modelo.Id.ToString());
    }

    public void Eliminar(int id) {
        EliminarPayload(id.ToString());
    }

    protected override ClienteViewModel ToViewModel(ClienteRestDto source) {
        return new ClienteViewModel {
            Id = source.Id,
            Activo = source.Activo,
            Dni = source.Dni ?? string.Empty,
            Nombre = source.Nombre ?? string.Empty,
            ApellidoPaterno = source.ApellidoPaterno ?? string.Empty,
            ApellidoMaterno = source.ApellidoMaterno ?? string.Empty,
            Correo = source.Correo ?? string.Empty,
            Telefono = source.Telefono ?? string.Empty,
            Genero = ParseClienteGenero(source.Genero),
            FechaNacimiento = ParseFecha(source.FechaNacimiento),
            Categoria = ParseClienteCategoria(source.Categoria),
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

    protected override ClienteRestDto ToRest(ClienteViewModel source) {
        return new ClienteRestDto {
            Id = source.Id,
            Activo = source.Activo,
            Dni = source.Dni.Trim(),
            Nombre = source.Nombre.Trim(),
            ApellidoPaterno = source.ApellidoPaterno.Trim(),
            ApellidoMaterno = source.ApellidoMaterno.Trim(),
            Correo = source.Correo.Trim(),
            Telefono = source.Telefono.Trim(),
            Genero = ParseClienteGenero(source.Genero),
            FechaNacimiento = FormatFecha(source.FechaNacimiento ?? DateTime.Today),
            Categoria = ParseClienteCategoria(source.Categoria),
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

    private List<ClienteRestDto> ListarPayload() {
        using var client = CreateClient(ResourceSetting);
        using var response = client.GetAsync(string.Empty).GetAwaiter().GetResult();
        EnsureSuccess(response, "Listar clientes");
        return response.Content.ReadFromJsonAsync<List<ClienteRestDto>>().GetAwaiter().GetResult() ?? [];
    }

    private ClienteRestDto? ObtenerPayload(string path, string operation) {
        using var client = CreateClient(ResourceSetting);
        using var response = client.GetAsync(path).GetAwaiter().GetResult();
        if (response.StatusCode == HttpStatusCode.NotFound) {
            return null;
        }

        EnsureSuccess(response, operation);
        return response.Content.ReadFromJsonAsync<ClienteRestDto>().GetAwaiter().GetResult();
    }

    private void GuardarPayload(ClienteRestDto payload, Estado estado, string idPath) {
        using var client = CreateClient(ResourceSetting);
        using var response = estado switch {
            Estado.Nuevo => client.PostAsJsonAsync(string.Empty, payload).GetAwaiter().GetResult(),
            Estado.Modificado => client.PutAsJsonAsync(idPath, payload).GetAwaiter().GetResult(),
            Estado.Eliminado => client.DeleteAsync(idPath).GetAwaiter().GetResult(),
            _ => throw new InvalidOperationException($"Estado no soportado: {estado}")
        };

        EnsureSuccess(response, "Guardar cliente");
    }

    private void EliminarPayload(string path) {
        using var client = CreateClient(ResourceSetting);
        using var response = client.DeleteAsync(path).GetAwaiter().GetResult();
        EnsureSuccess(response, "Eliminar cliente");
    }

    private static string ParseClienteGenero(string? source) {
        return string.Equals(source, "FEMENINO", StringComparison.OrdinalIgnoreCase)
            ? "FEMENINO"
            : "MASCULINO";
    }

    private static string ParseClienteCategoria(string? source) {
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
