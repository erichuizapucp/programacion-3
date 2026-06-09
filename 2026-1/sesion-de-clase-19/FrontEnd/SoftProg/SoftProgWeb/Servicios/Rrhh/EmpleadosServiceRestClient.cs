using System.Net;
using System.Net.Http.Json;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Rrhh;

public class EmpleadosServiceRestClient : RestServiceClient<EmpleadoViewModel, EmpleadosServiceRestClient.EmpleadoRestDto>, IEmpleadosServiceClient {
    private const string ResourceConfig = "RestResources:Empleados";

    protected override string ResourceSetting => ResourceConfig;

    public EmpleadosServiceRestClient(IConfiguration configuration, IHttpClientFactory httpClientFactory)
        : base(configuration, httpClientFactory) {
    }

    public List<EmpleadoViewModel> Listar() {
        var payload = ListarPayload();
        var response = new List<EmpleadoViewModel>(payload.Count);
        foreach (var item in payload) {
            response.Add(ToViewModel(item));
        }

        return response;
    }

    public EmpleadoViewModel? Obtener(int id) {
        var payload = ObtenerPayload(id.ToString(), "Obtener empleado");
        return payload is null ? null : ToViewModel(payload);
    }

    public EmpleadoViewModel? BuscarPorDni(string dni) {
        var path = $"dni/{Uri.EscapeDataString(dni)}";
        var payload = ObtenerPayload(path, "Buscar empleado por DNI");
        return payload is null ? null : ToViewModel(payload);
    }

    public void Guardar(EmpleadoViewModel modelo, Estado estado) {
        GuardarPayload(ToRest(modelo), estado, modelo.Id.ToString());
    }

    public void Eliminar(int id) {
        EliminarPayload(id.ToString());
    }

    protected override EmpleadoViewModel ToViewModel(EmpleadoRestDto source) {
        var nombre = source.Nombre ?? string.Empty;
        var apellidoPaterno = source.ApellidoPaterno ?? string.Empty;

        return new EmpleadoViewModel {
            Id = source.Id,
            Activo = source.Activo,
            Dni = source.Dni ?? string.Empty,
            Nombre = nombre,
            ApellidoPaterno = apellidoPaterno,
            Genero = ParseGenero(source.Genero),
            FechaNacimiento = ParseFecha(source.FechaNacimiento),
            Cargo = ParseCargo(source.Cargo),
            Sueldo = source.Sueldo,
            AreaIdSeleccionada = source.Area?.Id ?? 0,
            AreaNombre = source.Area?.Nombre ?? string.Empty,
            NombreCompleto = $"{nombre} {apellidoPaterno}".Trim()
        };
    }

    protected override EmpleadoRestDto ToRest(EmpleadoViewModel source) {
        return new EmpleadoRestDto {
            Id = source.Id,
            Activo = source.Activo,
            Dni = source.Dni.Trim(),
            Nombre = source.Nombre.Trim(),
            ApellidoPaterno = source.ApellidoPaterno.Trim(),
            Genero = source.Genero.ToString(),
            FechaNacimiento = FormatFecha(source.FechaNacimiento ?? DateTime.Today),
            Cargo = source.Cargo.ToString(),
            Sueldo = source.Sueldo,
            Area = source.AreaIdSeleccionada <= 0
                ? null
                : new AreaRestDto {
                    Id = source.AreaIdSeleccionada,
                    Activo = true,
                    Nombre = source.AreaNombre
                }
        };
    }

    private List<EmpleadoRestDto> ListarPayload() {
        using var client = CreateClient(ResourceSetting);
        using var response = client.GetAsync(string.Empty).GetAwaiter().GetResult();
        EnsureSuccess(response, "Listar empleados");
        return response.Content.ReadFromJsonAsync<List<EmpleadoRestDto>>().GetAwaiter().GetResult() ?? [];
    }

    private EmpleadoRestDto? ObtenerPayload(string path, string operation) {
        using var client = CreateClient(ResourceSetting);
        using var response = client.GetAsync(path).GetAwaiter().GetResult();
        if (response.StatusCode == HttpStatusCode.NotFound) {
            return null;
        }

        EnsureSuccess(response, operation);
        return response.Content.ReadFromJsonAsync<EmpleadoRestDto>().GetAwaiter().GetResult();
    }

    private void GuardarPayload(EmpleadoRestDto payload, Estado estado, string idPath) {
        using var client = CreateClient(ResourceSetting);
        using var response = estado switch {
            Estado.Nuevo => client.PostAsJsonAsync(string.Empty, payload).GetAwaiter().GetResult(),
            Estado.Modificado => client.PutAsJsonAsync(idPath, payload).GetAwaiter().GetResult(),
            Estado.Eliminado => client.DeleteAsync(idPath).GetAwaiter().GetResult(),
            _ => throw new InvalidOperationException($"Estado no soportado: {estado}")
        };

        EnsureSuccess(response, "Guardar empleado");
    }

    private void EliminarPayload(string path) {
        using var client = CreateClient(ResourceSetting);
        using var response = client.DeleteAsync(path).GetAwaiter().GetResult();
        EnsureSuccess(response, "Eliminar empleado");
    }

    private static Genero ParseGenero(string? source) {
        return ParseEnum(source, Genero.MASCULINO);
    }

    private static Cargo ParseCargo(string? source) {
        return ParseEnum(source, Cargo.ASISTENTE);
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

    public sealed class EmpleadoRestDto {
        public int Id { get; set; }
        public bool Activo { get; set; }
        public string? Dni { get; set; }
        public string? Nombre { get; set; }
        public string? ApellidoPaterno { get; set; }
        public string? Genero { get; set; }
        public string? FechaNacimiento { get; set; }
        public string? Cargo { get; set; }
        public double Sueldo { get; set; }
        public AreaRestDto? Area { get; set; }
    }

    public sealed class AreaRestDto {
        public int Id { get; set; }
        public bool Activo { get; set; }
        public string? Nombre { get; set; }
    }
}
