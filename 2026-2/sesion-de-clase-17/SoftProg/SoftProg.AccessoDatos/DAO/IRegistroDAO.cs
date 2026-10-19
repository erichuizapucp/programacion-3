using SoftProg.Modelo;

namespace SoftProg.AccessoDatos.DAO {
    public interface IRegistroDAO<T> : IDAO<T, int> where T : Registro {
    }
}
