using SoftProg.Web.DTOs;
using SoftProg.Web.ViewModels;
using System.Net;

namespace SoftProg.Web.Servicios.Rest {
    public class CuentaUsuarioService : ICuentaUsuarioService {
        private string BaseUrlCuentas { get; set; }
        private string BaseUrlMiCuenta { get; set; }
        private string BaseUrlLogin { get; set; }

        public CuentaUsuarioService(IConfiguration configuration) {
            BaseUrlCuentas = $"{configuration["RestApi:BaseUrl"]!}/cuentas-usuario";
            BaseUrlMiCuenta = $"{configuration["RestApi:BaseUrl"]!}/mi-cuenta";
            BaseUrlLogin = $"{configuration["RestApi:BaseUrl"]!}/login";
        }

        public async Task<List<CuentaUsuarioViewModel>> FindAll() {
            string url = BaseUrlCuentas;
            List<CuentaUsuarioDTO> cuentas = await HttpUtils.DoGet<List<CuentaUsuarioDTO>>(url);
            return ToViewModels(cuentas);
        }

        public async Task<CuentaUsuarioViewModel?> FindById(int id) {
            string url = $"{BaseUrlCuentas}/{id}";
            CuentaUsuarioDTO cuenta = await HttpUtils.DoGet<CuentaUsuarioDTO>(url);
            return ToViewModel(cuenta);
        }

        public async Task<CuentaUsuarioViewModel?> FindByUserName(string userName) {
            string url = $"{BaseUrlMiCuenta}/{Uri.EscapeDataString(userName)}";
            try {
                CuentaUsuarioDTO cuenta = await HttpUtils.DoGet<CuentaUsuarioDTO>(url);
                return ToViewModel(cuenta);
            } catch (HttpRequestException ex) when (ex.StatusCode == HttpStatusCode.NotFound) {
                return null;
            }
        }

        public async Task Insert(CuentaUsuarioViewModel modelo) {
            string url = BaseUrlCuentas;
            CuentaUsuarioDTO cuenta = await HttpUtils.DoPost<CuentaUsuarioDTO, CuentaUsuarioDTO>(url, ToDto(modelo));
            modelo.Id = cuenta.Id;
        }

        public async Task Update(CuentaUsuarioViewModel modelo) {
            string url = $"{BaseUrlCuentas}/{modelo.Id}";
            await HttpUtils.DoPut<CuentaUsuarioDTO, CuentaUsuarioDTO>(url, ToDto(modelo));
        }

        public async Task Delete(int id) {
            string url = $"{BaseUrlCuentas}/{id}";
            await HttpUtils.DoDelete(url);
        }

        public async Task<bool> Autenticar(string userName, string password) {
            string url = BaseUrlLogin;
            CredencialesDTO credenciales = new() {
                UserName = userName,
                Password = password
            };
            try {
                await HttpUtils.DoPost<CredencialesDTO, CuentaUsuarioDTO>(url, credenciales);
                return true;
            } catch (HttpRequestException ex) when (ex.StatusCode == HttpStatusCode.Unauthorized) {
                return false;
            }
        }

        public async Task CambiarPassword(int id, string passwordActual, string passwordNueva) {
            CuentaUsuarioViewModel cuenta = (await FindById(id))!;
            string url = $"{BaseUrlMiCuenta}/{Uri.EscapeDataString(cuenta.UserName)}/password";
            CambiarPasswordDTO passwords = new() {
                PasswordActual = passwordActual,
                PasswordNueva = passwordNueva
            };
            try {
                await HttpUtils.DoPut(url, passwords);
            } catch (HttpRequestException ex) when (ex.StatusCode == HttpStatusCode.BadRequest) {
                throw new ServicioException("No se pudo cambiar la contraseña. Verifique la contraseña actual.", ex);
            }
        }

        private List<CuentaUsuarioViewModel> ToViewModels(List<CuentaUsuarioDTO> dtos) {
            List<CuentaUsuarioViewModel> cuentas = [];
            foreach (CuentaUsuarioDTO dto in dtos) {
                cuentas.Add(ToViewModel(dto));
            }
            return cuentas;
        }

        private CuentaUsuarioViewModel ToViewModel(CuentaUsuarioDTO dto) {
            return new CuentaUsuarioViewModel {
                Id = dto.Id,
                UserName = dto.UserName,
                Password = dto.Password ?? "",
                IsActivo = dto.Activo
            };
        }

        private CuentaUsuarioDTO ToDto(CuentaUsuarioViewModel cuenta) {
            return new CuentaUsuarioDTO {
                Id = cuenta.Id,
                UserName = cuenta.UserName,
                Password = cuenta.Password,
                Activo = cuenta.IsActivo
            };
        }
    }
}
