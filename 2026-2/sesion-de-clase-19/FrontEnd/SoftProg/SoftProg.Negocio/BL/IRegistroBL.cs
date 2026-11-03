using SoftProg.Modelo;

namespace SoftProg.Negocio.BL {
    public interface IRegistroBL<T, ID> where T : Registro {
        List<T> FindAll();
        T? FindById(ID id);
        void Insert(T entidad);
        void Update(T entidad);
        void Delete(ID id);
    }
}
