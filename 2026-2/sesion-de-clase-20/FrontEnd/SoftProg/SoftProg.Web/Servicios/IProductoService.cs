using SoftProg.Web.ViewModels;

namespace SoftProg.Web.Servicios {
    public interface IProductoService {
        List<ProductoViewModel> FindAll();
        ProductoViewModel? FindById(int id);
        void Insert(ProductoViewModel modelo);
        void Update(ProductoViewModel modelo);
        void Delete(int id);
        List<ProductoViewModel> FilterByName(string nombre);
    }
}
