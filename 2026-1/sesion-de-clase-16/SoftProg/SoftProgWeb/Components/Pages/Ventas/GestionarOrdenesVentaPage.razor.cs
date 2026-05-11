using Microsoft.AspNetCore.Components;
using SoftProgModelo.Modelos;
using SoftProgNegocio.Bo.Almacen;
using SoftProgNegocio.Bo.Clientes;
using SoftProgNegocio.Bo.Ventas;
using SoftProgWeb.ViewModels;
using SoftProgWeb.ViewModels.Mappers;

namespace SoftProgWeb.Components.Pages.Ventas;

public partial class GestionarOrdenesVentaPage : ComponentBase {
    [Inject] private IOrdenVentaBo OrdenVentaBo { get; set; } = default!;
    [Inject] private IClienteBo ClienteBo { get; set; } = default!;
    [Inject] private IProductoBo ProductoBo { get; set; } = default!;
    [Inject] private NavigationManager NavigationManager { get; set; } = default!;

    [SupplyParameterFromQuery(Name = "id")]
    public int? Id { get; set; }

    [SupplyParameterFromQuery(Name = "returnUrl")]
    public string? ReturnUrl { get; set; }

    private string Titulo { get; set; } = "Registrar orden de venta";
    private OrdenVentaViewModel Orden { get; set; } = new() { Lineas = [] };
    private List<ClienteViewModel> Clientes { get; set; } = new();
    private List<ProductoViewModel> Productos { get; set; } = new();
    private int _clienteIdSeleccionado;
    private int ClienteIdSeleccionado {
        get => _clienteIdSeleccionado;
        set {
            _clienteIdSeleccionado = value;
            Orden.ClienteIdSeleccionado = value;
            Orden.Cliente = Clientes.FirstOrDefault(cliente => cliente.Id == _clienteIdSeleccionado);
        }
    }
    private LineaOrdenVentaViewModel NuevaLinea { get; set; } = new() { Cantidad = 1 };
    private string MensajeResultado { get; set; } = string.Empty;
    private bool OperacionExitosa { get; set; }
    private string RutaRetorno => ObtenerRutaRetorno("/ListarOrdenesVenta");

    protected override void OnParametersSet() {
        CargarClientes();
        CargarProductos();

        if (Id is > 0) {
            try {
                var orden = OrdenVentaBo.Obtener(Id.Value) ?? throw new InvalidOperationException();
                Orden = OrdenVentaViewModelMapper.ToViewModel(orden);
                Orden.Lineas ??= [];
                ClienteIdSeleccionado = Orden.Cliente?.Id ?? 0;
                Titulo = "Modificar orden de venta";
            }
            catch {
                OperacionExitosa = false;
                MensajeResultado = "La orden no existe.";
            }
        }
        else {
            Orden = new OrdenVentaViewModel { FechaRegistro = DateTime.Now, Lineas = [] };
            ClienteIdSeleccionado = 0;
            Titulo = "Registrar orden de venta";
        }
    }

    private void CargarClientes() {
        try {
            Clientes = [.. ClienteBo.Listar().Select(ClienteViewModelMapper.ToViewModel)];
        }
        catch {
            Clientes = [];
        }
    }

    private void CargarProductos() {
        try {
            Productos = [.. ProductoBo.Listar().Select(ProductoViewModelMapper.ToViewModel)];
        }
        catch {
            Productos = [];
        }
    }

    private void AgregarLinea() {
        if (NuevaLinea.ProductoId <= 0 || NuevaLinea.Cantidad <= 0) {
            return;
        }

        var producto = Productos.FirstOrDefault(item => item.Id == NuevaLinea.ProductoId);
        if (producto is null) {
            return;
        }

        var existente = Orden.Lineas.FirstOrDefault(item => item.ProductoId == producto.Id);
        if (existente is not null) {
            existente.Cantidad += NuevaLinea.Cantidad;
            existente.PrecioUnitario = Convert.ToDouble(producto.Precio);
        }
        else {
            Orden.Lineas.Add(new LineaOrdenVentaViewModel {
                ProductoId = producto.Id,
                ProductoNombre = producto.Nombre,
                Cantidad = NuevaLinea.Cantidad,
                PrecioUnitario = Convert.ToDouble(producto.Precio)
            });
        }

        NuevaLinea = new LineaOrdenVentaViewModel { Cantidad = 1 };
    }

    private void QuitarLinea(LineaOrdenVentaViewModel linea) {
        Orden.Lineas.Remove(linea);
    }

    private void GuardarOrden() {
        MensajeResultado = string.Empty;
        OperacionExitosa = false;

        if (Orden.ClienteIdSeleccionado <= 0) {
            MensajeResultado = "Debe seleccionar un cliente.";
            return;
        }

        if (Orden.Lineas.Count == 0) {
            MensajeResultado = "Debe agregar al menos una linea a la orden.";
            return;
        }

        try {
            Orden.Lineas = Orden.Lineas
                .Where(linea => linea.ProductoId > 0 && linea.Cantidad > 0)
                .ToList();

            Orden.Cliente = Clientes.FirstOrDefault(cliente => cliente.Id == Orden.ClienteIdSeleccionado);
            if (Orden.Cliente is null) {
                MensajeResultado = "Debe seleccionar un cliente valido.";
                return;
            }

            var orden = OrdenVentaViewModelMapper.ToDomain(Orden);
            var estado = orden.Id <= 0 ? Estado.Nuevo : Estado.Modificado;
            OrdenVentaBo.Guardar(orden, estado);

            OperacionExitosa = true;
            MensajeResultado = "Operacion realizada correctamente.";
            NavigationManager.NavigateTo("/ListarOrdenesVenta");
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
