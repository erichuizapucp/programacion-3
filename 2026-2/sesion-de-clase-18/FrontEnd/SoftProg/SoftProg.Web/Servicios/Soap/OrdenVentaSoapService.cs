using SoftProg.Web.ViewModels;
using SoftProgWS;
using System.ServiceModel;

namespace SoftProg.Web.Servicios.Soap {
    public class OrdenVentaSoapService : IOrdenVentaService {
        private readonly OrdenVentaWSClient ordenVentaWS;

        public OrdenVentaSoapService(IConfiguration configuration) {
            string baseUrl = configuration["SoapWS:BaseUrl"]!;
            ordenVentaWS = new OrdenVentaWSClient(OrdenVentaWSClient.EndpointConfiguration.OrdenVentaWSPort, $"{baseUrl}/OrdenVentaWS");
        }

        public List<OrdenVentaViewModel> FindAll() {
            try {
                return ToViewModels(ordenVentaWS.findAllOrdenesVenta());
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public OrdenVentaViewModel? FindById(int id) {
            try {
                ordenVentaDTO? dto = ordenVentaWS.findOrdenVentaById(id);
                return dto == null ? null : ToViewModel(dto);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public void Insert(OrdenVentaViewModel ordenVenta) {
            try {
                ordenVenta.Id = ordenVentaWS.insertOrdenVenta(ToSoap(ordenVenta));
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public void Update(OrdenVentaViewModel ordenVenta) {
            try {
                ordenVentaWS.updateOrdenVenta(ToSoap(ordenVenta));
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public void Delete(int id) {
            try {
                ordenVentaWS.deleteOrdenVenta(id);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public List<OrdenVentaViewModel> FindByCuenta(string userName) {
            try {
                return ToViewModels(ordenVentaWS.findOrdenesVentaByCuenta(userName));
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
                IdCliente = dto.idCliente,
                NombreCliente = dto.nombreCliente,
                IdEmpleado = dto.idEmpleado,
                NombreEmpleado = dto.nombreEmpleado,
                IsActivo = dto.activo
            };

            foreach (lineaOrdenVentaDTO linea in dto.lineas ?? []) {
                ordenVenta.Lineas.Add(new LineaOrdenVentaViewModel {
                    Id = linea.id,
                    IdProducto = linea.idProducto,
                    NombreProducto = linea.nombreProducto,
                    Precio = linea.precio,
                    Cantidad = linea.cantidad,
                    SubTotal = linea.subTotal
                });
            }
            return ordenVenta;
        }

        private ordenVentaDTO ToSoap(OrdenVentaViewModel ordenVenta) {
            List<lineaOrdenVentaDTO> lineas = [];
            foreach (LineaOrdenVentaViewModel linea in ordenVenta.Lineas) {
                lineas.Add(new lineaOrdenVentaDTO {
                    id = linea.Id,
                    idProducto = linea.IdProducto,
                    cantidad = linea.Cantidad,
                    subTotal = linea.SubTotal
                });
            }

            return new ordenVentaDTO {
                id = ordenVenta.Id,
                idCliente = ordenVenta.IdCliente,
                idEmpleado = ordenVenta.IdEmpleado,
                total = ordenVenta.Total,
                lineas = lineas.ToArray(),
                activo = ordenVenta.IsActivo
            };
        }
    }
}
