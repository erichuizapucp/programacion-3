using SoftProg.Web.ViewModels;
using SoftProgWS;
using System.ServiceModel;

namespace SoftProg.Web.Servicios.Soap;
    public class ProductoService : IProductoService {
    private readonly ProductoWSClient productoWS;

    public ProductoService(IConfiguration configuration) {
        string baseUrl = configuration["SoapWS:BaseUrl"]!;
        productoWS = new ProductoWSClient(ProductoWSClient.EndpointConfiguration.ProductoWSPort, $"{baseUrl}/ProductoWS");
    }

    public async Task<List<ProductoViewModel>> FindAll() {
        try {
            var dtos = await productoWS.findAllProductosAsync();
            return ToViewModels(dtos.@return);
        } catch (FaultException ex) {
            throw new ServicioException(ex.Message, ex);
        }
    }

    public async Task<ProductoViewModel?> FindById(int id) {
        try {
            var dto = await productoWS.findProductoByIdAsync(id);
            return dto == null ? null : ToViewModel(dto.@return);
        } catch (FaultException ex) {
            throw new ServicioException(ex.Message, ex);
        }
    }

    public async Task Insert(ProductoViewModel producto) {
        try {
            productoDTO dto = ToDto(producto);
            var resp = await productoWS.insertProductoAsync(dto);
            producto.Id = resp.@return;
        }
        catch (FaultException ex) {
            throw new ServicioException(ex.Message, ex);
        }
    }

    public async Task Update(ProductoViewModel producto) {
        try {
            productoWS.updateProducto(ToDto(producto));
        } catch (FaultException ex) {
            throw new ServicioException(ex.Message, ex);
        }
    }

    public async Task Delete(int id) {
        try {
            productoWS.deleteProducto(id);
        } catch (FaultException ex) {
            throw new ServicioException(ex.Message, ex);
        }
    }

    public async Task<List<ProductoViewModel>> FilterByName(string nombre) {
        try {
            return ToViewModels(productoWS.filterProductosByName(nombre));
        } catch (FaultException ex) {
            throw new ServicioException(ex.Message, ex);
        }
    }

    private List<ProductoViewModel> ToViewModels(productoDTO[]? dtos) {
        List<ProductoViewModel> productos = [];
        foreach (productoDTO dto in dtos ?? []) {
            productos.Add(ToViewModel(dto));
        }
        return productos;
    }

    private ProductoViewModel ToViewModel(productoDTO dto) {
        return new ProductoViewModel {
            Id = dto.id,
            Nombre = dto.nombre,
            UnidadMedida = Enum.Parse<UnidadMedida>(dto.unidadMedida),
            Precio = dto.precio,
            IsActivo = dto.activo
        };
    }

    private productoDTO ToDto(ProductoViewModel producto) {
        return new productoDTO {
            id = producto.Id,
            nombre = producto.Nombre,
            unidadMedida = producto.UnidadMedida.ToString(),
            precio = producto.Precio,
            activo = producto.IsActivo
        };
    }
}
