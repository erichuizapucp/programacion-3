using SoftProgWS.Clientes;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;
using SoftProgWeb.ViewModels.Mappers;

namespace SoftProgWeb.Servicios.Clientes;

public class ClientesServicecsImpl : SoapServiceBase, IClientesService {
    private const string EndpointSetting = "SoapEndpoints:Clientes";

    public ClientesServicecsImpl(IConfiguration configuration)
        : base(configuration) {
    }

    public List<ClienteViewModel> Listar() {
        var clienteWs = CrearClienteWs();
        var clientes = clienteWs.listarClientes() ?? [];

        var respuesta = new List<ClienteViewModel>();
        foreach (var item in clientes) {
            var viewModel = ClienteViewModelMapper.ToViewModel(ToDomain(item));
            respuesta.Add(viewModel);
        }

        return respuesta;
    }

    public ClienteViewModel? Obtener(int id) {
        var clienteWs = CrearClienteWs();
        var cliente = clienteWs.obtenerCliente(id);
        return cliente is null ? null : ClienteViewModelMapper.ToViewModel(ToDomain(cliente));
    }

    public ClienteViewModel? BuscarPorDni(string dni) {
        var clienteWs = CrearClienteWs();
        var cliente = clienteWs.buscarClientePorDni(dni);
        return cliente is null ? null : ClienteViewModelMapper.ToViewModel(ToDomain(cliente));
    }

    public ClienteViewModel? BuscarPorCuenta(string cuenta) {
        var clienteWs = CrearClienteWs();
        var cliente = clienteWs.buscarClientePorCuenta(cuenta);
        return cliente is null ? null : ClienteViewModelMapper.ToViewModel(ToDomain(cliente));
    }

    public void Guardar(ClienteViewModel cliente, Estado estado) {
        var clienteWs = CrearClienteWs();
        var domain = ClienteViewModelMapper.ToDomain(cliente);
        clienteWs.guardarCliente(ToSoap(domain), ParseEstado<estado>(estado));
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

    private static Cliente ToDomain(cliente source) {
        return new Cliente {
            Id = source.id,
            Activo = source.activo,
            Dni = source.dni ?? string.Empty,
            Nombre = source.nombre ?? string.Empty,
            ApellidoPaterno = source.apellidoPaterno ?? string.Empty,
            Genero = ParseEnum(source.genero, Genero.MASCULINO),
            FechaNacimiento = source.fechaNacimientoSpecified ? source.fechaNacimiento : DateTime.Today,
            Categoria = ParseEnum(source.categoria, CategoriaCliente.ESTANDARD),
            LineaCredito = source.lineaCredito,
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

    private static cliente ToSoap(Cliente source) {
        return new cliente {
            id = source.Id,
            activo = source.Activo,
            dni = source.Dni,
            nombre = source.Nombre,
            apellidoPaterno = source.ApellidoPaterno,
            genero = ParseEnum(source.Genero, genero.MASCULINO),
            generoSpecified = true,
            fechaNacimiento = source.FechaNacimiento,
            fechaNacimientoSpecified = true,
            categoria = ParseEnum(source.Categoria, categoriaCliente.ESTANDARD),
            categoriaSpecified = true,
            lineaCredito = source.LineaCredito,
            cuentaUsuario = source.CuentaUsuario is null
                ? null
                : new SoftProgWS.Clientes.cuentaUsuario {
                    id = source.CuentaUsuario.Id,
                    activo = source.CuentaUsuario.Activo,
                    userName = source.CuentaUsuario.UserName,
                    password = source.CuentaUsuario.Password
                }
        };
    }
}