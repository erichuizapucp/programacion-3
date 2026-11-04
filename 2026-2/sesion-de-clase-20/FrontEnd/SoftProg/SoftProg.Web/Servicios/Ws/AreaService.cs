using SoftProg.Web.ViewModels;
using SoftProgWS;
using System.ServiceModel;

namespace SoftProg.Web.Servicios.Ws {
    public class AreaService : IAreaService {
        private readonly AreaWSClient areaWS;

        public AreaService(IConfiguration configuration) {
            string baseUrl = configuration["SoapWS:BaseUrl"]!;
            areaWS = new AreaWSClient(AreaWSClient.EndpointConfiguration.AreaWSPort, $"{baseUrl}/AreaWS");
        }

        public async Task<List<AreaViewModel>> FindAll() {
            try {
                findAllAreasResponse respuesta = await areaWS.findAllAreasAsync();
                return ToViewModels(respuesta.@return);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task<AreaViewModel?> FindById(int id) {
            try {
                findAreaByIdResponse respuesta = await areaWS.findAreaByIdAsync(id);
                areaDTO? dto = respuesta.@return;
                return dto == null ? null : ToViewModel(dto);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task Insert(AreaViewModel area) {
            try {
                insertAreaResponse respuesta = await areaWS.insertAreaAsync(ToDto(area));
                area.Id = respuesta.@return;
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task Update(AreaViewModel area) {
            try {
                await areaWS.updateAreaAsync(ToDto(area));
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task Delete(int id) {
            try {
                await areaWS.deleteAreaAsync(id);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task<List<AreaViewModel>> FilterByName(string nombre) {
            try {
                filterAreasByNameResponse respuesta = await areaWS.filterAreasByNameAsync(nombre);
                return ToViewModels(respuesta.@return);
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

        private areaDTO ToDto(AreaViewModel area) {
            return new areaDTO {
                id = area.Id,
                nombre = area.Nombre,
                activo = area.IsActivo
            };
        }
    }
}
