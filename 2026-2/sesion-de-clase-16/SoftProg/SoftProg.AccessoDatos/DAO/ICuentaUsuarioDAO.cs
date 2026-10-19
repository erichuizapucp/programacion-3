using SoftProg.Modelo.Seguridad;

namespace SoftProg.AccessoDatos.DAO {
    public interface ICuentaUsuarioDAO : IRegistroDAO<CuentaUsuario> {
        CuentaUsuario? FindByUserName(string userName);
    }
}
