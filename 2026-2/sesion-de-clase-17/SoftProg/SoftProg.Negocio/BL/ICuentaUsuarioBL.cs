using SoftProg.Modelo.Seguridad;

namespace SoftProg.Negocio.BL {
    public interface ICuentaUsuarioBL : IRegistroBL<CuentaUsuario, int> {
        CuentaUsuario? FindByUserName(string userName);
        bool Autenticar(string userName, string password);
        void CambiarPassword(int id, string passwordActual, string passwordNueva);
    }
}
