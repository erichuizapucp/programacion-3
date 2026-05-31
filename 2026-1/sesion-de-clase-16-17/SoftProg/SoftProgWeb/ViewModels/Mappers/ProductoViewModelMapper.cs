using SoftProgModelo.Modelos.Almacen;

namespace SoftProgWeb.ViewModels.Mappers;

public static class ProductoViewModelMapper {
    public static ProductoViewModel ToViewModel(Producto producto) {
        return new ProductoViewModel {
            Id = producto.Id,
            Nombre = producto.Nombre,
            UnidadMedida = ToViewModelEnum(producto.UnidadMedida),
            Precio = Convert.ToDecimal(producto.Precio),
            Activo = producto.Activo
        };
    }

    public static Producto ToDomain(ProductoViewModel viewModel) {
        return new Producto {
            Id = viewModel.Id,
            Nombre = viewModel.Nombre.Trim(),
            UnidadMedida = ToDomainEnum(viewModel.UnidadMedida),
            Precio = Convert.ToDouble(viewModel.Precio),
            Activo = viewModel.Activo
        };
    }

    private static UnidadMedidaEnum ToViewModelEnum(UnidadMedida unidadMedida) {
        return unidadMedida switch {
            UnidadMedida.UND => UnidadMedidaEnum.Unidad,
            UnidadMedida.Kilos => UnidadMedidaEnum.Kilos,
            UnidadMedida.Onzas => UnidadMedidaEnum.Onzas,
            UnidadMedida.Litros => UnidadMedidaEnum.Litros,
            _ => UnidadMedidaEnum.Unidad
        };
    }

    private static UnidadMedida ToDomainEnum(UnidadMedidaEnum unidadMedida) {
        return unidadMedida switch {
            UnidadMedidaEnum.Unidad => UnidadMedida.UND,
            UnidadMedidaEnum.Kilos => UnidadMedida.Kilos,
            UnidadMedidaEnum.Onzas => UnidadMedida.Onzas,
            UnidadMedidaEnum.Litros => UnidadMedida.Litros,
            _ => UnidadMedida.UND
        };
    }
}
