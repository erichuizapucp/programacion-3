using SoftProgModelo.Modelos.Rrhh;

namespace SoftProgPersistencia.Dao.Cuentas;

public interface ICuentaUsuarioDao : IPersistible<CuentaUsuario, int>
{
    bool Login(string username, string password);
}
