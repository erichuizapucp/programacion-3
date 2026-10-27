using SoftProg.Web.ViewModels;
using SoftProgWS;
using System.Globalization;
using System.ServiceModel;

namespace SoftProg.Web.Servicios.Soap {
    public class EmpleadoSoapService : IEmpleadoService {
        private const string FormatoFecha = "yyyy-MM-dd";

        private readonly EmpleadoWSClient empleadoWS;

        public EmpleadoSoapService(IConfiguration configuration) {
            string baseUrl = configuration["SoapWS:BaseUrl"]!;
            empleadoWS = new EmpleadoWSClient(EmpleadoWSClient.EndpointConfiguration.EmpleadoWSPort, $"{baseUrl}/EmpleadoWS");
        }

        public List<EmpleadoViewModel> FindAll() {
            try {
                return ToViewModels(empleadoWS.findAllEmpleados());
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public EmpleadoViewModel? FindById(int id) {
            try {
                empleadoDTO? dto = empleadoWS.findEmpleadoById(id);
                return dto == null ? null : ToViewModel(dto);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public void Insert(EmpleadoViewModel empleado) {
            try {
                empleado.Id = empleadoWS.insertEmpleado(ToSoap(empleado));
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public void Update(EmpleadoViewModel empleado) {
            try {
                empleadoWS.updateEmpleado(ToSoap(empleado));
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public void Delete(int id) {
            try {
                empleadoWS.deleteEmpleado(id);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public List<EmpleadoViewModel> FilterByName(string nombre) {
            try {
                return ToViewModels(empleadoWS.filterEmpleadosByName(nombre));
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public EmpleadoViewModel? FindByCuenta(string userName) {
            try {
                empleadoDTO? dto = empleadoWS.findEmpleadoByCuenta(userName);
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

        private empleadoDTO ToSoap(EmpleadoViewModel empleado) {
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
