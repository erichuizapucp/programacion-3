using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PUCP.Edu.Pe.SoftProg.Modelo.Logistica.Almacen;
using PUCP.Edu.Pe.SoftProg.Negocio.BO;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAO.Logistica.Almacen;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAOImpl.Logistica.Almacen;

namespace PUCP.Edu.Pe.SoftProg.Negocio.BOImpl {
    public class ProductoBOImpl : IProductoBO {
        private readonly IProductoDAO productoDAO;

        public ProductoBOImpl() {
            this.productoDAO = new ProductoDAOImpl();
        }

        public void Guardar(Producto producto, Estado estado) {
            if (estado == Estado.Nuevo) {
                this.productoDAO.Insertar(producto);
            }
            else { 
                this.productoDAO.Modificar(producto);
            }
        }

        public void Eliminar(int id) {
            this.productoDAO.Eliminar(id);
        }

        public Producto Obtener(int id) {
            return this.productoDAO.Buscar(id);
        }

        public List<Producto> Listar() {
            return this.productoDAO.Listar();
        }        
    }
}
