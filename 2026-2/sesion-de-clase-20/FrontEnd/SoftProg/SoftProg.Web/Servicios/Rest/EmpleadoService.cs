using Microsoft.AspNetCore.WebUtilities;
using SoftProg.Web.DTOs;
using SoftProg.Web.ViewModels;
using System.Globalization;
using System.Net;

namespace SoftProg.Web.Servicios.Rest {
    public class EmpleadoService : IEmpleadoService {
        private string BaseUrlEmpleados { get; set; }
        private string BaseUrlMiCuenta { get; set; }
        private const string FormatoFecha = "yyyy-MM-dd";

        public EmpleadoService(IConfiguration configuration) {
            BaseUrlEmpleados = $"{configuration["RestApi:BaseUrl"]!}/empleados";
            BaseUrlMiCuenta = $"{configuration["RestApi:BaseUrl"]!}/mi-cuenta";
        }

        public async Task<List<EmpleadoViewModel>> FindAll() {
            string url = BaseUrlEmpleados;
            List<EmpleadoDTO> empleados = await HttpUtils.DoGet<List<EmpleadoDTO>>(url);
            return ToViewModels(empleados);
        }

        public async Task<List<EmpleadoViewModel>> FilterByName(string nombre) {
            string url = QueryHelpers.AddQueryString(BaseUrlEmpleados, new Dictionary<string, string> {
                { "nombre", nombre }
            });
            List<EmpleadoDTO> empleados = await HttpUtils.DoGet<List<EmpleadoDTO>>(url);
            return ToViewModels(empleados);
        }

        public async Task<EmpleadoViewModel?> FindById(int id) {
            string url = $"{BaseUrlEmpleados}/{id}";
            EmpleadoDTO empleado = await HttpUtils.DoGet<EmpleadoDTO>(url);
            return ToViewModel(empleado);
        }

        public async Task<EmpleadoViewModel?> FindByCuenta(string userName) {
            string url = $"{BaseUrlMiCuenta}/{Uri.EscapeDataString(userName)}/empleado";
            try {
                EmpleadoDTO empleado = await HttpUtils.DoGet<EmpleadoDTO>(url);
                return ToViewModel(empleado);
            } catch (HttpRequestException ex) when (ex.StatusCode == HttpStatusCode.NotFound) {
                return null;
            }
        }

        public async Task Insert(EmpleadoViewModel modelo) {
            string url = BaseUrlEmpleados;
            EmpleadoDTO empleado = await HttpUtils.DoPost<EmpleadoDTO, EmpleadoDTO>(url, ToDto(modelo));
            modelo.Id = empleado.Id;
        }

        public async Task Update(EmpleadoViewModel modelo) {
            string url = $"{BaseUrlEmpleados}/{modelo.Id}";
            await HttpUtils.DoPut<EmpleadoDTO, EmpleadoDTO>(url, ToDto(modelo));
        }

        public async Task Delete(int id) {
            string url = $"{BaseUrlEmpleados}/{id}";
            await HttpUtils.DoDelete(url);
        }

        private List<EmpleadoViewModel> ToViewModels(List<EmpleadoDTO> dtos) {
            List<EmpleadoViewModel> empleados = [];
            foreach (EmpleadoDTO dto in dtos) {
                empleados.Add(ToViewModel(dto));
            }
            return empleados;
        }

        private EmpleadoViewModel ToViewModel(EmpleadoDTO dto) {
            return new EmpleadoViewModel {
                Id = dto.Id,
                IdCuentaUsuario = dto.CuentaUsuario?.Id ?? 0,
                Dni = dto.Dni,
                Nombre = dto.Nombre,
                ApellidoPaterno = dto.ApellidoPaterno,
                Genero = Enum.Parse<Genero>(dto.Genero),
                FechaNacimiento = DateTime.ParseExact(dto.FechaNacimiento, FormatoFecha, CultureInfo.InvariantCulture),
                IdArea = dto.Area?.Id ?? 0,
                NombreArea = dto.Area?.Nombre ?? "",
                Cargo = Enum.Parse<Cargo>(dto.Cargo),
                Sueldo = dto.Sueldo,
                IsActivo = dto.Activo
            };
        }

        private EmpleadoDTO ToDto(EmpleadoViewModel empleado) {
            return new EmpleadoDTO {
                Id = empleado.Id,
                CuentaUsuario = empleado.IdCuentaUsuario > 0 ? new() { Id = empleado.IdCuentaUsuario } : null,
                Dni = empleado.Dni,
                Nombre = empleado.Nombre,
                ApellidoPaterno = empleado.ApellidoPaterno,
                Genero = empleado.Genero.ToString(),
                FechaNacimiento = empleado.FechaNacimiento!.Value.ToString(FormatoFecha, CultureInfo.InvariantCulture),
                Area = new() { Id = empleado.IdArea },
                Cargo = empleado.Cargo.ToString(),
                Sueldo = empleado.Sueldo,
                Activo = empleado.IsActivo
            };
        }
    }
}
