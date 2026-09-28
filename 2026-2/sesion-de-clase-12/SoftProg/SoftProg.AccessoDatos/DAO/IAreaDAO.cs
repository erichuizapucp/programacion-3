using SoftProg.Modelo.RRHH;

namespace SoftProg.AccessoDatos.DAO {
    public interface IAreaDAO : IRegistroDAO<Area> {
        Area? FindByName(string nombre);
    }
}
