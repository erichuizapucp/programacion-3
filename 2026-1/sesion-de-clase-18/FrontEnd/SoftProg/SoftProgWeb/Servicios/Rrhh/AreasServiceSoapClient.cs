using SoftProgWS;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Rrhh;

public class AreasServiceSoapClient : SoapServiceClient<AreaViewModel, area>, IAreaServiceClient {
    private const string EndpointSetting = "SoapEndpoints:Areas";

    public AreasServiceSoapClient(IConfiguration configuration)
        : base(configuration) {
    }

    public List<AreaViewModel> Listar() {
        var clienteWs = CrearClienteWs();
        var areas = clienteWs.listarAreas() ?? [];

        var respuesta = new List<AreaViewModel>();
        foreach (var item in areas) {
            respuesta.Add(ToViewModel(item));
        }

        return respuesta;
    }

    public AreaViewModel? Obtener(int id) {
        var clienteWs = CrearClienteWs();
        var area = clienteWs.obtenerArea(id);
        return area is null ? null : ToViewModel(area);
    }

    public void Guardar(AreaViewModel area, Estado estado) {
        var clienteWs = CrearClienteWs();
        clienteWs.guardarArea(ToSoap(area), ParseEstado<estado>(estado));
    }

    public void Eliminar(int id) {
        var clienteWs = CrearClienteWs();
        clienteWs.eliminarArea(id);
    }

    private AreasWSClient CrearClienteWs() {
        return (AreasWSClient)CreateClient();
    }

    protected override object CreateClient() {
        var endpoint = AreasWSClient.EndpointConfiguration.AreasWSPort;
        var url = Configuration[EndpointSetting]?.Trim();

        if (string.IsNullOrWhiteSpace(url)) {
            return new AreasWSClient(endpoint);
        }

        return new AreasWSClient(endpoint, url);
    }

    protected override AreaViewModel ToViewModel(area source) {
        return new AreaViewModel {
            Id = source.id,
            Activo = source.activo,
            Nombre = source.nombre ?? string.Empty
        };
    }

    protected override area ToSoap(AreaViewModel source) {
        return new area {
            id = source.Id,
            activo = source.Activo,
            nombre = source.Nombre.Trim()
        };
    }

}
