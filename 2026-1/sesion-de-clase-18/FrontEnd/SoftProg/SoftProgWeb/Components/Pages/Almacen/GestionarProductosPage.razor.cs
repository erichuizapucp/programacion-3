using Microsoft.AspNetCore.Components;
using SoftProgWeb.Servicios.Almacen;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Components.Pages.Almacen;

public partial class GestionarProductosPage : ComponentBase {
    [Inject] private IProductosServiceClient ProductoServiceClient { get; set; } = default!;
    [Inject] private NavigationManager NavigationManager { get; set; } = default!;

    [SupplyParameterFromQuery(Name = "id")]
    public int? Id { get; set; }

    [SupplyParameterFromQuery(Name = "returnUrl")]
    public string? ReturnUrl { get; set; }

    private string Titulo { get; set; } = "Registrar producto";
    private ProductoViewModel Producto { get; set; } = new() { UnidadMedida = UnidadMedidaEnum.Unidad };
    private string MensajeResultado { get; set; } = string.Empty;
    private bool OperacionExitosa { get; set; }
    private string RutaRetorno => ObtenerRutaRetorno("/ListarProductos");

    protected override void OnParametersSet() {
        if (Id is > 0) {
            try {
                Producto = ProductoServiceClient.Obtener(Id.Value) ?? throw new InvalidOperationException();
                Titulo = "Modificar producto";
            }
            catch {
                OperacionExitosa = false;
                MensajeResultado = "El producto no existe.";
            }
        }
        else {
            Producto = new ProductoViewModel { UnidadMedida = UnidadMedidaEnum.Unidad };
            Titulo = "Registrar producto";
        }
    }

    private void GuardarProducto() {
        MensajeResultado = string.Empty;
        OperacionExitosa = false;

        if (Id is > 0) {
            Producto.Id = Id.Value;
        }

        try {
            var estado = Producto.Id <= 0 ? Estado.Nuevo : Estado.Modificado;
            ProductoServiceClient.Guardar(Producto, estado);

            OperacionExitosa = true;
            MensajeResultado = "Operacion realizada correctamente.";
            NavigationManager.NavigateTo("/ListarProductos");
        }
        catch {
            OperacionExitosa = false;
            MensajeResultado = "No se pudo completar la operacion.";
        }

    }

    private string ObtenerRutaRetorno(string fallback) {
        if (string.IsNullOrWhiteSpace(ReturnUrl)) {
            return fallback;
        }

        return ReturnUrl.StartsWith('/') ? ReturnUrl : fallback;
    }
}
