using System.Net;
using System.Net.Http.Json;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Rrhh;

public class AreasServiceRestClient : RestServiceClient<AreaViewModel, AreasServiceRestClient.AreaRestDto>, IAreaServiceClient {
    private const string ResourceConfig = "RestResources:Areas";

    protected override string ResourceSetting => ResourceConfig;

    public AreasServiceRestClient(IConfiguration configuration, IHttpClientFactory httpClientFactory)
        : base(configuration, httpClientFactory) {
    }

    public List<AreaViewModel> Listar() {
        var payload = ListarPayload();
        var response = new List<AreaViewModel>(payload.Count);
        foreach (var item in payload) {
            response.Add(ToViewModel(item));
        }

        return response;
    }

    public AreaViewModel? Obtener(int id) {
        var payload = ObtenerPayload(id.ToString());
        return payload is null ? null : ToViewModel(payload);
    }

    public void Guardar(AreaViewModel modelo, Estado estado) {
        GuardarPayload(ToRest(modelo), estado, modelo.Id.ToString());
    }

    public void Eliminar(int id) {
        EliminarPayload(id.ToString());
    }

    protected override AreaViewModel ToViewModel(AreaRestDto source) {
        return new AreaViewModel {
            Id = source.Id,
            Activo = source.Activo,
            Nombre = source.Nombre ?? string.Empty
        };
    }

    protected override AreaRestDto ToRest(AreaViewModel source) {
        return new AreaRestDto {
            Id = source.Id,
            Activo = source.Activo,
            Nombre = source.Nombre.Trim()
        };
    }

    private List<AreaRestDto> ListarPayload() {
        using var client = CreateClient(ResourceSetting);
        using var response = client.GetAsync(string.Empty).GetAwaiter().GetResult();
        EnsureSuccess(response, "Listar areas");
        return response.Content.ReadFromJsonAsync<List<AreaRestDto>>().GetAwaiter().GetResult() ?? [];
    }

    private AreaRestDto? ObtenerPayload(string path) {
        using var client = CreateClient(ResourceSetting);
        using var response = client.GetAsync(path).GetAwaiter().GetResult();
        if (response.StatusCode == HttpStatusCode.NotFound) {
            return null;
        }

        EnsureSuccess(response, "Obtener area");
        return response.Content.ReadFromJsonAsync<AreaRestDto>().GetAwaiter().GetResult();
    }

    private void GuardarPayload(AreaRestDto payload, Estado estado, string idPath) {
        using var client = CreateClient(ResourceSetting);
        using var response = estado switch {
            Estado.Nuevo => client.PostAsJsonAsync(string.Empty, payload).GetAwaiter().GetResult(),
            Estado.Modificado => client.PutAsJsonAsync(idPath, payload).GetAwaiter().GetResult(),
            Estado.Eliminado => client.DeleteAsync(idPath).GetAwaiter().GetResult(),
            _ => throw new InvalidOperationException($"Estado no soportado: {estado}")
        };

        EnsureSuccess(response, "Guardar area");
    }

    private void EliminarPayload(string path) {
        using var client = CreateClient(ResourceSetting);
        using var response = client.DeleteAsync(path).GetAwaiter().GetResult();
        EnsureSuccess(response, "Eliminar area");
    }

    public sealed class AreaRestDto {
        public int Id { get; set; }
        public bool Activo { get; set; }
        public string? Nombre { get; set; }
    }
}
