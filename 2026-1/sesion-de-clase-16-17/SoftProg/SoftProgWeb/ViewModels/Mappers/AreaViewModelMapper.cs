using SoftProgModelo.Modelos.Rrhh;

namespace SoftProgWeb.ViewModels.Mappers;

public static class AreaViewModelMapper {
    public static AreaViewModel ToViewModel(Area area) {
        return new AreaViewModel {
            Id = area.Id,
            Nombre = area.Nombre,
            Activo = area.Activo
        };
    }

    public static Area ToDomain(AreaViewModel viewModel) {
        return new Area {
            Id = viewModel.Id,
            Nombre = viewModel.Nombre,
            Activo = viewModel.Activo
        };
    }
}
