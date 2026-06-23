using SoftProgWS;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Almacen;

public class ProductosServiceSoapClient : SoapServiceClient<ProductoViewModel, producto>, IProductosServiceClient {
    private const string EndpointSetting = "SoapEndpoints:Productos";

    public ProductosServiceSoapClient(IConfiguration configuration)
        : base(configuration) {
    }

    public List<ProductoViewModel> Listar() {
        var clienteWs = CrearClienteWs();
        var productos = clienteWs.listarProductos() ?? [];

        var respuesta = new List<ProductoViewModel>();
        foreach (var item in productos) {
            respuesta.Add(ToViewModel(item));
        }

        return respuesta;
    }

    public ProductoViewModel? Obtener(int id) {
        var clienteWs = CrearClienteWs();
        var producto = clienteWs.obtenerProducto(id);
        return producto is null ? null : ToViewModel(producto);
    }

    public void Guardar(ProductoViewModel producto, Estado estado) {
        var clienteWs = CrearClienteWs();
        clienteWs.guardarProducto(ToSoap(producto), ParseEstado<estado>(estado));
    }

    public void Eliminar(int id) {
        var clienteWs = CrearClienteWs();
        clienteWs.eliminarProducto(id);
    }

    private ProductosWSClient CrearClienteWs() {
        return (ProductosWSClient)CreateClient();
    }

    protected override object CreateClient() {
        var endpoint = ProductosWSClient.EndpointConfiguration.ProductosWSPort;
        var url = Configuration[EndpointSetting]?.Trim();

        if (string.IsNullOrWhiteSpace(url)) {
            return new ProductosWSClient(endpoint);
        }

        return new ProductosWSClient(endpoint, url);
    }

    protected override ProductoViewModel ToViewModel(producto source) {
        return new ProductoViewModel {
            Id = source.id,
            Activo = source.activo,
            Nombre = source.nombre ?? string.Empty,
            Precio = Convert.ToDecimal(source.precio),
            UnidadMedida = ToViewModelUnidadMedida(source.unidadMedida)
        };
    }

    protected override producto ToSoap(ProductoViewModel source) {
        return new producto {
            id = source.Id,
            activo = source.Activo,
            nombre = source.Nombre.Trim(),
            precio = Convert.ToDouble(source.Precio),
            unidadMedida = ToSoapUnidadMedida(source.UnidadMedida),
            unidadMedidaSpecified = true
        };
    }

    private static UnidadMedidaEnum ToViewModelUnidadMedida(unidadMedida source) {
        return source switch {
            unidadMedida.UND => UnidadMedidaEnum.Unidad,
            unidadMedida.Kilos => UnidadMedidaEnum.Kilos,
            unidadMedida.Onzas => UnidadMedidaEnum.Onzas,
            unidadMedida.Litros => UnidadMedidaEnum.Litros,
            _ => UnidadMedidaEnum.Unidad
        };
    }

    private static unidadMedida ToSoapUnidadMedida(UnidadMedidaEnum source) {
        return source switch {
            UnidadMedidaEnum.Unidad => unidadMedida.UND,
            UnidadMedidaEnum.Kilos => unidadMedida.Kilos,
            UnidadMedidaEnum.Onzas => unidadMedida.Onzas,
            UnidadMedidaEnum.Litros => unidadMedida.Litros,
            _ => unidadMedida.UND
        };
    }
}
