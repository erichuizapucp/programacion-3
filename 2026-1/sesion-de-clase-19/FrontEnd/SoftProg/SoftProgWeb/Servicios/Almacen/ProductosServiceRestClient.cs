using System.Net;
using System.Net.Http.Json;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Almacen;

public class ProductosServiceRestClient : RestServiceClient<ProductoViewModel, ProductosServiceRestClient.ProductoRestDto>, IProductosServiceClient {
    private const string ResourceConfig = "RestResources:Productos";

    protected override string ResourceSetting => ResourceConfig;

    public ProductosServiceRestClient(IConfiguration configuration, IHttpClientFactory httpClientFactory)
        : base(configuration, httpClientFactory) {
    }

    public List<ProductoViewModel> Listar() {
        var payload = ListarPayload();
        var response = new List<ProductoViewModel>(payload.Count);
        foreach (var item in payload) {
            response.Add(ToViewModel(item));
        }

        return response;
    }

    public ProductoViewModel? Obtener(int id) {
        var payload = ObtenerPayload(id.ToString());
        return payload is null ? null : ToViewModel(payload);
    }

    public void Guardar(ProductoViewModel modelo, Estado estado) {
        GuardarPayload(ToRest(modelo), estado, modelo.Id.ToString());
    }

    public void Eliminar(int id) {
        EliminarPayload(id.ToString());
    }

    protected override ProductoViewModel ToViewModel(ProductoRestDto source) {
        return new ProductoViewModel {
            Id = source.Id,
            Activo = source.Activo,
            Nombre = source.Nombre ?? string.Empty,
            Precio = Convert.ToDecimal(source.Precio),
            UnidadMedida = ToViewModelUnidadMedida(source.UnidadMedida)
        };
    }

    protected override ProductoRestDto ToRest(ProductoViewModel source) {
        return new ProductoRestDto {
            Id = source.Id,
            Activo = source.Activo,
            Nombre = source.Nombre.Trim(),
            Precio = Convert.ToDouble(source.Precio),
            UnidadMedida = ToRestUnidadMedida(source.UnidadMedida)
        };
    }

    private List<ProductoRestDto> ListarPayload() {
        using var client = CreateClient(ResourceSetting);
        using var response = client.GetAsync(string.Empty).GetAwaiter().GetResult();
        EnsureSuccess(response, "Listar productos");
        return response.Content.ReadFromJsonAsync<List<ProductoRestDto>>().GetAwaiter().GetResult() ?? [];
    }

    private ProductoRestDto? ObtenerPayload(string path) {
        using var client = CreateClient(ResourceSetting);
        using var response = client.GetAsync(path).GetAwaiter().GetResult();
        if (response.StatusCode == HttpStatusCode.NotFound) {
            return null;
        }

        EnsureSuccess(response, "Obtener producto");
        return response.Content.ReadFromJsonAsync<ProductoRestDto>().GetAwaiter().GetResult();
    }

    private void GuardarPayload(ProductoRestDto payload, Estado estado, string idPath) {
        using var client = CreateClient(ResourceSetting);
        using var response = estado switch {
            Estado.Nuevo => client.PostAsJsonAsync(string.Empty, payload).GetAwaiter().GetResult(),
            Estado.Modificado => client.PutAsJsonAsync(idPath, payload).GetAwaiter().GetResult(),
            Estado.Eliminado => client.DeleteAsync(idPath).GetAwaiter().GetResult(),
            _ => throw new InvalidOperationException($"Estado no soportado: {estado}")
        };

        EnsureSuccess(response, "Guardar producto");
    }

    private void EliminarPayload(string path) {
        using var client = CreateClient(ResourceSetting);
        using var response = client.DeleteAsync(path).GetAwaiter().GetResult();
        EnsureSuccess(response, "Eliminar producto");
    }

    private static UnidadMedidaEnum ToViewModelUnidadMedida(string? source) {
        return source?.ToUpperInvariant() switch {
            "UND" => UnidadMedidaEnum.Unidad,
            "UNIDAD" => UnidadMedidaEnum.Unidad,
            "KILOS" => UnidadMedidaEnum.Kilos,
            "ONZAS" => UnidadMedidaEnum.Onzas,
            "LITROS" => UnidadMedidaEnum.Litros,
            _ => UnidadMedidaEnum.Unidad
        };
    }

    private static string ToRestUnidadMedida(UnidadMedidaEnum source) {
        return source switch {
            UnidadMedidaEnum.Unidad => "UND",
            UnidadMedidaEnum.Kilos => "Kilos",
            UnidadMedidaEnum.Onzas => "Onzas",
            UnidadMedidaEnum.Litros => "Litros",
            _ => "UND"
        };
    }

    public sealed class ProductoRestDto {
        public int Id { get; set; }
        public bool Activo { get; set; }
        public string? Nombre { get; set; }
        public double Precio { get; set; }
        public string? UnidadMedida { get; set; }
    }
}
