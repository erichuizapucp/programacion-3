using SoftProg.Web.ViewModels;
using SoftProgWS;
using System.ServiceModel;

namespace SoftProg.Web.Servicios.Ws {
    public class CuentaUsuarioService : ICuentaUsuarioService {
        private readonly CuentaUsuarioWSClient cuentaUsuarioWS;

        public CuentaUsuarioService(IConfiguration configuration) {
            string baseUrl = configuration["SoapWS:BaseUrl"]!;
            cuentaUsuarioWS = new CuentaUsuarioWSClient(CuentaUsuarioWSClient.EndpointConfiguration.CuentaUsuarioWSPort, $"{baseUrl}/CuentaUsuarioWS");
        }

        public async Task<List<CuentaUsuarioViewModel>> FindAll() {
            try {
                findAllCuentasUsuarioResponse respuesta = await cuentaUsuarioWS.findAllCuentasUsuarioAsync();
                List<CuentaUsuarioViewModel> cuentas = [];
                foreach (cuentaUsuarioDTO dto in respuesta.@return ?? []) {
                    cuentas.Add(ToViewModel(dto));
                }
                return cuentas;
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task<CuentaUsuarioViewModel?> FindById(int id) {
            try {
                findCuentaUsuarioByIdResponse respuesta = await cuentaUsuarioWS.findCuentaUsuarioByIdAsync(id);
                cuentaUsuarioDTO? dto = respuesta.@return;
                return dto == null ? null : ToViewModel(dto);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task Insert(CuentaUsuarioViewModel cuentaUsuario) {
            try {
                insertCuentaUsuarioResponse respuesta = await cuentaUsuarioWS.insertCuentaUsuarioAsync(ToDto(cuentaUsuario));
                cuentaUsuario.Id = respuesta.@return;
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task Update(CuentaUsuarioViewModel cuentaUsuario) {
            try {
                await cuentaUsuarioWS.updateCuentaUsuarioAsync(ToDto(cuentaUsuario));
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task Delete(int id) {
            try {
                await cuentaUsuarioWS.deleteCuentaUsuarioAsync(id);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task<CuentaUsuarioViewModel?> FindByUserName(string userName) {
            try {
                findByUserNameResponse respuesta = await cuentaUsuarioWS.findByUserNameAsync(userName);
                cuentaUsuarioDTO? dto = respuesta.@return;
                return dto == null ? null : ToViewModel(dto);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task<bool> Autenticar(string userName, string password) {
            try {
                autenticarResponse respuesta = await cuentaUsuarioWS.autenticarAsync(userName, password);
                return respuesta.@return;
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task CambiarPassword(int id, string passwordActual, string passwordNueva) {
            try {
                await cuentaUsuarioWS.cambiarPasswordAsync(id, passwordActual, passwordNueva);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        private CuentaUsuarioViewModel ToViewModel(cuentaUsuarioDTO dto) {
            return new CuentaUsuarioViewModel {
                Id = dto.id,
                UserName = dto.userName,
                Password = dto.password,
                IsActivo = dto.activo
            };
        }

        private cuentaUsuarioDTO ToDto(CuentaUsuarioViewModel cuentaUsuario) {
            return new cuentaUsuarioDTO {
                id = cuentaUsuario.Id,
                userName = cuentaUsuario.UserName,
                password = cuentaUsuario.Password,
                activo = cuentaUsuario.IsActivo
            };
        }
    }
}
