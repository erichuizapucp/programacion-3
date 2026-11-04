using SoftProg.Web.ViewModels;

namespace SoftProg.Web.Servicios.Rest {
    public class ProductosRestService : IProductoService {
        public async Task<List<ProductoViewModel>> FindAll() {
            throw new NotImplementedException();
        }

        public async Task<ProductoViewModel?> FindById(int id) {
            throw new NotImplementedException();
        }

        public async Task<List<ProductoViewModel>> FilterByName(string nombre) {
            throw new NotImplementedException();
        }

        public async Task Delete(int id) {
            throw new NotImplementedException();
        }

        public async Task Insert(ProductoViewModel modelo) {
            throw new NotImplementedException();
        }

        public async Task Update(ProductoViewModel modelo) {
            throw new NotImplementedException();
        }
    }
}
