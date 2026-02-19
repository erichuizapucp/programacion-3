using PUCP.SoftProg.Modelo.RRHH;

namespace PUCP.SoftProg.Persistencia.DAO.RRHH {
    public interface ICuentaUsuarioDAO : IPersistibleTransaccional<CuentaUsuario, int>{
        bool Login(string username, string password);
    }
}
