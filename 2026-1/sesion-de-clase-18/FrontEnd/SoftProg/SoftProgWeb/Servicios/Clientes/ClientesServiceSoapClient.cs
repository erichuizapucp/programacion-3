using SoftProgWS;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Clientes;

public class ClientesServiceSoapClient : SoapServiceClient<ClienteViewModel, cliente>, IClientesServiceClient {
    private const string EndpointSetting = "SoapEndpoints:Clientes";

    public ClientesServiceSoapClient(IConfiguration configuration)
        : base(configuration) {
    }

    public List<ClienteViewModel> Listar() {
        var clienteWs = CrearClienteWs();
        var clientes = clienteWs.listarClientes() ?? [];

        var respuesta = new List<ClienteViewModel>();
        foreach (var item in clientes) {
            respuesta.Add(ToViewModel(item));
        }

        return respuesta;
    }

    public ClienteViewModel? Obtener(int id) {
        var clienteWs = CrearClienteWs();
        var cliente = clienteWs.obtenerCliente(id);
        return cliente is null ? null : ToViewModel(cliente);
    }

    public ClienteViewModel? BuscarPorDni(string dni) {
        var clienteWs = CrearClienteWs();
        var cliente = clienteWs.buscarClientePorDni(dni);
        return cliente is null ? null : ToViewModel(cliente);
    }

    public ClienteViewModel? BuscarPorCuenta(string cuenta) {
        var clienteWs = CrearClienteWs();
        var cliente = clienteWs.buscarClientePorCuenta(cuenta);
        return cliente is null ? null : ToViewModel(cliente);
    }

    public void Guardar(ClienteViewModel cliente, Estado estado) {
        var clienteWs = CrearClienteWs();
        clienteWs.guardarCliente(ToSoap(cliente), ParseEstado<estado>(estado));
    }

    public void Eliminar(int id) {
        var clienteWs = CrearClienteWs();
        clienteWs.eliminarCliente(id);
    }

    private ClientesWSClient CrearClienteWs() {
        return (ClientesWSClient)CreateClient();
    }

    protected override object CreateClient() {
        var endpoint = ClientesWSClient.EndpointConfiguration.ClientesWSPort;
        var url = Configuration[EndpointSetting]?.Trim();

        if (string.IsNullOrWhiteSpace(url)) {
            return new ClientesWSClient(endpoint);
        }

        return new ClientesWSClient(endpoint, url);
    }

    protected override ClienteViewModel ToViewModel(cliente source) {
        return new ClienteViewModel {
            Id = source.id,
            Activo = source.activo,
            Dni = source.dni ?? string.Empty,
            Nombre = source.nombre ?? string.Empty,
            ApellidoPaterno = source.apellidoPaterno ?? string.Empty,
            ApellidoMaterno = string.Empty,
            Correo = string.Empty,
            Telefono = string.Empty,
            Genero = ParseEnum(source.genero, genero.MASCULINO).ToString(),
            FechaNacimiento = source.fechaNacimientoSpecified ? source.fechaNacimiento : DateTime.Today,
            Categoria = ParseEnum(source.categoria, categoriaCliente.ESTANDARD).ToString(),
            LineaCredito = Convert.ToDecimal(source.lineaCredito),
            CuentaUsuario = source.cuentaUsuario is null
                ? null
                : new CuentaUsuarioViewModel {
                    Id = source.cuentaUsuario.id,
                    Activo = source.cuentaUsuario.activo,
                    UserName = source.cuentaUsuario.userName ?? string.Empty,
                    Password = string.Empty
                }
        };
    }

    protected override cliente ToSoap(ClienteViewModel source) {
        var generoCliente = Enum.TryParse<genero>(source.Genero, true, out var generoParseado)
            ? generoParseado
            : genero.MASCULINO;

        var categoria = Enum.TryParse<categoriaCliente>(source.Categoria, true, out var categoriaParseada)
            ? categoriaParseada
            : categoriaCliente.ESTANDARD;

        return new cliente {
            id = source.Id,
            activo = source.Activo,
            dni = source.Dni.Trim(),
            nombre = source.Nombre.Trim(),
            apellidoPaterno = source.ApellidoPaterno.Trim(),
            genero = generoCliente,
            generoSpecified = true,
            fechaNacimiento = source.FechaNacimiento ?? DateTime.Today,
            fechaNacimientoSpecified = true,
            categoria = categoria,
            categoriaSpecified = true,
            lineaCredito = Convert.ToDouble(source.LineaCredito),
            cuentaUsuario = source.CuentaUsuario is null
                ? null
                : new cuentaUsuario {
                    id = source.CuentaUsuario.Id,
                    activo = source.CuentaUsuario.Activo,
                    userName = source.CuentaUsuario.UserName.Trim(),
                    password = source.CuentaUsuario.Password
                }
        };
    }
}