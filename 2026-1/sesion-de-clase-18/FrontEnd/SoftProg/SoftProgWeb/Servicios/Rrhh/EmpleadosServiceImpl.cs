using Microsoft.Extensions.Configuration;
using SoftProgModelo.Modelos;
using SoftProgModelo.Modelos.Rrhh;
using SoftProgWS.Empleados;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;
using SoftProgWeb.ViewModels.Mappers;

namespace SoftProgWeb.Servicios.Rrhh;

public class EmpleadosServiceImpl : SoapServiceBase, IEmpleadosService {
    private const string EndpointSetting = "SoapEndpoints:Empleados";

    public EmpleadosServiceImpl(IConfiguration configuration)
        : base(configuration) {
    }

    public List<EmpleadoViewModel> Listar() {
        var clienteWs = CrearClienteWs();
        var empleados = clienteWs.listarEmpleados() ?? [];

        var respuesta = new List<EmpleadoViewModel>();
        foreach (var item in empleados) {
            var viewModel = EmpleadoViewModelMapper.ToViewModel(ToDomain(item));
            respuesta.Add(viewModel);
        }

        return respuesta;
    }

    public EmpleadoViewModel? Obtener(int id) {
        var clienteWs = CrearClienteWs();
        var empleado = clienteWs.obtenerEmpleaedo(id);
        return empleado is null ? null : EmpleadoViewModelMapper.ToViewModel(ToDomain(empleado));
    }

    public EmpleadoViewModel? BuscarPorDni(string dni) {
        var clienteWs = CrearClienteWs();
        var empleado = clienteWs.buscarEmpleadoPorDni(dni);
        return empleado is null ? null : EmpleadoViewModelMapper.ToViewModel(ToDomain(empleado));
    }

    public void Guardar(EmpleadoViewModel empleado, Estado estado) {
        var clienteWs = CrearClienteWs();
        var areaDomain = new Area {
            Id = empleado.AreaIdSeleccionada,
            Nombre = empleado.AreaNombre,
            Activo = true
        };
        var domain = EmpleadoViewModelMapper.ToDomain(empleado, areaDomain);
        clienteWs.guardarEmpleado(ToSoap(domain), ParseEstado<estado>(estado));
    }

    public void Eliminar(int id) {
        var clienteWs = CrearClienteWs();
        clienteWs.eliminarEmpleado(id);
    }

    private EmpleadosWSClient CrearClienteWs() {
        return (EmpleadosWSClient)CreateClient();
    }

    protected override object CreateClient() {
        var endpoint = EmpleadosWSClient.EndpointConfiguration.EmpleadosWSPort;
        var url = Configuration[EndpointSetting]?.Trim();

        if (string.IsNullOrWhiteSpace(url)) {
            return new EmpleadosWSClient(endpoint);
        }

        return new EmpleadosWSClient(endpoint, url);
    }

    private static Empleado ToDomain(empleado source) {
        return new Empleado {
            Id = source.id,
            Activo = source.activo,
            Dni = source.dni ?? string.Empty,
            Nombre = source.nombre ?? string.Empty,
            ApellidoPaterno = source.apellidoPaterno ?? string.Empty,
            Genero = ParseEnum(source.genero, SoftProgModelo.Modelos.Genero.MASCULINO),
            FechaNacimiento = source.fechaNacimientoSpecified ? source.fechaNacimiento : DateTime.Today,
            Cargo = ParseEnum(source.cargo, Cargo.ASISTENTE),
            Sueldo = source.sueldo,
            Area = source.area is null
                ? null
                : new Area {
                    Id = source.area.id,
                    Activo = source.area.activo,
                    Nombre = source.area.nombre ?? string.Empty
                },
            CuentaUsuario = source.cuentaUsuario is null
                ? null
                : new CuentaUsuario {
                    Id = source.cuentaUsuario.id,
                    Activo = source.cuentaUsuario.activo,
                    UserName = source.cuentaUsuario.userName ?? string.Empty,
                    Password = source.cuentaUsuario.password ?? string.Empty
                }
        };
    }

    private static empleado ToSoap(Empleado source) {
        return new empleado {
            id = source.Id,
            activo = source.Activo,
            dni = source.Dni,
            nombre = source.Nombre,
            apellidoPaterno = source.ApellidoPaterno,
            genero = ParseEnum(source.Genero, genero.MASCULINO),
            generoSpecified = true,
            fechaNacimiento = source.FechaNacimiento,
            fechaNacimientoSpecified = true,
            cargo = ParseEnum(source.Cargo, cargo.ASISTENTE),
            cargoSpecified = true,
            sueldo = source.Sueldo,
            area = source.Area is null
                ? null
                : new SoftProgWS.Empleados.area {
                    id = source.Area.Id,
                    activo = source.Area.Activo,
                    nombre = source.Area.Nombre
                },
            cuentaUsuario = source.CuentaUsuario is null
                ? null
                : new SoftProgWS.Empleados.cuentaUsuario {
                    id = source.CuentaUsuario.Id,
                    activo = source.CuentaUsuario.Activo,
                    userName = source.CuentaUsuario.UserName,
                    password = source.CuentaUsuario.Password
                }
        };
    }

}
