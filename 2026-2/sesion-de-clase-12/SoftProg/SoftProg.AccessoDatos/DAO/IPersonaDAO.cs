using SoftProg.Modelo;

namespace SoftProg.AccessoDatos.DAO {
    public interface IPersonaDAO<T> : IRegistroDAO<T> where T : Persona {
        T? FindByDni(string dni);
    }
}
