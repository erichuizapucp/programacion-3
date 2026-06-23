using SoftProgWS;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Rrhh;

public class EmpleadosServiceSoapClient : SoapServiceClient<EmpleadoViewModel, empleado>, IEmpleadosServiceClient {
    private const string EndpointSetting = "SoapEndpoints:Empleados";

    public EmpleadosServiceSoapClient(IConfiguration configuration)
        : base(configuration) {
    }

    public List<EmpleadoViewModel> Listar() {
        var clienteWs = CrearClienteWs();
        var empleados = clienteWs.listarEmpleados() ?? [];

        var respuesta = new List<EmpleadoViewModel>();
        foreach (var item in empleados) {
            respuesta.Add(ToViewModel(item));
        }

        return respuesta;
    }

    public EmpleadoViewModel? Obtener(int id) {
        var clienteWs = CrearClienteWs();
        var empleado = clienteWs.obtenerEmpleaedo(id);
        return empleado is null ? null : ToViewModel(empleado);
    }

    public EmpleadoViewModel? BuscarPorDni(string dni) {
        var clienteWs = CrearClienteWs();
        var empleado = clienteWs.buscarEmpleadoPorDni(dni);
        return empleado is null ? null : ToViewModel(empleado);
    }

    public void Guardar(EmpleadoViewModel empleado, Estado estado) {
        var clienteWs = CrearClienteWs();
        clienteWs.guardarEmpleado(ToSoap(empleado), ParseEstado<estado>(estado));
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

    protected override EmpleadoViewModel ToViewModel(empleado source) {
        var nombre = source.nombre ?? string.Empty;
        var apellidoPaterno = source.apellidoPaterno ?? string.Empty;

        return new EmpleadoViewModel {
            Id = source.id,
            Activo = source.activo,
            Dni = source.dni ?? string.Empty,
            Nombre = nombre,
            ApellidoPaterno = apellidoPaterno,
            Genero = ParseEnum(source.genero, Genero.MASCULINO),
            FechaNacimiento = source.fechaNacimientoSpecified ? source.fechaNacimiento : DateTime.Today,
            Cargo = ParseEnum(source.cargo, SoftProgWeb.ViewModels.Cargo.ASISTENTE),
            Sueldo = source.sueldo,
            AreaIdSeleccionada = source.area?.id ?? 0,
            AreaNombre = source.area?.nombre ?? string.Empty,
            NombreCompleto = $"{nombre} {apellidoPaterno}".Trim()
        };
    }

    protected override empleado ToSoap(EmpleadoViewModel source) {
        return new empleado {
            id = source.Id,
            activo = source.Activo,
            dni = source.Dni.Trim(),
            nombre = source.Nombre.Trim(),
            apellidoPaterno = source.ApellidoPaterno.Trim(),
            genero = ParseEnum(source.Genero, genero.MASCULINO),
            generoSpecified = true,
            fechaNacimiento = source.FechaNacimiento ?? DateTime.Today,
            fechaNacimientoSpecified = true,
            cargo = ParseEnum(source.Cargo, cargo.ASISTENTE),
            cargoSpecified = true,
            sueldo = source.Sueldo,
            area = source.AreaIdSeleccionada <= 0
                ? null
                : new area {
                    id = source.AreaIdSeleccionada,
                    activo = true,
                    nombre = source.AreaNombre
                },
            cuentaUsuario = null
        };
    }

}
