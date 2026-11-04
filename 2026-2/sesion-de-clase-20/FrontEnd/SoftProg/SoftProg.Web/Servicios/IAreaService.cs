using SoftProg.Web.ViewModels;

namespace SoftProg.Web.Servicios {
    public interface IAreaService {
        List<AreaViewModel> FindAll();
        AreaViewModel? FindById(int id);
        void Insert(AreaViewModel modelo);
        void Update(AreaViewModel modelo);
        void Delete(int id);
        List<AreaViewModel> FilterByName(string nombre);
    }
}
