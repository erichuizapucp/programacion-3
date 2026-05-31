using SoftProgWS.Productos;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;
using SoftProgWeb.ViewModels.Mappers;

namespace SoftProgWeb.Servicios.Almacen;

public class ProductosServiceImpl : SoapServiceBase, IProductosService {
    private const string EndpointSetting = "SoapEndpoints:Productos";

    public ProductosServiceImpl(IConfiguration configuration)
        : base(configuration) {
    }

    public List<ProductoViewModel> Listar() {
        var clienteWs = CrearClienteWs();
        var productos = clienteWs.listarProductos() ?? [];

        var respuesta = new List<ProductoViewModel>();
        foreach (var item in productos) {
            var viewModel = ProductoViewModelMapper.ToViewModel(ToDomain(item));
            respuesta.Add(viewModel);
        }

        return respuesta;
    }

    public ProductoViewModel? Obtener(int id) {
        var clienteWs = CrearClienteWs();
        var producto = clienteWs.obtenerProducto(id);
        return producto is null ? null : ProductoViewModelMapper.ToViewModel(ToDomain(producto));
    }

    public void Guardar(ProductoViewModel producto, Estado estado) {
        var clienteWs = CrearClienteWs();
        var domain = ProductoViewModelMapper.ToDomain(producto);
        clienteWs.guardarProducto(ToSoap(domain), ParseEstado<estado>(estado));
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

    private static Producto ToDomain(producto source) {
        return new Producto {
            Id = source.id,
            Activo = source.activo,
            Nombre = source.nombre ?? string.Empty,
            Precio = source.precio,
            UnidadMedida = ParseEnum(source.unidadMedida, UnidadMedida.UND)
        };
    }

    private static producto ToSoap(Producto source) {
        return new producto {
            id = source.Id,
            activo = source.Activo,
            nombre = source.Nombre,
            precio = source.Precio,
            unidadMedida = ParseEnum(source.UnidadMedida, unidadMedida.UND),
            unidadMedidaSpecified = true
        };
    }
}
