namespace SoftProg.AccessoDatos.DAO {
    public interface IDAO<T, ID> {
        List<T> FindAll();
        T? FindById(ID id);
        void Insert(T modelo);
        void Update(T modelo);
        void Delete(ID id);
    }
}
