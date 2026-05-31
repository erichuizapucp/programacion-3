namespace SoftProgWeb.ViewModels.Mappers;

public static class CuentaUsuarioViewModelMapper {
    public static CuentaUsuarioViewModel ToViewModel(CuentaUsuario cuenta) {
        return new CuentaUsuarioViewModel {
            Id = cuenta.Id,
            UserName = cuenta.UserName,
            Activo = cuenta.Activo
        };
    }

    public static CuentaUsuario ToDomain(CuentaUsuarioViewModel viewModel, string passwordFallback) {
        return new CuentaUsuario {
            Id = viewModel.Id,
            UserName = viewModel.UserName.Trim(),
            Password = string.IsNullOrWhiteSpace(viewModel.Password) ? passwordFallback : viewModel.Password,
            Activo = viewModel.Activo
        };
    }
}
