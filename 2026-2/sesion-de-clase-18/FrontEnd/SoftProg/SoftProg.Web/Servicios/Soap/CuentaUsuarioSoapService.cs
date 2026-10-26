using SoftProg.Web.ViewModels;
using SoftProgWS;
using System.ServiceModel;

namespace SoftProg.Web.Servicios.Soap {
    public class CuentaUsuarioSoapService : ICuentaUsuarioService {
        private readonly CuentaUsuarioWSClient cuentaUsuarioWS;

        public CuentaUsuarioSoapService(IConfiguration configuration) {
            string baseUrl = configuration["SoapWS:BaseUrl"]!;
            cuentaUsuarioWS = new CuentaUsuarioWSClient(CuentaUsuarioWSClient.EndpointConfiguration.CuentaUsuarioWSPort, $"{baseUrl}/CuentaUsuarioWS");
        }

        public List<CuentaUsuarioViewModel> FindAll() {
            try {
                List<CuentaUsuarioViewModel> cuentas = [];
                foreach (cuentaUsuarioDTO dto in cuentaUsuarioWS.findAllCuentasUsuario() ?? []) {
                    cuentas.Add(ToViewModel(dto));
                }
                return cuentas;
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public CuentaUsuarioViewModel? FindById(int id) {
            try {
                cuentaUsuarioDTO? dto = cuentaUsuarioWS.findCuentaUsuarioById(id);
                return dto == null ? null : ToViewModel(dto);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public void Insert(CuentaUsuarioViewModel cuentaUsuario) {
            try {
                cuentaUsuario.Id = cuentaUsuarioWS.insertCuentaUsuario(ToSoap(cuentaUsuario));
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public void Update(CuentaUsuarioViewModel cuentaUsuario) {
            try {
                cuentaUsuarioWS.updateCuentaUsuario(ToSoap(cuentaUsuario));
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public void Delete(int id) {
            try {
                cuentaUsuarioWS.deleteCuentaUsuario(id);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public CuentaUsuarioViewModel? FindByUserName(string userName) {
            try {
                cuentaUsuarioDTO? dto = cuentaUsuarioWS.findByUserName(userName);
                return dto == null ? null : ToViewModel(dto);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public bool Autenticar(string userName, string password) {
            try {
                return cuentaUsuarioWS.autenticar(userName, password);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public void CambiarPassword(int id, string passwordActual, string passwordNueva) {
            try {
                cuentaUsuarioWS.cambiarPassword(id, passwordActual, passwordNueva);
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

        private cuentaUsuarioDTO ToSoap(CuentaUsuarioViewModel cuentaUsuario) {
            return new cuentaUsuarioDTO {
                id = cuentaUsuario.Id,
                userName = cuentaUsuario.UserName,
                password = cuentaUsuario.Password,
                activo = cuentaUsuario.IsActivo
            };
        }
    }
}
