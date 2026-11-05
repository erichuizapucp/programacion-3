using Microsoft.AspNetCore.WebUtilities;
using SoftProg.Web.DTOs;
using SoftProg.Web.ViewModels;

namespace SoftProg.Web.Servicios.Rest {
    public class AreaService : IAreaService {
        private string BaseUrl { get; set; }

        public AreaService(IConfiguration configuracion) {
            BaseUrl = $"{configuracion["RestApi:BaseUrl"]!}/areas";
        }

        public async Task<List<AreaViewModel>> FindAll() {
            string url = BaseUrl;
            List<AreaDTO> areas = await HttpUtils.DoGet<List<AreaDTO>>(url);
            return ToViewModels(areas);
        }

        public async Task<List<AreaViewModel>> FilterByName(string nombre) {
            string url = QueryHelpers.AddQueryString(BaseUrl, new Dictionary<string, string> {
                { "nombre", nombre }
            });
            List<AreaDTO> areas = await HttpUtils.DoGet<List<AreaDTO>>(url);
            return ToViewModels(areas);
        }

        public async Task<AreaViewModel?> FindById(int id) {
            string url = $"{BaseUrl}/{id}";
            AreaDTO area = await HttpUtils.DoGet<AreaDTO>(url);
            return ToViewModel(area);
        }

        public async Task Insert(AreaViewModel modelo) {
            string url = $"{BaseUrl}";
            await HttpUtils.DoPost<AreaDTO, AreaDTO>(url, ToDto(modelo));
        }

        public async Task Update(AreaViewModel modelo) {
            string url = $"{BaseUrl}/{modelo.Id}";
            await HttpUtils.DoPut<AreaDTO, AreaDTO>(url, ToDto(modelo));
        }

        public async Task Delete(int id) {
            string url = $"{BaseUrl}/{id}";
            await HttpUtils.DoDelete(url);
        }

        private List<AreaViewModel> ToViewModels(List<AreaDTO> dtos) {
            List<AreaViewModel> areas = [];
            foreach (AreaDTO dto in dtos) {
                areas.Add(ToViewModel(dto));
            }
            return areas;
        }

        private AreaViewModel ToViewModel(AreaDTO dto) {
            return new AreaViewModel {
                Id = dto.Id,
                Nombre = dto.Nombre,
                IsActivo = dto.Activo
            };
        }

        private AreaDTO ToDto(AreaViewModel area) {
            return new AreaDTO {
                Id = area.Id,
                Nombre = area.Nombre,
                Activo = area.IsActivo
            };
        }
    }
}
