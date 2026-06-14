using System.Net;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.Servicios.Rest.Dtos.Rrhh;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Rrhh;

public class EmpleadosServiceRestClient : BaseRestServiceClient<EmpleadoViewModel, EmpleadoRestDto>, IEmpleadosServiceClient {
    public EmpleadosServiceRestClient(IConfiguration configuration, IHttpClientFactory httpClientFactory)
        : base(configuration, httpClientFactory) {
        // IConfiguration e IHttpClientFactory son inyectados por el contenedor de DI.
    }

    public List<EmpleadoViewModel> Listar() {
        var payload = Api.Get<List<EmpleadoRestDto>>("/empleados");

        var response = new List<EmpleadoViewModel>(payload.Count);
        foreach (var item in payload) {
            response.Add(ToViewModel(item));
        }

        return response;
    }

    public EmpleadoViewModel? Obtener(int id) {
        try {
            var payload = Api.Get<EmpleadoRestDto>($"/empleados/{id}");
            return ToViewModel(payload);
        } catch (HttpRequestException ex) when (ex.StatusCode == HttpStatusCode.NotFound) {
            return null;
        }
    }

    public EmpleadoViewModel? BuscarPorDni(string dni) {
        try {
            var path = $"/empleados/dni/{Uri.EscapeDataString(dni)}";
            var payload = Api.Get<EmpleadoRestDto>(path);
            return ToViewModel(payload);
        } catch (HttpRequestException ex) when (ex.StatusCode == HttpStatusCode.NotFound) {
            return null;
        }
    }

    public void Guardar(EmpleadoViewModel modelo, Estado estado) {
        var payload = ToRest(modelo);
        switch (estado) {
            case Estado.Nuevo:
                Api.Post("/empleados", payload);
                break;
            case Estado.Modificado:
                Api.Put($"/empleados/{modelo.Id}", payload);
                break;
            default:
                throw new InvalidOperationException($"Estado no soportado: {estado}");
        }
    }

    public void Eliminar(int id) {
        Api.Delete($"/empleados/{id}");
    }

    protected override EmpleadoViewModel ToViewModel(EmpleadoRestDto source) {
        var nombre = source.Nombre;
        var apellidoPaterno = source.ApellidoPaterno;

        return new EmpleadoViewModel {
            Id = source.Id,
            Activo = source.Activo,
            Dni = source.Dni,
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

    private static Genero ParseGenero(string? source) {
        return ParseEnum(source, Genero.MASCULINO);
    }

    private static Cargo ParseCargo(string? source) {
        return ParseEnum(source, Cargo.ASISTENTE);
    }

}
