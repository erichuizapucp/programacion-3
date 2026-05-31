using Microsoft.AspNetCore.Components;
using SoftProgWeb.Servicios.Almacen;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Components.Pages.Almacen;

public partial class ListarProductosPage : ComponentBase {
    [Inject] private IProductosServiceClient ProductoService { get; set; } = default!;
    [Inject] private NavigationManager NavigationManager { get; set; } = default!;

    private const int TamanoPagina = 5;

    private List<ProductoViewModel> Productos { get; set; } = new();
    private string MensajeResultado { get; set; } = string.Empty;
    private bool OperacionExitosa { get; set; }
    private int PaginaActual { get; set; } = 1;

    private IEnumerable<ProductoViewModel> ProductosPaginados =>
        Productos.Skip((PaginaActual - 1) * TamanoPagina).Take(TamanoPagina);

    private int TotalRegistros => Productos.Count;
    private int TotalPaginas => Math.Max(1, (int)Math.Ceiling((double)TotalRegistros / TamanoPagina));
    private int InicioRegistro => TotalRegistros == 0 ? 0 : ((PaginaActual - 1) * TamanoPagina) + 1;
    private int FinRegistro => Math.Min(PaginaActual * TamanoPagina, TotalRegistros);

    protected override void OnInitialized() {
        CargarProductos();
    }

    private void CargarProductos() {
        try {
            Productos = ProductoService.Listar();
            ReiniciarPaginacion();
            MensajeResultado = string.Empty;
        }
        catch {
            Productos = [];
            OperacionExitosa = false;
            MensajeResultado = "No se pudo completar la operacion.";
        }
    }

    private void RegistrarProducto() {
        NavigationManager.NavigateTo("/GestionarProductos");
    }

    private void ModificarProducto(int id) {
        NavigationManager.NavigateTo($"/GestionarProductos?id={id}");
    }

    private void EliminarProducto(int id) {
        try {
            ProductoService.Eliminar(id);
            OperacionExitosa = true;
            MensajeResultado = "Operacion realizada correctamente.";
            CargarProductos();
        }
        catch {
            OperacionExitosa = false;
            MensajeResultado = "No se pudo completar la operacion.";
        }
    }

    private void PaginaAnterior() {
        if (PaginaActual > 1) {
            PaginaActual--;
        }
    }

    private void PaginaSiguiente() {
        if (PaginaActual < TotalPaginas) {
            PaginaActual++;
        }
    }

    private void ReiniciarPaginacion() {
        PaginaActual = 1;
    }
}
