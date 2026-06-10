using System.Net;
using System.Net.Http.Json;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Cuentas;

public class CuentasUsuarioRestClient : RestServiceClient<CuentaUsuarioViewModel, CuentasUsuarioRestClient.CuentaUsuarioRestDto>, 
    ICuentasUsuarioServiceClient {
    private const string ResourceConfig = "RestResources:CuentasUsuario";

    protected override string ResourceSetting => ResourceConfig;

    public CuentasUsuarioRestClient(IConfiguration configuration, IHttpClientFactory httpClientFactory)
        : base(configuration, httpClientFactory) {
    }

    public bool Login(string username, string password) {
        using var client = CreateClient(ResourceSetting);
        using var response = client.PostAsJsonAsync("login", 
            new CuentaUsuarioRestDto {
            UserName = username.Trim(),
            Password = password
        }).GetAwaiter().GetResult();
        if (response.StatusCode == HttpStatusCode.Unauthorized) {
            return false;
        }

        EnsureSuccess(response, "Login de cuenta");
        return true;
    }

    public List<CuentaUsuarioViewModel> Listar() {
        var payload = ListarPayload();
        var respuesta = new List<CuentaUsuarioViewModel>();
        foreach (var item in payload) {
            respuesta.Add(ToViewModel(item, includePassword: false));
        }

        return respuesta;
    }

    public CuentaUsuarioViewModel? Obtener(int id) {
        var payload = ObtenerPayload(id.ToString(), "Obtener cuenta");
        return payload is null ? null : ToViewModel(payload, includePassword: true);
    }

    public CuentaUsuarioViewModel? ObtenerPorUsername(string username) {
        var cuentas = Listar();
        return cuentas.FirstOrDefault(actual =>
            string.Equals(actual.UserName, username, StringComparison.OrdinalIgnoreCase));
    }

    public void Guardar(CuentaUsuarioViewModel modelo, Estado estado) {
        var fallback = string.Empty;
        if (modelo.Id > 0) {
            var actual = Obtener(modelo.Id);
            fallback = actual?.Password ?? string.Empty;
        }

        var payload = ToRest(modelo, fallback);
        GuardarPayload(payload, estado, modelo.Id.ToString());
    }

    public void Eliminar(int id) {
        EliminarPayload(id.ToString());
    }

    protected override CuentaUsuarioViewModel ToViewModel(CuentaUsuarioRestDto source) {
        return ToViewModel(source, includePassword: false);
    }

    protected override CuentaUsuarioRestDto ToRest(CuentaUsuarioViewModel source) {
        return ToRest(source, string.Empty);
    }

    private List<CuentaUsuarioRestDto> ListarPayload() {
        using var client = CreateClient(ResourceSetting);
        using var response = client.GetAsync(string.Empty).GetAwaiter().GetResult();
        EnsureSuccess(response, "Listar cuentas");
        return response.Content.ReadFromJsonAsync<List<CuentaUsuarioRestDto>>().GetAwaiter().GetResult() ?? [];
    }

    private CuentaUsuarioRestDto? ObtenerPayload(string path, string operation) {
        using var client = CreateClient(ResourceSetting);
        using var response = client.GetAsync(path).GetAwaiter().GetResult();
        if (response.StatusCode == HttpStatusCode.NotFound) {
            return null;
        }

        EnsureSuccess(response, operation);
        return response.Content.ReadFromJsonAsync<CuentaUsuarioRestDto>().GetAwaiter().GetResult();
    }

    private void GuardarPayload(CuentaUsuarioRestDto payload, Estado estado, string idPath) {
        using var client = CreateClient(ResourceSetting);
        using var response = estado switch {
            Estado.Nuevo => client.PostAsJsonAsync(string.Empty, payload).GetAwaiter().GetResult(),
            Estado.Modificado => client.PutAsJsonAsync(idPath, payload).GetAwaiter().GetResult(),
            Estado.Eliminado => client.DeleteAsync(idPath).GetAwaiter().GetResult(),
            _ => throw new InvalidOperationException($"Estado no soportado: {estado}")
        };

        EnsureSuccess(response, "Guardar cuenta");
    }

    private void EliminarPayload(string path) {
        using var client = CreateClient(ResourceSetting);
        using var response = client.DeleteAsync(path).GetAwaiter().GetResult();
        EnsureSuccess(response, "Eliminar cuenta");
    }

    private static CuentaUsuarioViewModel ToViewModel(CuentaUsuarioRestDto source, bool includePassword) {
        return new CuentaUsuarioViewModel {
            Id = source.Id,
            Activo = source.Activo,
            UserName = source.UserName ?? string.Empty,
            Password = includePassword ? source.Password ?? string.Empty : string.Empty,
            ConfirmarPassword = string.Empty
        };
    }

    private static CuentaUsuarioRestDto ToRest(CuentaUsuarioViewModel source, string passwordFallback) {
        var password = string.IsNullOrWhiteSpace(source.Password) ? passwordFallback : source.Password;

        return new CuentaUsuarioRestDto {
            Id = source.Id,
            Activo = source.Activo,
            UserName = source.UserName.Trim(),
            Password = password
        };
    }

    public sealed class CuentaUsuarioRestDto {
        public int Id { get; set; }
        public bool Activo { get; set; }
        public string? UserName { get; set; }
        public string? Password { get; set; }
    }
}
