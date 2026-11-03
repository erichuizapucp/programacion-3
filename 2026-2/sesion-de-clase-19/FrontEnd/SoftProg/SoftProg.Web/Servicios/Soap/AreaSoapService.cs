using SoftProg.Web.ViewModels;
using SoftProgWS;
using System.ServiceModel;

namespace SoftProg.Web.Servicios.Soap {
    public class AreaSoapService : IAreaService {
        private readonly AreaWSClient areaWS;

        public AreaSoapService(IConfiguration configuration) {
            string baseUrl = configuration["SoapWS:BaseUrl"]!;
            areaWS = new AreaWSClient(AreaWSClient.EndpointConfiguration.AreaWSPort, $"{baseUrl}/AreaWS");
        }

        public List<AreaViewModel> FindAll() {
            try {
                return ToViewModels(areaWS.findAllAreas());
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public AreaViewModel? FindById(int id) {
            try {
                areaDTO? dto = areaWS.findAreaById(id);
                return dto == null ? null : ToViewModel(dto);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public void Insert(AreaViewModel area) {
            try {
                area.Id = areaWS.insertArea(ToSoap(area));
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public void Update(AreaViewModel area) {
            try {
                areaWS.updateArea(ToSoap(area));
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public void Delete(int id) {
            try {
                areaWS.deleteArea(id);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public List<AreaViewModel> FilterByName(string nombre) {
            try {
                return ToViewModels(areaWS.filterAreasByName(nombre));
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        private List<AreaViewModel> ToViewModels(areaDTO[]? dtos) {
            List<AreaViewModel> areas = [];
            foreach (areaDTO dto in dtos ?? []) {
                areas.Add(ToViewModel(dto));
            }
            return areas;
        }

        private AreaViewModel ToViewModel(areaDTO dto) {
            return new AreaViewModel {
                Id = dto.id,
                Nombre = dto.nombre,
                IsActivo = dto.activo
            };
        }

        private areaDTO ToSoap(AreaViewModel area) {
            return new areaDTO {
                id = area.Id,
                nombre = area.Nombre,
                activo = area.IsActivo
            };
        }
    }
}
