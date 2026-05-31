using Microsoft.Extensions.Configuration;
using SoftProgModelo.Modelos;
using SoftProgModelo.Modelos.Rrhh;
using SoftProgWS.Areas;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;
using SoftProgWeb.ViewModels.Mappers;

namespace SoftProgWeb.Servicios.Rrhh;

public class AreasServiceImpl : SoapServiceBase, IAreaService {
    private const string EndpointSetting = "SoapEndpoints:Areas";

    public AreasServiceImpl(IConfiguration configuration)
        : base(configuration) {
    }

    public List<AreaViewModel> Listar() {
        var clienteWs = CrearClienteWs();
        var areas = clienteWs.listarAreas() ?? [];

        var respuesta = new List<AreaViewModel>();
        foreach (var item in areas) {
            var viewModel = AreaViewModelMapper.ToViewModel(ToDomain(item));
            respuesta.Add(viewModel);
        }

        return respuesta;
    }

    public AreaViewModel? Obtener(int id) {
        var clienteWs = CrearClienteWs();
        var area = clienteWs.obtenerArea(id);
        return area is null ? null : AreaViewModelMapper.ToViewModel(ToDomain(area));
    }

    public void Guardar(AreaViewModel area, Estado estado) {
        var clienteWs = CrearClienteWs();
        var domain = AreaViewModelMapper.ToDomain(area);
        clienteWs.guardarArea(ToSoap(domain), ParseEstado<estado>(estado));
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

    private static Area ToDomain(area source) {
        return new Area {
            Id = source.id,
            Activo = source.activo,
            Nombre = source.nombre ?? string.Empty
        };
    }

    private static area ToSoap(Area source) {
        return new area {
            id = source.Id,
            activo = source.Activo,
            nombre = source.Nombre
        };
    }

}
