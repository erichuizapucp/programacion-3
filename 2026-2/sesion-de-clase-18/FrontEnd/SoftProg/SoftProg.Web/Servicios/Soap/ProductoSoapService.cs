using SoftProg.Web.ViewModels;
using SoftProgWS;
using System.ServiceModel;

namespace SoftProg.Web.Servicios.Soap {
    public class ProductoSoapService : IProductoService {
        private readonly ProductoWSClient productoWS;

        public ProductoSoapService(IConfiguration configuration) {
            string baseUrl = configuration["SoapWS:BaseUrl"]!;
            productoWS = new ProductoWSClient(ProductoWSClient.EndpointConfiguration.ProductoWSPort, $"{baseUrl}/ProductoWS");
        }

        public List<ProductoViewModel> FindAll() {
            try {
                return ToViewModels(productoWS.findAllProductos());
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public ProductoViewModel? FindById(int id) {
            try {
                productoDTO? dto = productoWS.findProductoById(id);
                return dto == null ? null : ToViewModel(dto);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public void Insert(ProductoViewModel producto) {
            try {
                producto.Id = productoWS.insertProducto(ToSoap(producto));
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public void Update(ProductoViewModel producto) {
            try {
                productoWS.updateProducto(ToSoap(producto));
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public void Delete(int id) {
            try {
                productoWS.deleteProducto(id);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public List<ProductoViewModel> FilterByName(string nombre) {
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

        private productoDTO ToSoap(ProductoViewModel producto) {
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
