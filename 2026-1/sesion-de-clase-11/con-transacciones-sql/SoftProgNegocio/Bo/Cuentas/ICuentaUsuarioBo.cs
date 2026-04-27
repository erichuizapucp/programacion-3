using SoftProgModelo.Modelos.Rrhh;

namespace SoftProgNegocio.Bo.Cuentas;

public interface ICuentaUsuarioBo : IGestionable<CuentaUsuario>
{
    bool Login(string username, string password);
}
