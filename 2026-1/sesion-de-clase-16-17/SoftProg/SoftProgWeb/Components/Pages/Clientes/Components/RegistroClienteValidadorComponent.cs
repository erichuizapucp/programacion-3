namespace SoftProgWeb.Components.Pages.Clientes.Components;

public static class RegistroClienteValidador
{
    public static IReadOnlyList<string> ValidarPaso(RegistroClienteFormulario formulario, int paso)
    {
        var errores = new List<string>();

        if (paso == 0)
        {
            if (string.IsNullOrWhiteSpace(formulario.Usuario))
            {
                errores.Add("El usuario es obligatorio.");
            }

            if (string.IsNullOrWhiteSpace(formulario.Contrasena))
            {
                errores.Add("La contraseña es obligatoria.");
            }

            return errores;
        }

        if (paso == 1)
        {
            if (string.IsNullOrWhiteSpace(formulario.Dni))
            {
                errores.Add("El DNI es obligatorio.");
            }
            else
            {
                if (!formulario.Dni.All(char.IsDigit))
                {
                    errores.Add("El DNI solo puede contener números.");
                }

                if (formulario.Dni.Length != 8)
                {
                    errores.Add("El DNI debe tener 8 dígitos.");
                }
            }

            if (string.IsNullOrWhiteSpace(formulario.Nombre))
            {
                errores.Add("El nombre es obligatorio.");
            }

            if (string.IsNullOrWhiteSpace(formulario.ApellidoPaterno))
            {
                errores.Add("El apellido paterno es obligatorio.");
            }

            if (string.IsNullOrWhiteSpace(formulario.Genero))
            {
                errores.Add("Debe seleccionar un género.");
            }

            if (formulario.FechaNacimiento is null)
            {
                errores.Add("La fecha de nacimiento es obligatoria.");
            }

            return errores;
        }

        if (paso == 2)
        {
            if (formulario.LineaCredito is null)
            {
                errores.Add("La línea de crédito es obligatoria.");
            }
            else if (formulario.LineaCredito < 0)
            {
                errores.Add("La línea de crédito no puede ser negativa.");
            }

            if (string.IsNullOrWhiteSpace(formulario.Categoria))
            {
                errores.Add("Debe seleccionar una categoría.");
            }
        }

        return errores;
    }
}
