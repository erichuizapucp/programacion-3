using System.Net;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.Servicios.Rest.Dtos.Clientes;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Clientes;

public class ClientesServiceRestClient : BaseRestServiceClient<ClienteViewModel, ClienteRestDto>, IClientesServiceClient {
    public ClientesServiceRestClient(IConfiguration configuration, IHttpClientFactory httpClientFactory)
        : base(configuration, httpClientFactory) {
        // IConfiguration e IHttpClientFactory son inyectados por el contenedor de DI.
    }

    public List<ClienteViewModel> Listar() {
        var payload = Api.Get<List<ClienteRestDto>>("/clientes");

        var response = new List<ClienteViewModel>(payload.Count);
        foreach (var item in payload) {
            response.Add(ToViewModel(item));
        }

        return response;
    }

    public ClienteViewModel? Obtener(int id) {
        try {
            var payload = Api.Get<ClienteRestDto>($"/clientes/{id}");
            return ToViewModel(payload);
        } catch (HttpRequestException ex) when (ex.StatusCode == HttpStatusCode.NotFound) {
            return null;
        }
    }

    public ClienteViewModel? BuscarPorDni(string dni) {
        try {
            var path = $"/clientes/dni/{Uri.EscapeDataString(dni)}";
            var payload = Api.Get<ClienteRestDto>(path);
            return ToViewModel(payload);
        } catch (HttpRequestException ex) when (ex.StatusCode == HttpStatusCode.NotFound) {
            return null;
        }
    }

    public ClienteViewModel? BuscarPorCuenta(string cuenta) {
        try {
            var path = $"/clientes/cuenta/{Uri.EscapeDataString(cuenta)}";
            var payload = Api.Get<ClienteRestDto>(path);
            return ToViewModel(payload);
        } catch (HttpRequestException ex) when (ex.StatusCode == HttpStatusCode.NotFound) {
            return null;
        }
    }

    public void Guardar(ClienteViewModel modelo, Estado estado) {
        var payload = ToRest(modelo);
        switch (estado) {
            case Estado.Nuevo:
                Api.Post("/clientes", payload);
                break;
            case Estado.Modificado:
                Api.Put($"/clientes/{modelo.Id}", payload);
                break;
            default:
                throw new InvalidOperationException($"Estado no soportado: {estado}");
        }
    }

    public void Eliminar(int id) {
        Api.Delete($"/clientes/{id}");
    }

    protected override ClienteViewModel ToViewModel(ClienteRestDto source) {
        return new ClienteViewModel {
            Id = source.Id,
            Activo = source.Activo,
            Dni = source.Dni,
            Nombre = source.Nombre,
            ApellidoPaterno = source.ApellidoPaterno,
            ApellidoMaterno = source.ApellidoMaterno ?? string.Empty,
            Correo = source.Correo ?? string.Empty,
            Telefono = source.Telefono ?? string.Empty,
            Genero = ParseClienteGenero(source.Genero),
            FechaNacimiento = ParseFecha(source.FechaNacimiento),
            Categoria = ParseClienteCategoria(source.Categoria),
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
}
