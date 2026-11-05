using SoftProg.Web.DTOs;
using SoftProg.Web.ViewModels;

namespace SoftProg.Web.Servicios.Rest {
    public class OrdenVentaService : IOrdenVentaService {
        private string BaseUrlOrdenes { get; set; }
        private string BaseUrlMiCuenta { get; set; }

        public OrdenVentaService(IConfiguration configuration) {
            BaseUrlOrdenes = $"{configuration["RestApi:BaseUrl"]!}/ordenes-venta";
            BaseUrlMiCuenta = $"{configuration["RestApi:BaseUrl"]!}/mi-cuenta";
        }

        public async Task<List<OrdenVentaViewModel>> FindAll() {
            string url = BaseUrlOrdenes;
            List<OrdenVentaDTO> ordenes = await HttpUtils.DoGet<List<OrdenVentaDTO>>(url);
            return ToViewModels(ordenes);
        }

        public async Task<OrdenVentaViewModel?> FindById(int id) {
            string url = $"{BaseUrlOrdenes}/{id}";
            OrdenVentaDTO orden = await HttpUtils.DoGet<OrdenVentaDTO>(url);
            return ToViewModel(orden);
        }

        public async Task<List<OrdenVentaViewModel>> FindByCuenta(string userName) {
            string url = $"{BaseUrlMiCuenta}/{Uri.EscapeDataString(userName)}/ordenes";
            List<OrdenVentaDTO> ordenes = await HttpUtils.DoGet<List<OrdenVentaDTO>>(url);
            return ToViewModels(ordenes);
        }

        public async Task Insert(OrdenVentaViewModel modelo) {
            string url = BaseUrlOrdenes;
            OrdenVentaDTO orden = await HttpUtils.DoPost<OrdenVentaDTO, OrdenVentaDTO>(url, ToDto(modelo));
            modelo.Id = orden.Id;
        }

        public async Task Update(OrdenVentaViewModel modelo) {
            string url = $"{BaseUrlOrdenes}/{modelo.Id}";
            await HttpUtils.DoPut<OrdenVentaDTO, OrdenVentaDTO>(url, ToDto(modelo));
        }

        public async Task Delete(int id) {
            string url = $"{BaseUrlOrdenes}/{id}";
            await HttpUtils.DoDelete(url);
        }

        private List<OrdenVentaViewModel> ToViewModels(List<OrdenVentaDTO> dtos) {
            List<OrdenVentaViewModel> ordenes = [];
            foreach (OrdenVentaDTO dto in dtos) {
                ordenes.Add(ToViewModel(dto));
            }
            return ordenes;
        }

        private OrdenVentaViewModel ToViewModel(OrdenVentaDTO dto) {
            OrdenVentaViewModel ordenVenta = new() {
                Id = dto.Id,
                IdCliente = dto.Cliente?.Id ?? 0,
                NombreCliente = dto.Cliente != null ? $"{dto.Cliente.Nombre} {dto.Cliente.ApellidoPaterno}" : "",
                IdEmpleado = dto.Empleado?.Id ?? 0,
                NombreEmpleado = dto.Empleado != null ? $"{dto.Empleado.Nombre} {dto.Empleado.ApellidoPaterno}" : null,
                IsActivo = dto.Activo
            };

            foreach (LineaOrdenVentaDTO linea in dto.Lineas ?? []) {
                ordenVenta.Lineas.Add(new LineaOrdenVentaViewModel {
                    Id = linea.Id,
                    IdProducto = linea.Producto.Id,
                    NombreProducto = linea.Producto.Nombre,
                    Precio = linea.Producto.Precio,
                    Cantidad = linea.Cantidad,
                    SubTotal = linea.SubTotal
                });
            }
            return ordenVenta;
        }

        private OrdenVentaDTO ToDto(OrdenVentaViewModel ordenVenta) {
            List<LineaOrdenVentaDTO> lineas = [];
            foreach (LineaOrdenVentaViewModel linea in ordenVenta.Lineas) {
                lineas.Add(new LineaOrdenVentaDTO {
                    Id = linea.Id,
                    Producto = new() { Id = linea.IdProducto },
                    Cantidad = linea.Cantidad,
                    SubTotal = linea.SubTotal
                });
            }

            return new OrdenVentaDTO {
                Id = ordenVenta.Id,
                Cliente = ordenVenta.IdCliente > 0 ? new() { Id = ordenVenta.IdCliente } : null,
                Empleado = ordenVenta.IdEmpleado > 0 ? new() { Id = ordenVenta.IdEmpleado } : null,
                Total = ordenVenta.Total,
                Lineas = lineas,
                Activo = ordenVenta.IsActivo
            };
        }
    }
}
