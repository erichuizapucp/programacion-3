using System.Net;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.Servicios.Rest.Dtos.Almacen;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Almacen;

public class ProductosServiceRestClient : BaseRestServiceClient<ProductoViewModel, ProductoRestDto>, IProductosServiceClient {
    public ProductosServiceRestClient(IConfiguration configuration, IHttpClientFactory httpClientFactory)
        : base(configuration, httpClientFactory) {
        // IConfiguration e IHttpClientFactory son inyectados por el contenedor de DI.
    }

    public List<ProductoViewModel> Listar() {
        var payload = Api.Get<List<ProductoRestDto>>("/productos");

        var response = new List<ProductoViewModel>(payload.Count);
        foreach (var item in payload) {
            response.Add(ToViewModel(item));
        }

        return response;
    }

    public ProductoViewModel? Obtener(int id) {
        try {
            var payload = Api.Get<ProductoRestDto>($"/productos/{id}");
            return ToViewModel(payload);
        } catch (HttpRequestException ex) when (ex.StatusCode == HttpStatusCode.NotFound) {
            return null;
        }
    }

    public void Guardar(ProductoViewModel modelo, Estado estado) {
        var payload = ToRest(modelo);
        switch (estado) {
            case Estado.Nuevo:
                Api.Post("/productos", payload);
                break;
            case Estado.Modificado:
                Api.Put($"/productos/{modelo.Id}", payload);
                break;
            default:
                throw new InvalidOperationException($"Estado no soportado: {estado}");
        }
    }

    public void Eliminar(int id) {
        Api.Delete($"/productos/{id}");
    }

    protected override ProductoViewModel ToViewModel(ProductoRestDto source) {
        return new ProductoViewModel {
            Id = source.Id,
            Activo = source.Activo,
            Nombre = source.Nombre,
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
}
