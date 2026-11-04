using SoftProg.Web.ViewModels;
using SoftProgWS;
using System.Globalization;
using System.ServiceModel;

namespace SoftProg.Web.Servicios.Ws {
    public class EmpleadoService : IEmpleadoService {
        private const string FormatoFecha = "yyyy-MM-dd";

        private readonly EmpleadoWSClient empleadoWS;

        public EmpleadoService(IConfiguration configuration) {
            string baseUrl = configuration["SoapWS:BaseUrl"]!;
            empleadoWS = new EmpleadoWSClient(EmpleadoWSClient.EndpointConfiguration.EmpleadoWSPort, $"{baseUrl}/EmpleadoWS");
        }

        public async Task<List<EmpleadoViewModel>> FindAll() {
            try {
                findAllEmpleadosResponse respuesta = await empleadoWS.findAllEmpleadosAsync();
                return ToViewModels(respuesta.@return);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task<EmpleadoViewModel?> FindById(int id) {
            try {
                findEmpleadoByIdResponse respuesta = await empleadoWS.findEmpleadoByIdAsync(id);
                empleadoDTO? dto = respuesta.@return;
                return dto == null ? null : ToViewModel(dto);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task Insert(EmpleadoViewModel empleado) {
            try {
                insertEmpleadoResponse respuesta = await empleadoWS.insertEmpleadoAsync(ToDto(empleado));
                empleado.Id = respuesta.@return;
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task Update(EmpleadoViewModel empleado) {
            try {
                await empleadoWS.updateEmpleadoAsync(ToDto(empleado));
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task Delete(int id) {
            try {
                await empleadoWS.deleteEmpleadoAsync(id);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task<List<EmpleadoViewModel>> FilterByName(string nombre) {
            try {
                filterEmpleadosByNameResponse respuesta = await empleadoWS.filterEmpleadosByNameAsync(nombre);
                return ToViewModels(respuesta.@return);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task<EmpleadoViewModel?> FindByCuenta(string userName) {
            try {
                findEmpleadoByCuentaResponse respuesta = await empleadoWS.findEmpleadoByCuentaAsync(userName);
                empleadoDTO? dto = respuesta.@return;
                return dto == null ? null : ToViewModel(dto);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        private List<EmpleadoViewModel> ToViewModels(empleadoDTO[]? dtos) {
            List<EmpleadoViewModel> empleados = [];
            foreach (empleadoDTO dto in dtos ?? []) {
                empleados.Add(ToViewModel(dto));
            }
            return empleados;
        }

        private EmpleadoViewModel ToViewModel(empleadoDTO dto) {
            return new EmpleadoViewModel {
                Id = dto.id,
                IdCuentaUsuario = dto.cuentaUsuario?.id ?? 0,
                Dni = dto.dni,
                Nombre = dto.nombre,
                ApellidoPaterno = dto.apellidoPaterno,
                Genero = Enum.Parse<Genero>(dto.genero),
                FechaNacimiento = DateTime.ParseExact(dto.fechaNacimiento, FormatoFecha, CultureInfo.InvariantCulture),
                IdArea = dto.area.id,
                NombreArea = dto.area.nombre,
                Cargo = Enum.Parse<Cargo>(dto.cargo),
                Sueldo = dto.sueldo,
                IsActivo = dto.activo
            };
        }

        private empleadoDTO ToDto(EmpleadoViewModel empleado) {
            return new empleadoDTO {
                id = empleado.Id,
                cuentaUsuario = empleado.IdCuentaUsuario > 0 ? new() { id = empleado.IdCuentaUsuario } : null,
                dni = empleado.Dni,
                nombre = empleado.Nombre,
                apellidoPaterno = empleado.ApellidoPaterno,
                genero = empleado.Genero.ToString(),
                fechaNacimiento = empleado.FechaNacimiento!.Value.ToString(FormatoFecha, CultureInfo.InvariantCulture),
                area = new() { id = empleado.IdArea },
                cargo = empleado.Cargo.ToString(),
                sueldo = empleado.Sueldo,
                activo = empleado.IsActivo
            };
        }
    }
}
