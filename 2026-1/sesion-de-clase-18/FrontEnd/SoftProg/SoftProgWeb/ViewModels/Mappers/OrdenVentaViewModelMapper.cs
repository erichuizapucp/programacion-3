namespace SoftProgWeb.ViewModels.Mappers;

public static class OrdenVentaViewModelMapper {
    public static OrdenVentaViewModel ToViewModel(OrdenVenta orden) {
        return new OrdenVentaViewModel {
            Id = orden.Id,
            Cliente = orden.Cliente is null ? null : ClienteViewModelMapper.ToViewModel(orden.Cliente),
            ClienteIdSeleccionado = orden.Cliente?.Id ?? 0,
            FechaRegistro = orden.Fecha,
            Lineas = orden.Lineas.Select(ToLineaViewModel).ToList(),
            Activo = orden.Activo
        };
    }

    public static OrdenVenta ToDomain(OrdenVentaViewModel viewModel) {
        return new OrdenVenta {
            Id = viewModel.Id,
            Fecha = viewModel.FechaRegistro,
            Cliente = viewModel.Cliente is null ? null : new Cliente { Id = viewModel.Cliente.Id },
            Lineas = viewModel.Lineas.Select(ToLineaDomain).ToList(),
            // En negocio, el total de la orden debe igualar la suma de subtotales de lineas.
            Total = viewModel.Subtotal,
            Activo = viewModel.Activo
        };
    }

    public static LineaOrdenVentaViewModel ToLineaViewModel(LineaOrdenVenta linea) {
        return new LineaOrdenVentaViewModel {
            Id = linea.Id,
            ProductoId = linea.Producto?.Id ?? 0,
            ProductoNombre = linea.Producto?.Nombre ?? string.Empty,
            Cantidad = linea.Cantidad,
            PrecioUnitario = linea.Cantidad <= 0 ? 0 : linea.SubTotal / linea.Cantidad
        };
    }

    public static LineaOrdenVenta ToLineaDomain(LineaOrdenVentaViewModel viewModel) {
        return new LineaOrdenVenta {
            Id = viewModel.Id,
            Producto = new Producto { Id = viewModel.ProductoId },
            Cantidad = viewModel.Cantidad,
            SubTotal = viewModel.SubTotal,
            Activo = true
        };
    }
}
