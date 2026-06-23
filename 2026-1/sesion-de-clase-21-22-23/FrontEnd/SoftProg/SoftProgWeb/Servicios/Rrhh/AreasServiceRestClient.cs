using System.Net;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.Servicios.Rest.Dtos.Rrhh;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Rrhh;

public class AreasServiceRestClient : BaseRestServiceClient<AreaViewModel, AreaRestDto>, IAreaServiceClient {
    public AreasServiceRestClient(IConfiguration configuration, IHttpClientFactory httpClientFactory)
        : base(configuration, httpClientFactory) {
        // IConfiguration e IHttpClientFactory son inyectados por el contenedor de DI.
    }

    public List<AreaViewModel> Listar() {
        var payload = Api.Get<List<AreaRestDto>>("/areas");

        var response = new List<AreaViewModel>(payload.Count);
        foreach (var item in payload) {
            response.Add(ToViewModel(item));
        }

        return response;
    }

    public AreaViewModel? Obtener(int id) {
        try {
            var payload = Api.Get<AreaRestDto>($"/areas/{id}");
            return ToViewModel(payload);
        } catch (HttpRequestException ex) when (ex.StatusCode == HttpStatusCode.NotFound) {
            return null;
        }
    }

    public void Guardar(AreaViewModel modelo, Estado estado) {
        var payload = ToRest(modelo);
        switch (estado) {
            case Estado.Nuevo:
                Api.Post("/areas", payload);
                break;
            case Estado.Modificado:
                Api.Put($"/areas/{modelo.Id}", payload);
                break;
            default:
                throw new InvalidOperationException($"Estado no soportado: {estado}");
        }
    }

    public void Eliminar(int id) {
        Api.Delete($"/areas/{id}");
    }

    protected override AreaViewModel ToViewModel(AreaRestDto source) {
        return new AreaViewModel {
            Id = source.Id,
            Activo = source.Activo,
            Nombre = source.Nombre
        };
    }

    protected override AreaRestDto ToRest(AreaViewModel source) {
        return new AreaRestDto {
            Id = source.Id,
            Activo = source.Activo,
            Nombre = source.Nombre.Trim()
        };
    }
}
