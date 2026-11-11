using Microsoft.AspNetCore.WebUtilities;
using SoftProg.Web.DTOs;
using SoftProg.Web.ViewModels;
using System.Reflection;

namespace SoftProg.Web.Servicios.Rest {
    public class ProductoService : IProductoService {
        private string BaseUrl { get; set; }

        public ProductoService(IConfiguration configuracion) {
            BaseUrl = $"{configuracion["RestApi:BaseUrl"]!}/productos" ;
        }

        public async Task<List<ProductoViewModel>> FindAll() {
            string url = BaseUrl;
            List<ProductoDTO> productos = 
                await HttpUtils.DoGet<List<ProductoDTO>>(url);

            return ToViewModels(productos);
        }

        public async Task<List<ProductoViewModel>> FilterByName(string nombre) {
            string url = QueryHelpers.AddQueryString(BaseUrl,
                new Dictionary<string, string> {
                    { "nombre", nombre }
                });

            List<ProductoDTO> productos =
                await HttpUtils.DoGet<List<ProductoDTO>>(url);

            return ToViewModels(productos);
        }

        public async Task<ProductoViewModel?> FindById(int id) {
            string url = $"{BaseUrl}/{id}";
            ProductoDTO producto =
                await HttpUtils.DoGet<ProductoDTO>(url);

            return ToViewModel(producto);
        }

        public async Task Insert(ProductoViewModel modelo) {
            string url = BaseUrl;
            await HttpUtils.DoPost<ProductoDTO, ProductoDTO>(url, ToDto(modelo));
        }

        public async Task Update(ProductoViewModel modelo) {
            string url = $"{BaseUrl}/{modelo.Id}";
            await HttpUtils.DoPut<ProductoDTO, ProductoDTO>(url, ToDto(modelo));
        }

        public async Task Delete(int id) {
            string url = $"{BaseUrl}/{id}";
            await HttpUtils.DoDelete(url);
        }

        private List<ProductoViewModel> ToViewModels(List<ProductoDTO> dtos) {
            List<ProductoViewModel> productos = [];
            foreach (ProductoDTO dto in dtos) {
                productos.Add(ToViewModel(dto));
            }
            return productos;
        }

        private ProductoViewModel ToViewModel(ProductoDTO dto) {
            return new ProductoViewModel {
                Id = dto.Id,
                Nombre = dto.Nombre,
                UnidadMedida = Enum.Parse<UnidadMedida>(dto.UnidadMedida),
                Precio = dto.Precio,
                IsActivo = dto.Activo
            };
        }

        private ProductoDTO ToDto(ProductoViewModel producto) {
            return new ProductoDTO {
                Id = producto.Id,
                Nombre = producto.Nombre,
                UnidadMedida = producto.UnidadMedida.ToString(),
                Precio = producto.Precio,
                Activo = producto.IsActivo
            };
        }
    }
}
