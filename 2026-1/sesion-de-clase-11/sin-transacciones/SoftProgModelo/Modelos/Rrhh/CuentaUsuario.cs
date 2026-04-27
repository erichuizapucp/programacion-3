using SoftProgModelo.Modelos;

namespace SoftProgModelo.Modelos.Rrhh;

public class CuentaUsuario : Registro
{
    public string UserName { get; set; } = string.Empty;
    public string Password { get; set; } = string.Empty;
}
