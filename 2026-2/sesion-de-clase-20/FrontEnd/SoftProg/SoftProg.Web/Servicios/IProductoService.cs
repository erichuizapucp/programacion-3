using SoftProg.Web.ViewModels;

namespace SoftProg.Web.Servicios {
    public interface IProductoService {
        Task<List<ProductoViewModel>> FindAll();
        Task<ProductoViewModel?> FindById(int id);
        Task Insert(ProductoViewModel modelo);
        Task Update(ProductoViewModel modelo);
        Task Delete(int id);
        Task<List<ProductoViewModel>> FilterByName(string nombre);
    }
}
