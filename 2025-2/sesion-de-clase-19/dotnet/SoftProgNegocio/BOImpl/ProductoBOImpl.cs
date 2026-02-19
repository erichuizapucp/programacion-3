using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PUCP.SoftProg.Modelo.Almacen;
using PUCP.SoftProg.Negocio.BO;
using PUCP.SoftProg.Persistencia.DAO.Almacen;
using PUCP.SoftProg.Persistencia.DAOImpl.Almacen;
using PUCP.SoftProg.Modelo;

namespace PUCP.SoftProg.Negocio.BOImpl {
    public class ProductoBOImpl : IProductoBO {
        private readonly IProductoDAO productoDAO;

        public ProductoBOImpl() {
            this.productoDAO = new ProductoDAOImpl();
        }

        public void Guardar(Producto producto, Estado estado) {
            if (estado == Estado.Nuevo) {
                this.productoDAO.Crear(producto);
            }
            else { 
                this.productoDAO.Actualizar(producto);
            }
        }

        public void Eliminar(int id) {
            this.productoDAO.Eliminar(id);
        }

        public Producto Obtener(int id) {
            return this.productoDAO.Leer(id);
        }

        public List<Producto> Listar() {
            return this.productoDAO.LeerTodos();
        }        
    }
}
