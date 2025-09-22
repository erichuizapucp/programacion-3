using PUCP.SoftProg.Modelo.Clientes;

namespace PUCP.SoftProg.Negocio.BO {
    public interface IClienteBO : IBaseBO<Cliente> {
        Cliente BuscarPorDni(string dni);
    }
}
