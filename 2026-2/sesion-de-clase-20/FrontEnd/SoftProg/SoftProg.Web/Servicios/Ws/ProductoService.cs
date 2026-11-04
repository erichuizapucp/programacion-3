using SoftProg.Web.ViewModels;
using SoftProgWS;
using System.ServiceModel;

namespace SoftProg.Web.Servicios.Ws {
    public class ProductoService : IProductoService {
        private readonly ProductoWSClient productoWS;

        public ProductoService(IConfiguration configuration) {
            string baseUrl = configuration["SoapWS:BaseUrl"]!;
            productoWS = new ProductoWSClient(ProductoWSClient.EndpointConfiguration.ProductoWSPort, $"{baseUrl}/ProductoWS");
        }

        public async Task<List<ProductoViewModel>> FindAll() {
            try {
                findAllProductosResponse respuesta = await productoWS.findAllProductosAsync();
                return ToViewModels(respuesta.@return);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task<ProductoViewModel?> FindById(int id) {
            try {
                findProductoByIdResponse respuesta = await productoWS.findProductoByIdAsync(id);
                productoDTO? dto = respuesta.@return;
                return dto == null ? null : ToViewModel(dto);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task Insert(ProductoViewModel producto) {
            try {
                insertProductoResponse respuesta = await productoWS.insertProductoAsync(ToDto(producto));
                producto.Id = respuesta.@return;
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task Update(ProductoViewModel producto) {
            try {
                await productoWS.updateProductoAsync(ToDto(producto));
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task Delete(int id) {
            try {
                await productoWS.deleteProductoAsync(id);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task<List<ProductoViewModel>> FilterByName(string nombre) {
            try {
                filterProductosByNameResponse respuesta = await productoWS.filterProductosByNameAsync(nombre);
                return ToViewModels(respuesta.@return);
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
}
