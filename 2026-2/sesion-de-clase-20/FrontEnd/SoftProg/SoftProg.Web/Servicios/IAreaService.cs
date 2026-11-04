using SoftProg.Web.ViewModels;

namespace SoftProg.Web.Servicios {
    public interface IAreaService {
        Task<List<AreaViewModel>> FindAll();
        Task<AreaViewModel?> FindById(int id);
        Task Insert(AreaViewModel modelo);
        Task Update(AreaViewModel modelo);
        Task Delete(int id);
        Task<List<AreaViewModel>> FilterByName(string nombre);
    }
}
