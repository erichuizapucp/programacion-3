using Microsoft.Extensions.Configuration;
using SoftProgModelo.Modelos;
using SoftProgModelo.Modelos.Rrhh;
using SoftProgWS.CuentasUsuario;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;
using SoftProgWeb.ViewModels.Mappers;

namespace SoftProgWeb.Servicios.Cuentas;

public class CuentasUsuarioServiceImpl : SoapServiceBase, ICuentasUsuarioService {
    private const string EndpointSetting = "SoapEndpoints:CuentasUsuario";

    public CuentasUsuarioServiceImpl(IConfiguration configuration)
        : base(configuration) {
    }

    public bool Login(string username, string password) {
        var clienteWs = CrearClienteWs();
        return clienteWs.login(username, password);
    }

    public List<CuentaUsuarioViewModel> Listar() {
        var clienteWs = CrearClienteWs();
        var cuentas = clienteWs.listarCuentasUsuario() ?? [];

        var respuesta = new List<CuentaUsuarioViewModel>();
        foreach (var item in cuentas) {
            var viewModel = ToViewModel(ToDomain(item), includePassword: false);
            respuesta.Add(viewModel);
        }

        return respuesta;
    }

    public CuentaUsuarioViewModel? Obtener(int id) {
        var clienteWs = CrearClienteWs();
        var cuenta = clienteWs.obtenerCuentaUsuario(id);
        return cuenta is null ? null : ToViewModel(ToDomain(cuenta), includePassword: true);
    }

    public CuentaUsuarioViewModel? ObtenerPorUsername(string username) {
        var clienteWs = CrearClienteWs();
        var cuenta = (clienteWs.listarCuentasUsuario() ?? [])
            .FirstOrDefault(actual => string.Equals(actual.userName, username, StringComparison.OrdinalIgnoreCase));

        return cuenta is null ? null : ToViewModel(ToDomain(cuenta), includePassword: true);
    }

    public void Guardar(CuentaUsuarioViewModel cuenta, Estado estado) {
        var clienteWs = CrearClienteWs();
        var fallback = string.Empty;

        if (cuenta.Id > 0) {
            var cuentaActual = Obtener(cuenta.Id);
            fallback = cuentaActual?.Password ?? string.Empty;
        }

        var domain = CuentaUsuarioViewModelMapper.ToDomain(cuenta, fallback);
        clienteWs.guardarCuentaUsuario(ToSoap(domain), ParseEstado<estado>(estado));
    }

    public void Eliminar(int id) {
        var clienteWs = CrearClienteWs();
        clienteWs.eliminarCuentaUsuario(id);
    }

    private CuentasUsuarioWSClient CrearClienteWs() {
        return (CuentasUsuarioWSClient)CreateClient();
    }

    protected override object CreateClient() {
        var endpoint = CuentasUsuarioWSClient.EndpointConfiguration.CuentasUsuarioWSPort;
        var url = Configuration[EndpointSetting]?.Trim();

        if (string.IsNullOrWhiteSpace(url)) {
            return new CuentasUsuarioWSClient(endpoint);
        }

        return new CuentasUsuarioWSClient(endpoint, url);
    }

    private static CuentaUsuario ToDomain(cuentaUsuario source) {
        return new CuentaUsuario {
            Id = source.id,
            Activo = source.activo,
            UserName = source.userName ?? string.Empty,
            Password = source.password ?? string.Empty
        };
    }

    private static cuentaUsuario ToSoap(CuentaUsuario source) {
        return new cuentaUsuario {
            id = source.Id,
            activo = source.Activo,
            userName = source.UserName,
            password = source.Password
        };
    }

    private static CuentaUsuarioViewModel ToViewModel(CuentaUsuario source, bool includePassword) {
        var viewModel = CuentaUsuarioViewModelMapper.ToViewModel(source);
        if (includePassword) {
            viewModel.Password = source.Password;
        }
        return viewModel;
    }

}
