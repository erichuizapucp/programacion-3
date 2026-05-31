using SoftProgModelo.Modelos.Rrhh;

namespace SoftProgWeb.ViewModels.Mappers;

public static class EmpleadoViewModelMapper {
    public static EmpleadoViewModel ToViewModel(Empleado empleado) {
        return new EmpleadoViewModel {
            Id = empleado.Id,
            Dni = empleado.Dni,
            Nombre = empleado.Nombre,
            ApellidoPaterno = empleado.ApellidoPaterno,
            Genero = empleado.Genero,
            FechaNacimiento = empleado.FechaNacimiento,
            Cargo = empleado.Cargo,
            Sueldo = empleado.Sueldo,
            AreaIdSeleccionada = empleado.Area?.Id ?? 0,
            NombreCompleto = $"{empleado.Nombre} {empleado.ApellidoPaterno}".Trim(),
            AreaNombre = empleado.Area?.Nombre ?? string.Empty,
            Activo = empleado.Activo
        };
    }

    public static Empleado ToDomain(EmpleadoViewModel viewModel, Area area) {
        return new Empleado {
            Id = viewModel.Id,
            Dni = viewModel.Dni.Trim(),
            Nombre = viewModel.Nombre.Trim(),
            ApellidoPaterno = viewModel.ApellidoPaterno.Trim(),
            Genero = viewModel.Genero,
            FechaNacimiento = viewModel.FechaNacimiento ?? DateTime.Today,
            Cargo = viewModel.Cargo,
            Sueldo = viewModel.Sueldo,
            Area = area,
            Activo = viewModel.Activo
        };
    }
}
