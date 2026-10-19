using SoftProg.Modelo.Ventas;

namespace SoftProg.AccessoDatos.DAO {
    public interface IClienteDAO : IPersonaDAO<Cliente> {
        List<Cliente> FilterByName(string nombre);
        Cliente? FindByCuenta(string userName);
        void UpdateLineaCredito(int id, double lineaCredito);
    }
}
