using SoftProgWS;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Cuentas;

public class CuentasUsuarioSoapClient : SoapServiceClient<CuentaUsuarioViewModel, cuentaUsuario>, ICuentasUsuarioServiceClient {
    private const string EndpointSetting = "SoapEndpoints:CuentasUsuario";

    public CuentasUsuarioSoapClient(IConfiguration configuration)
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
            respuesta.Add(ToViewModel(item, includePassword: false));
        }

        return respuesta;
    }

    public CuentaUsuarioViewModel? Obtener(int id) {
        var clienteWs = CrearClienteWs();
        var cuenta = clienteWs.obtenerCuentaUsuario(id);
        return cuenta is null ? null : ToViewModel(cuenta, includePassword: true);
    }

    public CuentaUsuarioViewModel? ObtenerPorUsername(string username) {
        var clienteWs = CrearClienteWs();
        var cuenta = (clienteWs.listarCuentasUsuario() ?? [])
            .FirstOrDefault(actual => string.Equals(actual.userName, username, StringComparison.OrdinalIgnoreCase));

        return cuenta is null ? null : ToViewModel(cuenta, includePassword: true);
    }

    public void Guardar(CuentaUsuarioViewModel cuenta, Estado estado) {
        var clienteWs = CrearClienteWs();
        var fallback = string.Empty;

        if (cuenta.Id > 0) {
            var cuentaActual = Obtener(cuenta.Id);
            fallback = cuentaActual?.Password ?? string.Empty;
        }

        clienteWs.guardarCuentaUsuario(ToSoap(cuenta, fallback), ParseEstado<estado>(estado));
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

    protected override CuentaUsuarioViewModel ToViewModel(cuentaUsuario source) {
        return ToViewModel(source, includePassword: false);
    }

    protected override cuentaUsuario ToSoap(CuentaUsuarioViewModel source) {
        return ToSoap(source, string.Empty);
    }

    private cuentaUsuario ToSoap(CuentaUsuarioViewModel source, string passwordFallback) {
        var password = string.IsNullOrWhiteSpace(source.Password) ? passwordFallback : source.Password;

        return new cuentaUsuario {
            id = source.Id,
            activo = source.Activo,
            userName = source.UserName.Trim(),
            password = password
        };
    }

    private CuentaUsuarioViewModel ToViewModel(cuentaUsuario source, bool includePassword) {
        return new CuentaUsuarioViewModel {
            Id = source.id,
            Activo = source.activo,
            UserName = source.userName ?? string.Empty,
            Password = includePassword ? source.password ?? string.Empty : string.Empty,
            ConfirmarPassword = string.Empty
        };
    }
}
