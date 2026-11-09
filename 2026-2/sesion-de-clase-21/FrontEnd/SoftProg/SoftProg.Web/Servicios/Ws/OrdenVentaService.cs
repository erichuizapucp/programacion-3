using SoftProg.Web.ViewModels;
using SoftProgWS;
using System.ServiceModel;

namespace SoftProg.Web.Servicios.Ws {
    public class OrdenVentaService : IOrdenVentaService {
        private readonly OrdenVentaWSClient ordenVentaWS;

        public OrdenVentaService(IConfiguration configuration) {
            string baseUrl = configuration["SoapWS:BaseUrl"]!;
            ordenVentaWS = new OrdenVentaWSClient(OrdenVentaWSClient.EndpointConfiguration.OrdenVentaWSPort, $"{baseUrl}/OrdenVentaWS");
        }

        public async Task<List<OrdenVentaViewModel>> FindAll() {
            try {
                findAllOrdenesVentaResponse respuesta = await ordenVentaWS.findAllOrdenesVentaAsync();
                return ToViewModels(respuesta.@return);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task<OrdenVentaViewModel?> FindById(int id) {
            try {
                findOrdenVentaByIdResponse respuesta = await ordenVentaWS.findOrdenVentaByIdAsync(id);
                ordenVentaDTO? dto = respuesta.@return;
                return dto == null ? null : ToViewModel(dto);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task Insert(OrdenVentaViewModel ordenVenta) {
            try {
                insertOrdenVentaResponse respuesta = await ordenVentaWS.insertOrdenVentaAsync(ToDto(ordenVenta));
                ordenVenta.Id = respuesta.@return;
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task Update(OrdenVentaViewModel ordenVenta) {
            try {
                await ordenVentaWS.updateOrdenVentaAsync(ToDto(ordenVenta));
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task Delete(int id) {
            try {
                await ordenVentaWS.deleteOrdenVentaAsync(id);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task<List<OrdenVentaViewModel>> FindByCuenta(string userName) {
            try {
                findOrdenesVentaByCuentaResponse respuesta = await ordenVentaWS.findOrdenesVentaByCuentaAsync(userName);
                return ToViewModels(respuesta.@return);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        private List<OrdenVentaViewModel> ToViewModels(ordenVentaDTO[]? dtos) {
            List<OrdenVentaViewModel> ordenes = [];
            foreach (ordenVentaDTO dto in dtos ?? []) {
                ordenes.Add(ToViewModel(dto));
            }
            return ordenes;
        }

        private OrdenVentaViewModel ToViewModel(ordenVentaDTO dto) {
            OrdenVentaViewModel ordenVenta = new() {
                Id = dto.id,
                IdCliente = dto.cliente?.id ?? 0,
                NombreCliente = dto.cliente != null ? $"{dto.cliente.nombre} {dto.cliente.apellidoPaterno}" : "",
                IdEmpleado = dto.empleado?.id ?? 0,
                NombreEmpleado = dto.empleado != null ? $"{dto.empleado.nombre} {dto.empleado.apellidoPaterno}" : null,
                IsActivo = dto.activo
            };

            foreach (lineaOrdenVentaDTO linea in dto.lineas ?? []) {
                ordenVenta.Lineas.Add(new LineaOrdenVentaViewModel {
                    Id = linea.id,
                    IdProducto = linea.producto.id,
                    NombreProducto = linea.producto.nombre,
                    Precio = linea.producto.precio,
                    Cantidad = linea.cantidad,
                    SubTotal = linea.subTotal
                });
            }
            return ordenVenta;
        }

        private ordenVentaDTO ToDto(OrdenVentaViewModel ordenVenta) {
            List<lineaOrdenVentaDTO> lineas = [];
            foreach (LineaOrdenVentaViewModel linea in ordenVenta.Lineas) {
                lineas.Add(new lineaOrdenVentaDTO {
                    id = linea.Id,
                    producto = new() { id = linea.IdProducto },
                    cantidad = linea.Cantidad,
                    subTotal = linea.SubTotal
                });
            }

            return new ordenVentaDTO {
                id = ordenVenta.Id,
                cliente = ordenVenta.IdCliente > 0 ? new() { id = ordenVenta.IdCliente } : null,
                empleado = ordenVenta.IdEmpleado > 0 ? new() { id = ordenVenta.IdEmpleado } : null,
                total = ordenVenta.Total,
                lineas = lineas.ToArray(),
                activo = ordenVenta.IsActivo
            };
        }
    }
}
