using SoftProgModelo.Modelos;
using SoftProgModelo.Modelos.Clientes;
using SoftProgModelo.Modelos.Rrhh;

namespace SoftProgWeb.ViewModels.Mappers;

public static class ClienteViewModelMapper {
    public static ClienteViewModel ToViewModel(Cliente cliente) {
        return new ClienteViewModel {
            Id = cliente.Id,
            Dni = cliente.Dni,
            Nombre = cliente.Nombre,
            ApellidoPaterno = cliente.ApellidoPaterno,
            Genero = cliente.Genero.ToString(),
            FechaNacimiento = cliente.FechaNacimiento,
            LineaCredito = Convert.ToDecimal(cliente.LineaCredito),
            Categoria = cliente.Categoria.ToString(),
            CuentaUsuario = cliente.CuentaUsuario is null
                ? null
                : CuentaUsuarioViewModelMapper.ToViewModel(cliente.CuentaUsuario),
            Activo = cliente.Activo
        };
    }

    public static Cliente ToDomain(ClienteViewModel viewModel) {
        return new Cliente {
            Id = viewModel.Id,
            Dni = viewModel.Dni.Trim(),
            Nombre = viewModel.Nombre.Trim(),
            ApellidoPaterno = viewModel.ApellidoPaterno.Trim(),
            Genero = ParseGenero(viewModel.Genero),
            FechaNacimiento = viewModel.FechaNacimiento ?? DateTime.Today,
            LineaCredito = Convert.ToDouble(viewModel.LineaCredito),
            Categoria = ParseCategoria(viewModel.Categoria),
            CuentaUsuario = viewModel.CuentaUsuario is null
                ? null
                : CuentaUsuarioViewModelMapper.ToDomain(viewModel.CuentaUsuario, viewModel.CuentaUsuario.Password),
            Activo = viewModel.Activo
        };
    }

    private static Genero ParseGenero(string genero) {
        return Enum.TryParse<Genero>(genero, true, out var valor)
            ? valor
            : Genero.MASCULINO;
    }

    private static CategoriaCliente ParseCategoria(string categoria) {
        return Enum.TryParse<CategoriaCliente>(categoria, true, out var valor)
            ? valor
            : CategoriaCliente.ESTANDARD;
    }
}
