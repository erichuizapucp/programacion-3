using SoftProg.Modelo.Ventas;

namespace SoftProg.Negocio.BL {
    public interface IClienteBL : IRegistroBL<Cliente, int> {
        List<Cliente> FilterByName(string nombre);
    }
}
